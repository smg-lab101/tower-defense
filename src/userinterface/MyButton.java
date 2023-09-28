package userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MyButton {
	
	public int x, y, width, height, id;
	private String text;
	private boolean mouseOver, mousePressed;
	
	//used to see if the mouse is within bounds
	private Rectangle bounds;
	
	public MyButton(String text, int x, int y, int width, int height) {
		
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = -1;
		
		initBounds();
	}
	
	//For tile buttons with an ID
	public MyButton(String text, int x, int y, int width, int height, int id) {
		
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		
		initBounds();
	}
	
	public void draw(Graphics g) {
		
		// Body
		drawBody(g);
		
		//Border
		drawBorder(g);
		
		//Text
		drawText(g);

		
	}
	
	private void drawBorder(Graphics g) {
		
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		if(mousePressed) { //visual feedback on click
			g.drawRect(x + 1, y + 1, width - 2, height - 2);	
			g.drawRect(x + 2, y + 2, width - 4, height - 4);
		}
	}
	
	

	private void drawBody(Graphics g) {
		if(mouseOver) { g.setColor(Color.gray);
		}else {	g.setColor(Color.white);}
		
		g.fillRect(x, y, width, height);
		
	}

	private void drawText(Graphics g) {
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
		
		g.drawString(text, x - w /2 + width / 2, y + h / 2 + height/3);
		
	}
	
	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
		
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}

	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
