package helps;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import objects.PathPoint;

public class LoadSave {

	public static BufferedImage getSpriteAtlas() {
		BufferedImage img = null;
		//InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas_updated_2.png");
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("SpriteSheet.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static void CreateFile() {
		File textFile = new File("res/testTextFile.txt");
 
		try {
			textFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void CreateLevel(String name, int[] idArray) {
		File newLevel = new File("res/" + name + ".txt");

		if (newLevel.exists()) {
			System.out.println("File " + name + " already exists");
			return;
		} else {
			try {
				newLevel.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WriteToFile(newLevel, idArray, new PathPoint(0, 0), new PathPoint(0, 0));
	}

	private static void WriteToFile(File file, int[] idArray, PathPoint start, PathPoint end) {

		try {
			PrintWriter pw = new PrintWriter(file);

			for (Integer i : idArray) {
				pw.println(i);
			}

			pw.println(start.getxCord());
			pw.println(start.getyCord());
			pw.println(end.getxCord());
			pw.println(end.getyCord());

			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void SaveLevel(String name, int[][] idArray, PathPoint start, PathPoint end) {
		File levelFile = new File("res/" + name + ".txt");

		if (levelFile.exists()) {
			WriteToFile(levelFile, MyUtils.TwoDArrayTo1DintArray(idArray), start, end);

		} else {
			System.out.println("File " + name + " does not exists");
			return;
		}
	}

	private static ArrayList<Integer> ReadFromFile(File file) {
		ArrayList<Integer> list = new ArrayList<>();

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				list.add(Integer.parseInt(scanner.nextLine()));
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static ArrayList<PathPoint> getLevelPathPoints(String name) {
		File levelFile = new File("res/" + name + ".txt");

		if (levelFile.exists()) {
			ArrayList<Integer> list = ReadFromFile(levelFile);
			ArrayList<PathPoint> points = new ArrayList<>();
			points.add(new PathPoint(list.get(400), list.get(401)));
			points.add(new PathPoint(list.get(402), list.get(403)));
			return points;
		} else {
			System.out.println("File " + name + " already exists");
			return null;
		}
	}

	public static int[][] getLevelDate(String name) {
		File levelFile = new File("res/" + name + ".txt");

		if (levelFile.exists()) {
			ArrayList<Integer> list = ReadFromFile(levelFile);
			return MyUtils.ArrayListTo2Dint(list, 20, 20);
		} else {
			System.out.println("File " + name + " already exists");
			return null;
		}
	}
}
