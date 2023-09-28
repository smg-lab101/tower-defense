package helps;

import java.util.ArrayList;

public class MyUtils {
	
	public static int[][] ArrayListTo2Dint (ArrayList<Integer> list, int xSize, int ySize){
		int[][] newArray = new int[xSize][ySize];
		
		for (int j = 0; j < newArray.length; j++) {
			for (int i = 0; i < newArray[j].length; i++) {
				int index = j * ySize + i;
				newArray[j][i] = list.get(index);
			}
		}
		return newArray;	
	}
	
	public static int[] TwoDArrayTo1DintArray(int[][] twoDArray) {
		int[] oneDArray = new int[twoDArray.length * twoDArray[0].length];
		
		for (int j = 0; j < twoDArray.length; j++) {
			for (int i = 0; i < twoDArray[j].length; i++) {
				int index = j * twoDArray.length + i;
				oneDArray[index] = twoDArray[j][i];
			}
		}
		return oneDArray;	
		
	}
	
	public static int getHypoDistance (float x1, float y1, float x2, float y2) {
		float xDiff = Math.abs(x1 - x2);
		float yDiff = Math.abs(y1 - y2);
		
		
		return (int) Math.hypot(xDiff, yDiff);
	}

}
