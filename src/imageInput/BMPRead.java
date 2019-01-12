package imageInput;

import java.io.*;

public class BMPRead{
	public static int[][] bmpRead(File file) throws IOException{
		FileInputStream inputs = new FileInputStream(file);
		BufferedInputStream buffer = new BufferedInputStream(inputs);

		//int bmpHeaderLen = buffer.read();
		buffer.skip(14);	//skips Bitmap file header
		//System.out.println("bmpHeaderLen: " + bmpHeaderLen);
		byte[] data = new byte[4];
		buffer.read(data);
		int dibHeaderLen = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF) << 24;
		//System.out.println(dibHeaderLen);
		
		//Reads the image width and height
		buffer.read(data);
		int imageWidth = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF)<< 24;
		buffer.read(data);
		int imageHeight = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF)<< 24;
		//System.out.println("width, height: "+imageWidth+" , "+imageHeight);
		int numPixels = imageWidth * imageHeight;
		
		buffer.skip(dibHeaderLen - 8);	//skips to data (8 = #bytes read in width/height)
		
		//int[][] bitmap = new int[imageHeight][imageWidth*3];
		byte[] rgb = new byte[3];
		int [][] bitmap = new int[numPixels][3];
		
		for(int yIndex = imageHeight-1; yIndex > 0; yIndex--) {
			for(int xIndex = 0; xIndex<imageWidth; xIndex++) {
				int pixelIndex = (yIndex * imageWidth) + xIndex;
				buffer.read(rgb);
				//24bpp is stored as BGR, bitmap is stored in RGB format
				int color = (rgb[0] & 0xFF) | (rgb[1] & 0xFF) << 8| (rgb[2] & 0xFF) << 16;
				int red = (color >> 8) & 0xFF;
				int green  = (color) & 0xFF;
				int blue = (color >> 16) & 0xFF;
				bitmap[pixelIndex][0] = red;
				bitmap[pixelIndex][1] = green;
				bitmap[pixelIndex][2] = blue;
			}
		}
		buffer.close();
		return bitmap;
	}
	
	public static int getWidth(File file) throws IOException {
		FileInputStream inputs = new FileInputStream(file);
		BufferedInputStream buffer = new BufferedInputStream(inputs);
		
		buffer.skip(18);	//skips to width location
		byte[] data = new byte[4];
		buffer.read(data);
		int imageWidth = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF)<< 24;
		buffer.close();
		return imageWidth;
	}
	
	public static int getHeight(File file) throws IOException {
		FileInputStream inputs = new FileInputStream(file);
		BufferedInputStream buffer = new BufferedInputStream(inputs);
		
		buffer.skip(22);	//skips to height location
		byte[] data = new byte[4];
		buffer.read(data);
		int imageHeight = (data[0] & 0xFF) | (data[1] & 0xFF) <<8 | (data[2] & 0xFF) <<16 | (data[3] & 0xFF)<< 24;
		buffer.close();
		return imageHeight;
	}
}

/*
bitmap[yIndex][xIndex] = (rgb[0] & 0xFF);
bitmap[yIndex][xIndex+1] = (rgb[1] & 0xFF);
bitmap[yIndex][xIndex+2] = (rgb[2] & 0xFF);
//	System.out.printf("yindex: %d, xindex: %d, color: %d\n", yIndex, xIndex, bitmap[yIndex][xIndex+2]);
System.out.println(xIndex);
//}
*/