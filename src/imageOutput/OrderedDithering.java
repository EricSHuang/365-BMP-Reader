package imageOutput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
//import javax.swing.JFrame;
import javax.swing.JPanel;

public class OrderedDithering extends JPanel{
	private static final long serialVersionUID = 1L;
	private final int[] ditherMatrixRow1 = {0, 8, 2, 10};
	private final int[] ditherMatrixRow2 = {12, 4, 14, 6};
	private final int[] ditherMatrixRow3 = {3, 11, 1, 9};
	private final int[] ditherMatrixRow4 = {15, 7, 13, 5};
	private final int[][] ditherMatrix = {ditherMatrixRow1, ditherMatrixRow2, ditherMatrixRow3, ditherMatrixRow4};
	private final int ditherMatrixSize = 4;
	private final int gapSize = 255 / (ditherMatrixSize * ditherMatrixSize);
	
	private int[][] bitmap;
	private int width;
	//private int height;
	
	public OrderedDithering(int[][]bitmap, int width, int height) {
		this.bitmap = bitmap;
		this.width = width;
		//this.height = height;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		ArrayList<Point> decodedCoord = new ArrayList<>();
		for(int i=0; i < bitmap.length; i++) {
			int y = Math.floorDiv(i, width);
			int x = i - (width* y);
			decodedCoord.add(new Point(x,y));
			//System.out.printf("x: %d, y: %d\n", x, y);
		}
		
		//Applying the dither matrix
		for(int i=0; i < bitmap.length; i++) {
			int greyColor = bitmap[i][0];
			int x = decodedCoord.get(i).x;
			int y = decodedCoord.get(i).y;
			int ditherX = x % ditherMatrixSize;
			int ditherY = y % ditherMatrixSize;
			int ditheringVal = ditherMatrix[ditherX][ditherY];
			if ( greyColor > (ditheringVal * gapSize)) {
				g2d.setColor(Color.WHITE);
			}
			else {
				g2d.setColor(Color.BLACK);
			}
			g2d.drawLine(x, y, x, y);
		}
	}
	
	//Applies ordered dithering on the image.
	//The bitmap should be in grey-scale.
	public static JPanel ditherImage(int[][]bitmap, int width, int height) {
		OrderedDithering image = new OrderedDithering(bitmap, width, height);
		image.setPreferredSize(new Dimension(width, height));
	/*	JFrame frame = new JFrame();
		frame.setTitle("Dithering Image");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(image);
		frame.pack();
		frame.setVisible(true);
	*/
		return image;
	}
}