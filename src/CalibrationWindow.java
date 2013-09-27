import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

/* Class displaying the window used to calibrate a laser */
public class CalibrationWindow extends JFrame implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	/*Declaration of the components*/
	private JLabel labelCalibrationLaser, labelWavelength, labelPowerMesured, labelMW;
	private JTextField textPower;
	private JButton buttonPrevious, buttonNext, buttonSave, buttonOk;
	private JTextArea text;
	private JScrollPane scrollText;
	private JTextField textWavelength;
	private JPanel panel;
	private JOptionPane msgBoxError;
	private MainWindow window;

	/*Declaration of the other instance variables*/
	ActionManager actionManager;
	int laser = 0;
	int wavelength = 0;
	int i = 0;
	double[] power = new double[11];

	/*Constructor called when the window must be redrawn*/
	public CalibrationWindow() {
		initComponents();
	}

	/*Constructor called the first time*/
	public CalibrationWindow(int l, ActionManager t, MainWindow w) {
		laser = l;
		actionManager = t;
		window = w;
		initComponents();
	}

	/*Window builder*/
	private void initComponents() {
		add(getPanel(), BorderLayout.CENTER);
		setSize(614, 410);
		setTitle("Calibration");
		setLocationRelativeTo(this);
		setAlwaysOnTop(true);
		setResizable(false);
		setVisible(true);
	}

	/*Component settings*/
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GroupLayout());
			panel.add(getLabelCalibrationLaser(), new Constraints(new Leading(204, 250, 10,
					10), new Leading(15, 37, 10, 10)));
			panel.add(getLabelWaveLength(), new Constraints(new Leading(153, 10, 10),
					new Leading(83, 10, 10)));
			panel.add(getTextWavelength(), new Constraints(new Leading(278,
					119, 12, 12), new Leading(82, 12, 12)));
			panel.add(getButtonOk(), new Constraints(
					new Leading(418, 10, 10), new Leading(79, 12, 12)));
			panel.add(getScrollText(), new Constraints(new Leading(320,
					284, 10, 10), new Leading(168, 196, 10, 10)));
			panel.add(getLabelPowerMesured(), new Constraints(new Leading(23, 12, 12),
					new Leading(172, 10, 10)));
			panel.add(getTextPower(), new Constraints(new Leading(114,
					81, 10, 10), new Leading(213, 10, 10)));
			panel.add(getLabelMW(), new Constraints(new Leading(208, 12, 12),
					new Leading(215, 12, 12)));
			panel.add(getButtonPrevious(), new Constraints(new Leading(23,
					12, 12), new Leading(292, 10, 10)));
			panel.add(getButtonNext(), new Constraints(new Leading(159,
					124, 10, 10), new Leading(292, 10, 10)));
			panel.add(getButtonSave(), new Constraints(new Leading(23,
					262, 10, 10), new Leading(340, 10, 10)));
		}
		return panel;
	}

	private JButton getButtonSave() {
		if (buttonSave == null) {
			buttonSave = new JButton();
			buttonSave.setText("Save and quit");
			buttonSave.addActionListener(this);
		}
		return buttonSave;
	}

	private JLabel getLabelCalibrationLaser() {
		if (labelCalibrationLaser == null) {
			labelCalibrationLaser = new JLabel();
			labelCalibrationLaser.setFont(new Font("Dialog", Font.BOLD, 22));
			labelCalibrationLaser.setText("Calibration laser " + laser);
		}
		return labelCalibrationLaser;
	}

	private JLabel getLabelWaveLength() {
		if (labelWavelength == null) {
			labelWavelength = new JLabel();
			labelWavelength.setText("Wavelength: ");
		}
		return labelWavelength;
	}

	private JTextField getTextPower() {
		if (textPower == null) {
			textPower = new JTextField(new Double("1.00000").toString());
			textPower.setText("0");
			textPower.addKeyListener(this);
		}
		return textPower;
	}

	private JButton getButtonOk() {
		if (buttonOk == null) {
			buttonOk = new JButton();
			buttonOk.setText("Ok");
			buttonOk.addActionListener(this);
		}
		return buttonOk;
	}

	private JLabel getLabelMW() {
		if (labelMW == null) {
			labelMW = new JLabel();
			labelMW.setText("mW");
		}
		return labelMW;
	}

	private JButton getButtonPrevious() {
		if (buttonPrevious == null) {
			buttonPrevious = new JButton();
			buttonPrevious.setText("Previous point");
			buttonPrevious.addActionListener(this);
		}
		return buttonPrevious;
	}

	private JButton getButtonNext() {
		if (buttonNext == null) {
			buttonNext = new JButton();
			buttonNext.setText("Next point");
			buttonNext.addActionListener(this);
		}
		return buttonNext;
	}

	private JTextArea getText() {
		if (text == null) {
			text = new JTextArea();
			text.setText("Wavelength " + wavelength
					+ " nm\n\n- Point 1 : " + power[0] + " mW"
					+ "\n- Point 2 : " + power[1] + " mW"
					+ "\n- Point 3 : " + power[2] + " mW"
					+ "\n- Point 4 : " + power[3] + " mW"
					+ "\n- Point 5 : " + power[4] + " mW"
					+ "\n- Point 6 : " + power[5] + " mW"
					+ "\n- Point 7 : " + power[6] + " mW"
					+ "\n- Point 8 : " + power[7] + " mW"
					+ "\n- Point 9 : " + power[8] + " mW"
					+ "\n- Point 10 : " + power[9] + " mW"
					+ "\n- Point 11 : " + power[10] + " mW");
			text.setCaretPosition(0);
		}
		return text;
	}

	private JScrollPane getScrollText() {
		if (scrollText == null) {
			scrollText = new JScrollPane();
			scrollText.setViewportView(getText());
			scrollText.getVerticalScrollBar().setValue(0);
		}
		return scrollText;
	}

	private JLabel getLabelPowerMesured() {
		if (labelPowerMesured == null) {
			labelPowerMesured = new JLabel();
			labelPowerMesured.setText("Power mesured at the point 1/11:");
		}
		return labelPowerMesured;
	}

	private JTextField getTextWavelength() {
		if (textWavelength == null) {
			textWavelength = new JTextField(new Double("1.00000").toString());
			textWavelength.setText("0");
			textWavelength.addKeyListener(this);
		}
		return textWavelength;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			if (textWavelength.isFocusOwner()) {
				wavelength = (int) Double.parseDouble(textWavelength
						.getText().replaceAll(",", ""));
				loadExistingValues();
				updateConsole();
			}

			else if (textPower.isFocusOwner()) {
				power[i] = Double.parseDouble(textPower.getText()
						.replaceAll(",", ""));
				updateConsole();
				if (i == 10)
					i = 0;
				else
					i++;
				labelPowerMesured.setText("Power mesured for point " + (i + 1) + " / 11 :");
				textPower.setText("" + power[i]);
				actionManager.calibrationUp();
			}
		}
	}

	/* Action handlers */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonOk) {
			wavelength = (int) Double.parseDouble(textWavelength.getText());
			loadExistingValues();
			updateConsole();
		}

		if (e.getSource() == buttonNext) {
			power[i] = Double.parseDouble(textPower.getText().replaceAll(" ", "").replaceAll(",", "")
					.replaceAll(",", ""));
			updateConsole();
			if (i == 10)
				i = 0;
			else
				i++;
			labelPowerMesured.setText("Power mesured for point " + (i + 1) + " / 11 :");
			textPower.setText("" + power[i]);
			actionManager.calibrationUp();
		}

		if (e.getSource() == buttonPrevious) {
			power[i] = Double.parseDouble(textPower.getText().replaceAll(" ", "").replaceAll(",", "")
					.replaceAll(",", ""));
			updateConsole();
			if (i == 0)
				i = 10;
			else
				i--;
			labelPowerMesured.setText("Power mesured for point " + (i + 1) + " / 11 :");
			textPower.setText("" + power[i]);
			actionManager.calibrationDown();
		}

		if (e.getSource() == buttonSave) {
			if (wavelength != 0) {
				if (laser == 1)
					FileManager.writeValues(wavelength + ".ml1", power, laser);
				else
					FileManager.writeValues(wavelength + ".ml2", power, laser);
				window.miseAJourCombo();
				dispose();
			}

			else {
				msgBoxError = new JOptionPane();
				JOptionPane.showMessageDialog(this,
						"The wavelength is equal to 0", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	/*Update the console with the new point values*/
	public void updateConsole() {
		text.setText("Wavelength : " + wavelength
				+ " nm\n\n- Point 1 : " + power[0] + " mW"
				+ "\n- Point 2 : " + power[1] + " mW" + "\n- Point 3 : "
				+ power[2] + " mW" + "\n- Point 4 : " + power[3]
				+ " mW" + "\n- Point 5 : " + power[4] + " mW"
				+ "\n- Point 6 : " + power[5] + " mW" + "\n- Point 7 : "
				+ power[6] + " mW" + "\n- Point 8 : " + power[7]
				+ " mW" + "\n- Point 9 : " + power[8] + " mW"
				+ "\n- Point 10 : " + power[9] + " mW" + "\n- Point 11 : "
				+ power[10] + " mW");
		if (i * 23 < 200)
			text.setCaretPosition(i * 23);
	}

	public void loadExistingValues() {
		ArrayList<String> list = new ArrayList<String>();
		double[] reading;

		list.addAll(FileManager.searchFile("./L" + laser, ".ml" + laser));

		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).compareTo("" + wavelength + ".ml" + laser) == 0) {
				reading = FileManager.readValues("" + wavelength + ".ml" + laser,
						laser);

				for (int k = 0; k < 11; k++) {
					power[k] = reading[k];
				}

				textPower.setText("" + power[i]);
			}
		}
	}
}
