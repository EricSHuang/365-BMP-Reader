package imageOutput;

public class BrightenBitmap{
	public static int[][] brightenBitmap(int[][] bitmap){
		double brighteningFactor = 1.5;
		int[][] brightenedBitmap = new int[bitmap.length][bitmap[0].length];
		for(int i=0; i < bitmap.length; i++) {
			double r = bitmap[i][0];
			double g = bitmap[i][1];
			double b = bitmap[i][2];
			
			//Convert to YUV
			double y = (0.299*r) + (0.587*g) + (0.114*b);
			double brightenedY = y * brighteningFactor;
			double u = (-0.299*r) + (-0.587*g) + (0.886*b);
			double v = (0.701*r) + (-0.587*g) + (-0.114*b);
			
			brightenedY = colorInRange(brightenedY);
			u = colorInRange(u);
			v = colorInRange(v);
			
			int brightenedR = (int) (brightenedY + (1.403*v));
			int brightenedG = (int) (brightenedY - (0.334*u) - (0.714*v));
			int brightenedB = (int) (brightenedY + (1.77*u));
			
			brightenedR = colorInRange(brightenedR);
			brightenedG = colorInRange(brightenedG);
			brightenedB = colorInRange(brightenedB);
			
			brightenedBitmap[i][0] = brightenedR;
			brightenedBitmap[i][1] = brightenedG;
			brightenedBitmap[i][2] = brightenedB;
		}
		return brightenedBitmap;
		
	}
	
	//Checks that colors do not leave the range of 0-255 
	private static double colorInRange(double color) {
		if (color > 255) {
			color = 255;
		}
		if (color < 0) {
			color = 0;
		}
		return color;
	}
	
	//Checks that colors do not leave the range of 0-255
	private static int colorInRange(int color) {
		if (color > 255) {
			color = 255;
		}
		if (color < 0) {
			color = 0;
		}
		return color;
	}	
}