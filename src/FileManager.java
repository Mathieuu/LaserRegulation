import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*Class containing the load and save the calibration values from a file*/
public class FileManager {

	/*Create a file and write calibration values*/
	static void writeValues(String fileName, double[] data, int laser){
		
		File f;
		
		if(laser == 1) 
			f = new File("./L1/" + fileName);
		else 
			f = new File("./L2/" + fileName);
		
		try {
		    FileWriter fw = new FileWriter(f);
		    
		    for (double d : data) {
		        fw.write (String.valueOf (d));
		        fw.write ("\r\n");
		    }
		    fw.close();
		}
		catch (IOException exception) {
		     System .out.println ( "Reading error: " + exception.getMessage());
		}
	}
	
	/*Read from a file the calibration values*/
	static double[] readValues(String fileName, int laser){
		
	    double[] data = new double[11];
		
		try
		{
		    File f = new File ("./L" + laser + "/" + fileName);
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try{   
		        for (int i = 0; i < 11; i++){
		        	data[i] = Double.valueOf(br.readLine());		        	
		        }
		 
		        br.close();
		        fr.close();
		    }
		    
		    catch (IOException exception){
		        System.out.println ("Reading error: " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception) {
		    System.out.println ("The file has not been found");
		}
		
		return data;
	}
	
	/*List all files from a folder with a given extension*/
	static public  ArrayList<String> searchFile(String chemin, String extension) {
		
		File directory = new File(chemin);
		File[] subfiles = directory.listFiles();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		String nom = new String();
		
		for(int i=0 ; i<subfiles.length; i++){
			nom = subfiles[i].getName();
			
			if(nom.endsWith(extension)){
				if(nom.length() == 7){
					list.add(nom);
				}
				
				if(nom.length() == 8){
					list2.add(nom);
				}
			}
		}
		Collections.sort(list);
		Collections.sort(list2);
		
		list.addAll(list2);
		
		return list;
	}
	
	/*Load wavelengths from the previous session*/
	 static ArrayList<String> loadBackup(){
			
		ArrayList<String> liste = new ArrayList<String>();
		 
		try
		{
		    File f = new File ("backup.ml0");
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try{   
		        for (int i = 0; i < 2; i++){
		    		liste.add(br.readLine());
		    		if(liste.get(i) == null){
		    			liste.set(i, "" + 0);
		    		}
		        }
		 
		        br.close();
		        fr.close();
		    }
		    
		    catch (IOException exception){
		        System.out.println ("Reading error: " + exception.getMessage());
		        liste.set(0, "0");
		    }
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("The file has not been found");
	        liste.set(0, "0");
		}
		
		return liste;
	}
	 
 	/*Update the backup file with the wavelenght used*/
	static void writeBackup(ArrayList<String> liste){
		
		File f;
		
		f = new File("backup.ml0");
		
		if(f.exists())
			f.delete();
	
		try {
		    FileWriter fw = new FileWriter(f);
		    
		     for (int i = 0; i < liste.size(); i++)
		    {
		        fw.write (liste.get(i));
		        fw.write ("\r\n");
		    }
		 
		    fw.close();
		}
		catch (IOException exception){
			System .out.println ( "Reading error: " + exception.getMessage());
		}
	}
}
