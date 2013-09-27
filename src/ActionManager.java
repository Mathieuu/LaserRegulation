/*Class translating an action into an integer transmitted to the arduino card */
public class ActionManager {
	
	Communication serial;
	
	int laserManu;
	int calibration;
	
	/*Constructor*/
	ActionManager(Communication com){
		 serial = com;
	}
	
	/*Add a calibration point*/
	void addPoint(int l1, int l2){
		serial.write(121);
		serial.write(l1);
		serial.write(l2);
	}
	
	/*Remove a calibration point*/
	void removePoint(){
		serial.write(122);
	}
	
	/*Remove all calibration points*/
	void removeAll(){
		serial.write(123);
	}
	
	/*Allow to set the laser to a specific power*/
	void laserManu(int val, int laser){
		
		if (laser != laserManu){
			laserManu = laser;
			
			if(laserManu == 1)
				serial.write(101);
				
			else if(laserManu == 2)
				serial.write(103);
		}		
		serial.write(val);
	}
	
	/*Calibrate the laser to a specific value*/
	void laserCalibration(int l){
		if(l == 1){
			serial.write(113);
			calibration = 1;
		}
		
		else{
			serial.write(116);
			calibration = 2;
		}
	}
	
	/*Launch the regulation process*/
	public void launchRegulation() {
		serial.write(124);
	}
	
	/*Increase manually the power*/
	void calibrationUp(){
		if(calibration == 1)
			serial.write(114);
		
		else
			serial.write(117);
	}
	
	/*Decrease manually the power*/
	void calibrationDown(){
		if(calibration == 1)
			serial.write(115);
		
		else
			serial.write(118);
	}
	
	/*Shut down the laser 1*/
	void setElectro1(){
			serial.write(104);
	}
	
	void unsetElectro1(){
		serial.write(105);
	}
	
	/*Shut down the laser 1*/
	void setElectro2(){
		serial.write(106);
	}
	
	void unsetElectro2(){
		serial.write(107);
	}
}
