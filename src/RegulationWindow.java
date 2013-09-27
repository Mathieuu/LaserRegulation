import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/*Class displaying the regulation window when the process is launch*/
public class RegulationWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	/*Declaration of the components*/
	private JLabel labelPower, labelState, labelGif;
	private JPanel panelProcessState, panelPowerDisplay, panelPowerControl, panelStop;
	private JButton buttonIncreaseL1, buttonReduceL1, buttonIncreaseL2, buttonReduceL2, buttonStop;
	
	/*Other instance variables*/
	Communication serial;
	ArrayList<Integer> laser1, laser2;
	Timer timer;
	
	int val = 0;
	
	/*Constructor for a normal usage*/
	public RegulationWindow(Communication com, ArrayList<Integer> l1, ArrayList<Integer> l2) {
		serial = com;
		initComponents();
		laser1 = l1;
		laser2 = l2;
		timer = new Timer(200, this);
		timer.start();
		serial.setDataAvailable(false);
	}
	
	/*Constructor for the demo test*/
	public RegulationWindow() {
		initComponents();
	}
	
	/*Window builder*/
	private void initComponents() {
		
		setTitle("Regulation in progress ...");
		setSize(620, 300);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelProcessState = new JPanel();
		panelProcessState.add(getLabelGif());
		panelProcessState.add(getLabelState());
		
		panelPowerDisplay = new JPanel();	
		panelPowerDisplay.add(getLabelPower());
		
		panelPowerControl = new JPanel();
		panelPowerControl.add(getButtonInscreaseL1());
		panelPowerControl.add(getButtonReduceL1());
		panelPowerControl.add(getButtonIncreaseL2());
		panelPowerControl.add(getButtonReduceL2());
		
		panelStop = new JPanel();
		panelStop.add(getButtonStop());
		
		this.setLayout(new GridLayout(4, 1));
		this.getContentPane().add(panelProcessState);
	    this.getContentPane().add(panelPowerDisplay);
	    this.getContentPane().add(panelPowerControl);
	    this.getContentPane().add(panelStop);
	    this.setVisible(true);
		
		setResizable(false);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	/*Component settings*/
	private JLabel getLabelGif() {
		
		if (labelGif == null) {
			labelGif = new JLabel(new ImageIcon(getClass().getResource("/images/loading.gif" )));
		}
		return labelGif;
	}
	
	private JLabel getLabelState() {
		
		if (labelState == null) {
			labelState = new JLabel();
			labelState.setFont(new Font("Dialog", Font.BOLD, 30));
			labelState.setText("Process in progress...");
			labelState.setPreferredSize(new Dimension(360, 40));
			labelState.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return labelState;
	}

	private JLabel getLabelPower() {
		
		if (labelPower == null) {
			labelPower = new JLabel();
			labelPower.setText("Current power: 1000 mW");
		}
		return labelPower;
	}

	private JButton getButtonInscreaseL1() {
		
		if (buttonIncreaseL1 == null) {
			buttonIncreaseL1 = new JButton();
			buttonIncreaseL1.addActionListener(this);
			buttonIncreaseL1.setText("Increase L1");
		}
		return buttonIncreaseL1;
	}
	
	private JButton getButtonIncreaseL2() {
		
		if (buttonIncreaseL2 == null) {
			buttonIncreaseL2 = new JButton();
			buttonIncreaseL2.addActionListener(this);
			buttonIncreaseL2.setText("Increase L2");
		}
		return buttonIncreaseL2;
	}
	
	private JButton getButtonReduceL1() {
		
		if (buttonReduceL1 == null) {
			buttonReduceL1 = new JButton();
			buttonReduceL1.addActionListener(this);
			buttonReduceL1.setText("Reduce L1");
		}
		return buttonReduceL1;
	}
	
	private JButton getButtonReduceL2() {
		
		if (buttonReduceL2 == null) {
			buttonReduceL2 = new JButton();
			buttonReduceL2.addActionListener(this);
			buttonReduceL2.setText("Reduce L2");
		}
		return buttonReduceL2;
	}
	
	private JButton getButtonStop() {
		
		if (buttonStop == null) {
			buttonStop = new JButton();
			buttonStop.addActionListener(this);
			buttonStop.setText("Stop");
		}
		return buttonStop;
	}

	
	/*Action handlers*/
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == buttonStop){
			serial.write(125);
			MainWindow mainWindow = new MainWindow(serial, laser1, laser2);
			dispose();
			timer.stop();
		}
		
		if(e.getSource() == buttonIncreaseL1){
			serial.write(108);
		}
		
		if(e.getSource() == buttonReduceL1){
			serial.write(110);
		}
		
		if(e.getSource() == buttonIncreaseL2){
			serial.write(109);
		}
		
		if(e.getSource() == buttonReduceL2){
			serial.write(111);
		}
		
		if(e.getSource() == timer){
			if(serial.getDataAvailable()){
				System.out.println(serial.getTest());
				serial.setDataAvailable(false);
				labelPower.setText("Current power : " + serial.read() + "mW.");
			}
		}
	}
}
