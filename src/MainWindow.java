import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

/*Class displaying the main window where the user add the points to compute the regulation equation*/
public class MainWindow extends JFrame implements ActionListener, ChangeListener, ItemListener {
	
	private static final long serialVersionUID = 1L;
	
	/*Declaration of the components*/
	private JButton buttonAdd, buttonRemove, buttonRemoveAll, buttonLaunch;
	private JButton buttonCalibrateLaser1, buttonCalibrateLaser2;
	private JButton buttonShutter1, buttonShutter2;
	
	private JPanel panel;
	private JSlider sliderLaser1, sliderLaser2;
	private JSeparator verticalSeparator, horizontalSeparator;
	private JComboBox comboLaser1, comboLaser2;
	private JLabel labelSliderLaser1, labelSliderLaser2;
	private JTextArea textConsole;
	private JScrollPane scrollText;
	
	private JLabel labelTotalPower, labelIcon, labelPointNumber;
	
	/*Declaration of the other instance variables*/
	int currentPoint; //Point number currently adjusting
	int valueSliderLaser1 = 0, valueSliderLaser2 = 0; //Current value of sliders
	boolean ready = false, calibrationMemorised = false, allowBackupUpdate = false;
	double[] calibrationLaser1 = new double[11], calibrationLaser2 = new double[11];
	
	FileManager fileManager = new FileManager();
	
	ArrayList<String> listLaser1 = new ArrayList<String>(), listLaser2 = new ArrayList<String>();
	ArrayList<Integer> laser1 = new ArrayList<Integer>(), laser2 = new ArrayList<Integer>();
	ArrayList<String> previousStateValues = new ArrayList<String>();
	
	Communication serial;
	CalculationPoints calculation;
	
	ActionManager process;

	/*Constructor used at every change*/
	public MainWindow() {
		initComponents();
	}
	
	
	/*Constructor used during the initialization*/
	public MainWindow(Communication com) {
		calculation = new CalculationPoints();
		currentPoint = 1;
		laser1.add(0);	//First array case, avoid to read an empty array
		laser2.add(0);
		initComponents();
		process = new ActionManager(com);
		serial = com;
		ready = true;
		getPreviousWavelength();
	}
	
	/*Constructor used when stop is pressed in the regulation window*/
	public MainWindow(Communication com, ArrayList<Integer> l1, ArrayList<Integer> l2) {
		calculation = new CalculationPoints();
		currentPoint = 1;
		laser1 = l1;
		laser2 = l2;
		initComponents();
		process = new ActionManager(com);
		serial = com;
		ready = true;
		getPreviousWavelength();
	}
	
