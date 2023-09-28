package userinterface;

import java.awt.Color;
import java.awt.Graphics;

public class Bar {

	protected int x, y, width, height;

	public Bar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	protected void drawButtonFeedback(Graphics g, MyButton b) {
		// MouseOver and Border
		if (b.isMouseOver()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}

		g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());

		// MousePressed
		if (b.isMousePressed() && b.isMouseOver()) {
			g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
			g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
		}
	}

}
