package client;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChooseBMP{
	public static File chooseFile(){
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP Images", "bmp");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Choose a BMP Image");
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//System.out.println("BMP SELECTED");
			return chooser.getSelectedFile();	
		}
		else {
			return null;
		}
	}
}