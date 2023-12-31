package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel; //can draw, add button, text field

import inputs.KeyboardListener;
import inputs.MyMouseListener;


public class GameScreen extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Game game;
	private Dimension size;
	
	private MyMouseListener myMouseListener;
	private KeyboardListener keyboardListener;
	
	public GameScreen (Game game){
		this.game = game;
		
		setPanelSize();		
	}
	
	public void initInputs () {
		myMouseListener = new MyMouseListener(game);
		keyboardListener = new KeyboardListener(game);
		
		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);
		addKeyListener(keyboardListener);
		
		requestFocus(); //to ask for focus on these components
	}
	
	private void setPanelSize() {
		size = new Dimension(640, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g); //calls super class
		
		game.getRender().render(g);
	}

}
	
