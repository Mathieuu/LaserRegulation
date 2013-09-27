/*Class calculating the curve from key points*/
public class CalculationPoints {
	
	/*Instance variables*/
	double[] coef1, coef2;
	double[] offset1, offset2;
	
	/*Constructor*/
	CalculationPoints(){
		coef1 = new double[10];
		coef2 = new double[10];
		offset1 = new double[10];
		offset2 = new double[10];
	}
	
	/*Method computing the equation*/
	void setCurve(double[] calibrationL1, double[] calibrationL2){
		
		for(int i = 0; i < 10; i++){
			coef1[i] = (double)(calibrationL1[i+1]-calibrationL1[i]) / (double)((i+1)*10 - i*10);
			coef2[i] = (double)(calibrationL2[i+1]-calibrationL2[i]) / (double)((i+1)*10 - i*10);
			offset1[i] = (double)calibrationL1[i] - (coef1[i]*(double)i*10);
			offset2[i] = (double)calibrationL2[i] - (coef2[i]*(double)(i*10));
			System.out.println("Equation " + i + " Coef : " + coef1[i] + " Offset : " + offset1[i]);
		}
	}
	
	/*Method used to search for the first point*/
	double seekPoint1(int p){
		
		double point = (double)p;
		
		for (int i = 0; i < 10; i++)
			if(point >= i*10 && point <= (i+1)*10)
				return (point*coef1[i] + offset1[i]);
		
		return 0;
	}
	
	/*Method used to search for the second point*/
	double seekPoint2(int p){
		
		double point = (double)p;
		
		for (int i = 0; i < 10; i++){
			if(point >= i*10 && point <= (i+1)*10){
				return (point*coef2[i] + offset2[i]);
			}
		}
		
		return 0;
	}
	
	/*Method used to determine the power to use for a specific segment of the curve*/
	double computePower(int p1, int p2){
		return (seekPoint1(p1) + seekPoint2(p2));
	}
}
