package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
//import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import imageInput.BMPRead;
import imageInput.GreyScale;
import imageOutput.BrightenBitmap;
import imageOutput.DrawImage;
import imageOutput.Histogram;
import imageOutput.OrderedDithering;

public class Main{
	private static int[][]bitmap;
	private static int[][]greyScaleBitmap;
	private static int[][]brightenedBitmap;
	private static int imageWidth;
	private static int imageHeight;
	
	public static void main(String[] args) {
		File image = ChooseBMP.chooseFile();
		try {
			bitmap = BMPRead.bmpRead(image);
			greyScaleBitmap = GreyScale.greyScaleRead(image);
			brightenedBitmap = BrightenBitmap.brightenBitmap(bitmap);
			imageWidth = BMPRead.getWidth(image);
			imageHeight = BMPRead.getHeight(image);
			
			JFrame frame = new JFrame("BMP Read");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			CardLayout cardLayout = new CardLayout();
			JPanel cardPanel = new JPanel(cardLayout);
			
			//The "cards" in the cardlayout i.e. the panels of the BMP image
			JPanel original = DrawImage.draw(bitmap, imageWidth, imageHeight);
			JPanel redHistogram = Histogram.drawRedHistogram(bitmap);
			JPanel greenHistogram = Histogram.drawGreenHistogram(bitmap);
			JPanel blueHistogram = Histogram.drawBlueHistogram(bitmap);
			JPanel brightened = DrawImage.draw(brightenedBitmap, imageWidth, imageHeight);
			JPanel greyscale = DrawImage.draw(greyScaleBitmap, imageWidth, imageHeight);
			JPanel dither = OrderedDithering.ditherImage(greyScaleBitmap, imageWidth, imageHeight);
			
			
			cardPanel.add(original, "Original");
			cardPanel.add(redHistogram, "Red Histogram");
			cardPanel.add(greenHistogram, "Green Histogram");
			cardPanel.add(blueHistogram, "Blue Histogram");
			cardPanel.add(brightened, "Brightened");
			cardPanel.add(greyscale, "Greyscale");
			cardPanel.add(dither, "Ordered Dithering");
			
			//Adds a next page button that flips through the panels
			JPanel buttonPanel = new JPanel();
			JButton nextPageButton = new JButton("Next Page");
			nextPageButton.setActionCommand("Next Page");
			nextPageButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					cardLayout.next(cardPanel);
				}
			});
			buttonPanel.add(nextPageButton);

			//frame.setPreferredSize(new Dimension(1080, 720));
			frame.add(cardPanel, BorderLayout.CENTER);
			frame.add(buttonPanel, BorderLayout.EAST);
			frame.pack();
			frame.setVisible(true);
		}
		catch (NullPointerException | IOException e){
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			String errorMessage = "An error in file selection occurred.\nYou either did not selelct a file or the image is not in BMP format.";
			JOptionPane.showMessageDialog(frame, errorMessage, "File Selection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}