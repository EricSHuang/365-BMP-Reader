package imageOutput;

import java.awt.*;
import java.util.ArrayList;
//import javax.swing.JFrame;
import javax.swing.JPanel;


public class DrawImage extends JPanel{
	private static final long serialVersionUID = 1L;
	private int[][] bitmap;	
	private int width;
	//private int height;
	
	public DrawImage(int[][] bitmap, int width, int height) {
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
		
		for(int i=0; i < bitmap.length; i++) {
			Color c = new Color(bitmap[i][0], bitmap[i][1], bitmap[i][2]);
			g2d.setColor(c);
			int x = decodedCoord.get(i).x;
			int y = decodedCoord.get(i).y;
			g2d.drawLine(x, y, x, y);
		}
	}
	
	
	
	public static JPanel draw(int[][]bitmap, int width, int height) {
		DrawImage image = new DrawImage(bitmap, width, height);
		image.setPreferredSize(new Dimension(width, height));
		//JFrame frame = new JFrame();
		//frame.setTitle("Original Image");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().add(image);
		//frame.pack();
		//frame.setVisible(true);
		
		return image;
	}
}