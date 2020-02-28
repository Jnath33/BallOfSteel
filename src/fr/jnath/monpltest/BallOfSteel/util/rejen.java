package fr.jnath.monpltest.BallOfSteel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class rejen {
	
	public void copyWorld(File src, File to) throws IOException{
		if(src.isDirectory()) {
			if(!to.exists())to.mkdir();
			String files[] = src.list();
			for(String file : files) {
				File srcFile= new File(src,file);
				File toFile = new File(to, file);
				copyWorld(srcFile, toFile);
			}
		}else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(to);
			byte b[] = new byte[1024];
			int length;
			while((length=in.read(b))>0) {
				out.write(b, 0, length);
				
			}
			in.close();
			out.close();
		}
	}
	
	public void deleateWorld(File fichier) {
		if(fichier.exists()) {
			File files[] = fichier.listFiles();
			for(int i = 0;i<files.length;i++) {
				if (files[i].isDirectory()) {
					deleateWorld(files[i]);
				}else{
					files[i].delete();
				}
			}
		}
	}
}
