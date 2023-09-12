package helps;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

public class LoadSave {
	
	public static BufferedImage getSpriteAtlas() {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static void CreateFile() {
		File textFile = new File("rec/testTextFile.txt");
		
		try {
			textFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void CreateLevel (String name, int[] idArray) {
		File newLevel = new File("rec/" + name + ".txt");
		
		if (newLevel.exists()) {
			System.out.println ("File " + name + " already exists");
			return;
		} else {
			try {
				
				newLevel.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		WriteToFile(newLevel, idArray);
		
	}
	
	private static void WriteToFile(File file, int[] idArray) {
		
		try {
			PrintWriter pw = new PrintWriter(file);
			
			for (Integer i : idArray) {
				pw.println(i);
			}
			pw.close();
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void ReadFromFile() {
		File textFile = new File("rec/text.txt");
		
		try {
			Scanner scanner = new Scanner(textFile);
			
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		

		
		
		
		
		
	}
