package imageInput;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class GreyScale{
	public static int[][] greyScaleRead(File file) throws IOException{
		FileInputStream inputs = new FileInputStream(file);
		BufferedInputStream buffer = new BufferedInputStream(inputs);
		
		buffer.skip(14);	//skips Bitmap file header
		byte[] data = new byte[4];
		buffer.read(data);
		int dibHeaderLen = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF) << 24;
		
		//Reads the image width and height
		buffer.read(data);
		int imageWidth = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF)<< 24;
		buffer.read(data);
		int imageHeight = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF)<< 24;
		//System.out.println("width, height: "+imageWidth+" , "+imageHeight);
		int numPixels = imageWidth * imageHeight;
		buffer.skip(dibHeaderLen - 8);	//skips to data (8 = #bytes read in width/height)
		
		
		byte[] rgb = new byte[3];
		int [][] bitmap = new int[numPixels][3];
		
		for(int yIndex = imageHeight-1; yIndex > 0; yIndex--) {
			for(int xIndex = 0; xIndex<imageWidth; xIndex++) {
				int pixelIndex = (yIndex * imageWidth) + xIndex;
				buffer.read(rgb);
				//24bpp is stored as BGR, bitmap is stored in RGB format
				int color = (rgb[0] & 0xFF) | (rgb[1] & 0xFF) << 8| (rgb[2] & 0xFF) << 16;
				int red = color & 0xFF;
				int green  = color & 0xFF;
				int blue = color & 0xFF;
				bitmap[pixelIndex][0] = red;
				bitmap[pixelIndex][1] = green;
				bitmap[pixelIndex][2] = blue;
			}
		}
		buffer.close();
		return bitmap;
	}
}