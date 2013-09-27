import java.io.File;
import java.io.IOException;

import javax.comm.*;
public class Regulation {

	//IN ORDER TO USE IT IN REAL CONDITIONS, UNCOMMENT 
	//THE CONTENTS OF THE METHODS IN COMMUNICATION AND THE 4
	//FOLLOWING LINES
	
	public static void main(String[] args) throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {

			File fb1 = new File("./L1/");
			fb1.mkdir(); 
		
			File fb2 = new File("./L2/");
			fb2.mkdir(); 
				
			FenetreInit fenetreInit = new FenetreInit(); 
			
			Communication serial = new Communication();
			/*	
			serial.delay(3000);
			serial.ecrire(120);
			
			
			while(serial.lire() != 121)
				serial.delay(100);
			//*/
			
			fenetreInit.dispose();
			
			MainWindow w = new MainWindow(serial);
	}
}
