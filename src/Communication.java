import java.io.InputStream;
import java.io.OutputStream;

import javax.comm.*;

import com.sun.comm.Win32Driver;

//IN ORDER TO USE IT IN REAL CONDITIONS, UNCOMMENT THE CONTENTS OF THE FOLLOWING METHODS

/*Object managing serial port communication
Compatible ONLY with window
To use it on a different OS, this class must be rewritten
Change the line portId=CommPortIdentifier.getPortIdentifier("COM3"); with the port where the arduino card is plugged */
public class Communication implements SerialPortEventListener{
	
	/*Instance variables*/
	InputStream in;
	OutputStream out; 
	
	Win32Driver w32Driver;
	CommPortIdentifier portId;
	
	SerialPort port;
	
	boolean dataAvalaible;
	int testValue;
	
	Communication() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException{
/*		w32Driver= new Win32Driver();
		w32Driver.initialize();
		
		portId=CommPortIdentifier.getPortIdentifier("COM3");  //replace COMXX by the port number used by the arduino card
	
		port=(SerialPort)portId.open("Laser regulation", 10000);
		
		port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	
		try {
			out = port.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in = port.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			port.addEventListener(this);
		} catch (TooManyListenersException e1) {
			e1.printStackTrace();
		}
		
		port.notifyOnDataAvailable(true);
		*/
	}
	
	void write(byte val){
		/*
		try {
			out.write(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(val);
		*/
	}
	
	void write(int val){
		/*
		try {
			out.write((byte)val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(val);
		*/
	}
	
	void write(char val){
		/*
		try {
			out.write((byte)val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	byte read(){
	/*
		try {
			val = (byte)in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		byte val = 0;
		return val;
	}
	
	void listPort(){
	/*
		Enumeration portList=CommPortIdentifier.getPortIdentifiers();
		
		while (portList.hasMoreElements()){
			portId=(CommPortIdentifier)portList.nextElement();
			System.out.println(portId.getName());
		}
		*/
	}
	
	void delay(int val){
	/*
		try{Thread.sleep(val);}catch(Exception e) {};
		*/
	}
		
	void close(){
	/*
		port.close();
		*/
	}

	int getTest(){
		return testValue;
	}
	
	void setTest(int p){
	/*
		testValue = p;
		*/
	}
	
	boolean getDataAvailable(){
		return dataAvalaible;
	}
	
	void setDataAvailable(boolean p){
	/*
		dataAvalaible = p;
		*/
	}
	
	public void serialEvent(SerialPortEvent e) {
	/*	
		dataAvalaible = true;
		try {
			testValue = in.available();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		*/
	}
}