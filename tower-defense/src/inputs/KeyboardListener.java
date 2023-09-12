package inputs;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import main.GameStates;
import static main.GameStates.*;

public class KeyboardListener implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) {
			System.out.println("A is pressed");
			GameStates.gameState = MENU;
		}
		else if(e.getKeyCode() == KeyEvent.VK_B) {
			System.out.println("B is pressed");
			GameStates.gameState = PLAYING;
		}
		else if(e.getKeyCode() == KeyEvent.VK_C){
			System.out.println("C is pressed");
			GameStates.gameState = SETTINGS;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