	private JPanel getJPanel0() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GroupLayout());
			panel.add(getLabelIcon(), new Constraints(new Leading(523, 80, 10, 10), new Leading(138, 80, 12, 12)));
			panel.add(getSliderLaser1(), new Constraints(new Leading(25, 10, 10), new Leading(30, 333, 10, 10)));
			panel.add(getButtonLaunch(), new Constraints(new Leading(325, 100, 100), new Leading(388, 12, 12)));
			panel.add(getSliderLaser2(), new Constraints(new Leading(725, 10, 10), new Leading(27, 332, 10, 10)));
			panel.add(getComboLaser1(), new Constraints(new Leading(483, 148, 10, 10), new Leading(39, 20, 12, 12)));
			panel.add(getComboLaser2(), new Constraints(new Leading(483, 148, 10, 10), new Leading(73, 20, 12, 12)));
			panel.add(getVerticalSeparator(), new Constraints(new Leading(421, 9, 10, 10), new Leading(35, 179, 10, 10)));
			panel.add(getScrollText(), new Constraints(new Leading(176, 409, 10, 10), new Leading(237, 136, 12, 12)));
			panel.add(getButtonCalibrateLaser2(), new Constraints(new Leading(471, 142, 146), new Leading(388, 12, 12)));
			panel.add(getButtonCalibrateLaser1(), new Constraints(new Leading(176, 142, 146), new Leading(388, 12, 12)));
			panel.add(getLabelSliderLaser1(), new Constraints(new Leading(9, 121, 10, 10), new Leading(373, 10, 10)));
			panel.add(getLabelSliderLaser2(), new Constraints(new Leading(634, 126, 10, 10), new Leading(373, 12, 12)));
			panel.add(getLabelTotalPower(), new Constraints(new Leading(453, 260, 10, 10), new Leading(114, 20, 12, 12)));
			panel.add(getLabelPointNumber(), new Constraints(new Leading(170, 10, 10), new Leading(28, 10, 10)));
			panel.add(getButtonAdd(), new Constraints(new Leading(139, 10, 10), new Leading(63, 10, 10)));
			panel.add(getButtonSupprimer(), new Constraints(new Leading(230, 10, 10), new Leading(63, 10, 10)));
			panel.add(getButtonRemoveAll(), new Constraints(new Leading(170, 10, 10), new Leading(103, 10, 10)));
			panel.add(getHorizontalSeparator(), new Constraints(new Leading(100, 280, 10, 10), new Leading(150, 9, 10, 10)));
			panel.add(getButtonShutter1(), new Constraints(new Leading(119, 10, 10), new Leading(175, 10, 10)));
			panel.add(getButtonShutter2(), new Constraints(new Leading(250, 10, 10), new Leading(175, 10, 10)));
		}
		return panel;
	}

	/*Window builder*/
	private void initComponents() {
		setTitle("Laser power calibration");
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setForeground(Color.black);
		add(getJPanel0(), BorderLayout.CENTER);
		setSize(775, 470);
		setResizable(false);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	/*Component settings*/
	private JButton getButtonShutter1() {
		if (buttonShutter1 == null) {
			buttonShutter1 = new JButton();
			buttonShutter1.setText("Block L1");
			buttonShutter1.addActionListener(this);
		}
		return buttonShutter1;
	}
	
	private JButton getButtonShutter2() {
		if (buttonShutter2 == null) {
			buttonShutter2 = new JButton();
			buttonShutter2.setText("Block L2");
			buttonShutter2.addActionListener(this);
		}
		return buttonShutter2;
	}
	
	private JLabel getLabelPointNumber() {
		if (labelPointNumber == null) {
			labelPointNumber = new JLabel();
			labelPointNumber.setText("Set point 1: ");
			labelPointNumber.setVisible(false);
		}
		return labelPointNumber;
	}
	
	private JButton getButtonAdd() {
		if (buttonAdd == null) {
			buttonAdd = new JButton();
			buttonAdd.addActionListener(this);
			buttonAdd.setText("Add");
			buttonAdd.setVisible(false);
		}
		return buttonAdd;
	}
	
	private JButton getButtonSupprimer() {
		if (buttonRemove == null) {
			buttonRemove = new JButton();
			buttonRemove.addActionListener(this);
			buttonRemove.setText("Remove");
			buttonRemove.setVisible(false);
		}
		return buttonRemove;
	}
	
	private JButton getButtonRemoveAll() {
		if (buttonRemoveAll == null) {
			buttonRemoveAll = new JButton();
			buttonRemoveAll.addActionListener(this);
			buttonRemoveAll.setText("Remove all");
			buttonRemoveAll.setVisible(false);
		}
		return buttonRemoveAll;
	}
	
	private JLabel getLabelIcon() {
		if (labelIcon == null) {
			labelIcon = new JLabel();
			labelIcon.setVisible(false);
		}
		return labelIcon;
	}
	
	private JLabel getLabelTotalPower() {
		if (labelTotalPower == null) {
			labelTotalPower = new JLabel();
			labelTotalPower.setText("Current total power : " + round(calculation.computePower(sliderLaser1.getValue(), sliderLaser2.getValue()), 3) + " mW");
			labelTotalPower.setVisible(true);
		}
		return labelTotalPower;
	}
	
	private JButton getButtonLaunch() {
		if (buttonLaunch == null) {
			buttonLaunch = new JButton();
			buttonLaunch.addActionListener(this);
			buttonLaunch.setText("Launch");
			buttonLaunch.setVisible(false);
		}
		return buttonLaunch;
	}
	

	private JSlider getSliderLaser1() {
		if (sliderLaser1 == null) {
			sliderLaser1 = new JSlider();
			sliderLaser1.addChangeListener(this);
			sliderLaser1.setOrientation(SwingConstants.VERTICAL);
			sliderLaser1.setValue(0);
			sliderLaser1.setVisible(false);
		}
		return sliderLaser1;
	}

	private JSlider getSliderLaser2() {
		if (sliderLaser2 == null) {
			sliderLaser2 = new JSlider();
			sliderLaser2.addChangeListener(this);
			sliderLaser2.setOrientation(SwingConstants.VERTICAL);
			sliderLaser2.setValue(0);
			sliderLaser2.setVisible(false);
		}
		return sliderLaser2;
	}
	

	private JLabel getLabelSliderLaser1() {
		if (labelSliderLaser1 == null) {
			labelSliderLaser1 = new JLabel();
			labelSliderLaser1.setText("Power L1 : " + valueSliderLaser1 + " %");
			labelSliderLaser1.setVisible(false);
		}
		return labelSliderLaser1;
	}

	private JLabel getLabelSliderLaser2() {
		if (labelSliderLaser2 == null) {
			labelSliderLaser2 = new JLabel();
			labelSliderLaser2.setText("Power L2 : " + valueSliderLaser2 + " %");
			labelSliderLaser2.setVisible(false);
		}
		return labelSliderLaser2;
	}
	
	private JButton getButtonCalibrateLaser1() {
		if (buttonCalibrateLaser1 == null) {
			buttonCalibrateLaser1 = new JButton();
			buttonCalibrateLaser1.setText("Calibration L1");
			buttonCalibrateLaser1.addActionListener(this);
		}
		return buttonCalibrateLaser1;
	}

	private JButton getButtonCalibrateLaser2() {
		if (buttonCalibrateLaser2 == null) {
			buttonCalibrateLaser2 = new JButton();
			buttonCalibrateLaser2.setText("Calibration L2");
			buttonCalibrateLaser2.addActionListener(this);
		}
		return buttonCalibrateLaser2;
	}

	private JScrollPane getScrollText() {
		if (scrollText == null) {
			scrollText = new JScrollPane();
			scrollText.setViewportView(getTextConsole());
			scrollText.getVerticalScrollBar().setValue(0);
		}
		return scrollText;
	}

	private JTextArea getTextConsole() {
		if (textConsole == null) {
			textConsole = new JTextArea();
			textConsole.setEditable(false);
	        Font police = new Font("Arial", Font.BOLD, 12);
	        textConsole.setFont(police);
	        updateConsole();
		}
		return textConsole;
	}

	private JComboBox getComboLaser1() {
		if (comboLaser1 == null) {
			comboLaser1 = new JComboBox();
			comboLaser1.setModel(new DefaultComboBoxModel(new Object[] { "Wavelength L1" }));
			comboLaser1.addItemListener(this);
			comboLaser1.setDoubleBuffered(false);
			comboLaser1.setBorder(null);
			
			listLaser1 = FileManager.searchFile("./L1", ".ml1");

			String liste = new String();
			
			for(int i = 0; i < listLaser1.size(); i++){
				liste = (String) listLaser1.get(i);
				liste = liste.replaceFirst(".ml1", " nm");
				comboLaser1.addItem(liste);
			}
		}
		return comboLaser1;
	}

	private JComboBox getComboLaser2() {
		if (comboLaser2 == null) {
			comboLaser2 = new JComboBox();
			comboLaser2.setModel(new DefaultComboBoxModel(new Object[] { "Wavelength L2" }));
			comboLaser2.addItemListener(this);
			comboLaser2.setDoubleBuffered(false);
			comboLaser2.setBorder(null);
			
			listLaser2 = FileManager.searchFile("./L2", ".ml2");

			String liste = new String();
			
			for(int i = 0; i < listLaser2.size(); i++){
				liste = (String) listLaser2.get(i);
				liste = liste.replaceFirst(".ml2", " nm");
				comboLaser2.addItem(liste);
			}
		}
		return comboLaser2;
	}

	private JSeparator getVerticalSeparator() {
		if (verticalSeparator == null) {
			verticalSeparator = new JSeparator();
			verticalSeparator.setOrientation(SwingConstants.VERTICAL);
		}
		return verticalSeparator;
	}
	
	private JSeparator getHorizontalSeparator() {
		if (horizontalSeparator == null) {
			horizontalSeparator = new JSeparator();
			horizontalSeparator.setOrientation(SwingConstants.HORIZONTAL);
		}
		return horizontalSeparator;
	}

	
	/*Action handlers*/
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == buttonLaunch){
			process.launchRegulation();
			RegulationWindow regulationWindow = new RegulationWindow(serial, laser1, laser2);
			dispose();
		}
		
		if(e.getSource() == buttonCalibrateLaser1){
			process.laserCalibration(1);
			CalibrationWindow calibrationWindow = new CalibrationWindow(1, process, this);
		}
		
		if(e.getSource() == buttonCalibrateLaser2){
			process.laserCalibration(2);
			CalibrationWindow calibrationWindow = new CalibrationWindow(2, process, this);
		}
		
		if(e.getSource() == buttonAdd){
			laser1.add(sliderLaser1.getValue());
			laser2.add(sliderLaser2.getValue());
			currentPoint++;
			labelPointNumber.setText("Set point " + (currentPoint) + " : ");
			process.addPoint(sliderLaser1.getValue(), sliderLaser2.getValue());
			updateConsole();
		}
		
		if(e.getSource() == buttonRemove){
			if(currentPoint > 1){
				laser1.remove(currentPoint - 1);
				laser2.remove(currentPoint - 1);
				currentPoint--;
				labelPointNumber.setText("Set point " + (currentPoint) + " : ");
				process.removePoint();
				updateConsole();
			}
		}
		
		if(e.getSource() == buttonRemoveAll){
			laser1.clear();
			laser2.clear();
			laser1.add(0);
			laser2.add(0);
			currentPoint = 1;
			labelPointNumber.setText("Set point " + (currentPoint) + " : ");
			process.removeAll();
			updateConsole();
		}
		
		if(e.getSource() == buttonShutter1){
			if(buttonShutter1.getText() == ("Block L1")){
				buttonShutter1.setText("Free L1");
				process.setElectro1();
			}
			
			else{
					buttonShutter1.setText("Block L1");
					process.unsetElectro1();
			}
		}
		
		
		if(e.getSource() == buttonShutter2){
			if(buttonShutter2.getText() == ("Block L2")){
				buttonShutter2.setText("Free L2");
				process.setElectro1();
			}
			
			else{
					buttonShutter2.setText("Block L2");
					process.unsetElectro1();
			}
		}
	}
	
	/*Combobox events*/
	public void stateChanged(ChangeEvent e) {
	    final JSlider source = (JSlider)e.getSource();
	    	if (e.getSource() == sliderLaser1 && ready) {
	    		  valueSliderLaser1 = (int)source.getValue();
	    		  labelSliderLaser1.setText("Power L1 : " + valueSliderLaser1 + " %");
	    		  process.laserManu(valueSliderLaser1, 1);
		    	  labelTotalPower.setText("Current total power : " + round(calculation.computePower(sliderLaser1.getValue(), sliderLaser2.getValue()), 3) + " mW");
	    	}
	    		 
	    	if (e.getSource() == sliderLaser2 && ready) {
	    		
	    		valueSliderLaser2 = (int)source.getValue();
	    		labelSliderLaser2.setText("Power L2 : " + valueSliderLaser2 + " %");
	    		process.laserManu(valueSliderLaser2, 2);	
	    		labelTotalPower.setText("Current total power : " + round(calculation.computePower(sliderLaser1.getValue(), sliderLaser2.getValue()), 3) + " mW");
	    	}
	    	
	    	if (calculation.computePower(valueSliderLaser1,valueSliderLaser2) > 250 && calculation.computePower(valueSliderLaser1,valueSliderLaser2) < 300){
	    		labelIcon.setIcon(new ImageIcon(getClass().getResource("/images/danger.png" )));
	    		labelIcon.setVisible(true);
	    	}
	    	
	    	else if (calculation.computePower(valueSliderLaser1,valueSliderLaser2) >= 300){
	    		labelIcon.setIcon(new ImageIcon(getClass().getResource("/images/erreur.png" )));
	    		labelIcon.setVisible(true);
	    	}
	    	
	    	else{
	    		labelIcon.setVisible(false);
	    	}
	}
	
	
	/*Item from combobox events*/
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == comboLaser1 || e.getSource() == comboLaser2){
    		if(("" + comboLaser1.getSelectedItem()).compareTo("Wavelength L1") != 0 && ("" + comboLaser2.getSelectedItem()).compareTo("Wavelength L2") != 0){
				
				System.out.println("" + comboLaser1.getSelectedItem());
				double[] firstValue;
				double[] secondValue;
	
				firstValue = FileManager.readValues(("" + comboLaser1.getSelectedItem()).replaceFirst(" nm", ".ml1"), 1);
				secondValue = FileManager.readValues(("" + comboLaser2.getSelectedItem()).replaceFirst(" nm", ".ml2"), 2);
				
				for(int l = 0; l < 11; l++){
					calibrationLaser1[l] = firstValue[l];
					calibrationLaser2[l] = secondValue[l];
				}
				
				calculation.setCurve(firstValue, secondValue);
				labelTotalPower.setText("Puissance totale actuelle : " + calculation.computePower(sliderLaser1.getValue(), sliderLaser2.getValue()) + " mW");
				
		    	if (calculation.computePower(valueSliderLaser1,valueSliderLaser2) > 250 && calculation.computePower(valueSliderLaser1,valueSliderLaser2) < 300){
		    		labelIcon.setIcon(new ImageIcon(getClass().getResource("/images/danger.png" )));
		    		labelIcon.setVisible(true);
		    	}
		    	
		    	else if (calculation.computePower(valueSliderLaser1,valueSliderLaser2) >= 300){
		    		labelIcon.setIcon(new ImageIcon(getClass().getResource("/images/erreur.png" )));
		    		labelIcon.setVisible(true);
		    	}
		    	
		    	else{
		    		labelIcon.setVisible(false);
		    	}
				
				calibrationMemorised = true;
				sliderLaser1.setVisible(true);
				sliderLaser2.setVisible(true);
				labelSliderLaser1.setVisible(true);
				labelSliderLaser2.setVisible(true);
				labelPointNumber.setVisible(true);
				buttonAdd.setVisible(true);
				buttonRemove.setVisible(true);
				buttonRemoveAll.setVisible(true);
				
				updateConsole();
    		}
    		
				
			else{
				calibrationMemorised = false;
				sliderLaser1.setVisible(false);
				sliderLaser2.setVisible(false);
				labelSliderLaser1.setVisible(false);
				labelSliderLaser2.setVisible(false);
				labelPointNumber.setVisible(false);
				buttonAdd.setVisible(false);
				buttonRemove.setVisible(false);
				buttonRemoveAll.setVisible(false);
				buttonLaunch.setVisible(false);
			}
    	}
		
		updateBackup();
	}
	
	/*Update the console at every change*/
	public void updateConsole() {
		if(!calibrationMemorised){
			textConsole.setBackground(new Color(255, 255, 255));
			textConsole.setText("Please choose the wavelength of lasers");
			buttonLaunch.setVisible(false);
		}
		
		else{
			textConsole.setBackground(new Color(255, 255, 255));
			textConsole.setText(generateConsoleText());
			buttonLaunch.setVisible(true); 
			
			if(laser1.size() < 3)
				buttonLaunch.setVisible(false);
			
			else
				buttonLaunch.setVisible(true); 
		}
		
		if(allowBackupUpdate){
			updateBackup();	
		}
	}
	
	
	String generateConsoleText(){
		String sentence = "Summary : \n\n";
		
		for(int i = 1; i < laser1.size(); i++){
			sentence = sentence + "Point " + i + " : " + "Laser 1 at " + laser1.get(i) + "% " + "and Laser 2 at " + laser2.get(i) + "%. \n";
		}
		
		return sentence;
	}
	
	
	/*Function recovering the wavelength from the previous session*/
	void getPreviousWavelength (){
		previousStateValues = FileManager.loadBackup();
		
		if(Integer.parseInt(previousStateValues.get(0)) == -1){
			previousStateValues.set(0, "0");
		}
		
		if(Integer.parseInt(previousStateValues.get(1)) == -1){
			previousStateValues.set(1, "0");
		}
		
		int conversion1 = Integer.parseInt(previousStateValues.get(0));
		int conversion2 = Integer.parseInt(previousStateValues.get(1));
			
		System.out.println(conversion1);
		System.out.println(conversion2);

		comboLaser1.setSelectedIndex(conversion1);
		comboLaser2.setSelectedIndex(conversion2);

		
		if(("" + comboLaser1.getSelectedItem()).compareTo("Wavelength L1") != 0 && ("" + comboLaser2.getSelectedItem()).compareTo("Wavelength L2") != 0){
			
			System.out.println("" + comboLaser1.getSelectedItem());
			double[] lecture1;
			double[] lecture2;

			lecture1 = FileManager.readValues(("" + comboLaser1.getSelectedItem()).replaceFirst(" nm", ".ml1"), 1);
			lecture2 = FileManager.readValues(("" + comboLaser2.getSelectedItem()).replaceFirst(" nm", ".ml2"), 2);
			
			for(int l = 0; l < 11; l++){
				calibrationLaser1[l] = lecture1[l];
				calibrationLaser2[l] = lecture2[2];
			}
		
			calibrationMemorised = true;
			
			sliderLaser1.setVisible(true);
			sliderLaser2.setVisible(true);
			labelSliderLaser1.setVisible(true);
			labelSliderLaser2.setVisible(true);	
			labelPointNumber.setVisible(true);
			buttonAdd.setVisible(true);
			buttonRemove.setVisible(true);
			buttonRemoveAll.setVisible(true);
			updateConsole();
		}
		allowBackupUpdate = true;
	}
	
	/*Function updating the file containing current wavelengths*/
	void updateBackup(){
		
		previousStateValues.set(0, "" + comboLaser1.getSelectedIndex());
		previousStateValues.set(1, "" + comboLaser2.getSelectedIndex());
		
		FileManager.writeBackup(previousStateValues);
	}
	 
	public double round(double what, int howmuch) {
		return (double)( (int)(what * Math.pow(10,howmuch) + .5) ) / Math.pow(10,howmuch);
	}

	/*This function is called when a new calibration is created*/
	public void miseAJourCombo() {
		comboLaser1.removeAllItems();
		comboLaser1.addItem("Wavelength L1");
		comboLaser2.removeAllItems();
		comboLaser2.addItem("Wavelength L2");
		
		listLaser1 = FileManager.searchFile("./L1", ".ml1");

		String liste = new String();
		
		for(int i = 0; i < listLaser1.size(); i++){
			liste = (String) listLaser1.get(i);
			liste = liste.replaceFirst(".ml1", " nm");
			comboLaser1.addItem(liste);
		}
		
		listLaser2 = FileManager.searchFile("./L2", ".ml2");
		
		for(int i = 0; i < listLaser2.size(); i++){
			liste = (String) listLaser2.get(i);
			liste = liste.replaceFirst(".ml2", " nm");
			comboLaser2.addItem(liste);
		}
	}
}