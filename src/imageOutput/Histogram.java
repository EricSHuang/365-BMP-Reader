package imageOutput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Histogram extends JPanel{
	private static final long serialVersionUID = 1L;
	private int[] values;
	private int maxValue;
	private static Dimension histogramDimension = new Dimension(256, 300);
	
	public Histogram(int[] values, int maxValue) {
		this.values = values;
		this.maxValue = maxValue;
	}
	
	//Draws the Histograms
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		
		double yScale = ((double)getHeight() / maxValue);
		//System.out.println("yScale: "+yScale);
		ArrayList<Point> numPerValue = new ArrayList<>();
		for(int i=0; i < values.length; i++) {
			int x = i;
			int y = (int) Math.round((double)values[i] * yScale);
			numPerValue.add(new Point(x,y));
			//System.out.printf("x: %d, y: %d\n", x, y);
		}
		
		//Draw the histogram
		for(int i=0; i < values.length; i++) {
			int x = numPerValue.get(i).x;
			int y1 = getHeight();
			int y2 = numPerValue.get(i).y;
			g2d.drawLine(x, y1, x, y2);
		}
	}
	
	
	public static JPanel drawRedHistogram(int[][] bitmap) {
		int[] redValues = new int[256];
		for(int i=0; i < 255; i++) {
			redValues[i] = 0;
		}
		for(int i=0; i < bitmap.length; i++) {
			int value = bitmap[i][0];
			redValues[value]++;
		}
		Histogram red = new Histogram(redValues, max(redValues));
		red.setPreferredSize(histogramDimension);
		return red;
	}
	
	
	public static JPanel drawGreenHistogram(int[][] bitmap) {
		int[] greenValues = new int[256];
		for(int i=0; i < 255; i++) {
			greenValues[i] = 0;
		}
		for(int i=0; i < bitmap.length; i++) {
			int value = bitmap[i][1];
			greenValues[value]++;
		}
		Histogram green = new Histogram(greenValues, max(greenValues));
		green.setPreferredSize(histogramDimension);
		return green;
	}
	
	
	public static JPanel drawBlueHistogram(int[][] bitmap) {
		int[] blueValues = new int[256];
		for(int i=0; i < 255; i++) {
			blueValues[i] = 0;
		}
		for(int i=0; i < bitmap.length; i++) {
			int value = bitmap[i][2];
			blueValues[value]++;
		}
		Histogram blue = new Histogram(blueValues, max(blueValues));
		blue.setPreferredSize(histogramDimension);
		return blue;
	}
	
	//Returns the maximum value in an array
	private static int max(int[] array) {
		int tempMax = 0;
		for(int i=0; i < array.length; i++) {
			if (array[i] > tempMax) {
				tempMax = array[i];
			}
		}
		return tempMax;
	}
	
}