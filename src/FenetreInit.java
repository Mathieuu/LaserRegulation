import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

/*Class displaying a window asking to wait during the initialization of the mechanical installation*/
public class FenetreInit extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/*Instance variables*/
	private JLabel labelInitialisation, labelGif;
	private JPanel panel;
	
	/*Constructor*/
	public FenetreInit() {
		initComponents();
	}


	/*Window builder*/
	private void initComponents() {
		setTitle("Please wait...");
		add(getJPanel0(), BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 155);
		setResizable(false);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/*Component settings*/
	private JPanel getJPanel0() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GroupLayout());
			panel.add(getLabelInitialisation(), new Constraints(new Leading(35, 396, 12, 12), new Leading(18, 74, 12, 12)));
			panel.add(getLabelGif(), new Constraints(new Leading(449, 68, 12, 12), new Leading(35, 45, 10, 10)));
		}
		return panel;
	}

	private JLabel getLabelInitialisation() {
		if (labelInitialisation == null) {
			labelInitialisation = new JLabel();
			labelInitialisation.setFont(new Font("Dialog", Font.BOLD, 50));
			labelInitialisation.setText("Initialisation...");
		}
		return labelInitialisation;
	}
	
	private JLabel getLabelGif() {
		if (labelGif == null) {
			labelGif = new JLabel(new ImageIcon(getClass().getResource("/images/loading.gif" )));
		}
		return labelGif;
	}

}

