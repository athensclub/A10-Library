package a10lib.awindow.acomponent;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import a10lib.awindow.AGraphics;

public abstract class AComponent {

	private Rectangle bounds = new Rectangle(0, 0, 0, 0);

	private Color background;

	public abstract void render(AGraphics g);

	/**
	 * Set the current background color of this AComponent
	 * 
	 * @param col:
	 *            the color that is going to be this AComponent's background color
	 */
	public void setBackgroundColor(Color col) {
		background = col;
	}

	/**
	 * Set the current background color of this AComponent
	 * 
	 * @param red:
	 *            the red value of the new background color of this AComponent
	 *            (0-255)
	 * @param green:
	 *            the green value of the new background color of this AComponent
	 *            (0-255)
	 * @param blue:
	 *            the blue value of the new background color of this AComponent
	 *            (0-255)
	 */
	public void setBackgroundColor(int red, int green, int blue) {
		setBackgroundColor(new Color(red, green, blue));
	}

	/**
	 * Get the current background color of this AComponent
	 * 
	 * @return the current background color of this AComponent
	 */

	public Color getBackgroundColor() {
		return background;
	}

	public abstract void onMouseReleased(MouseEvent e);
	
	public abstract void onMouseExited(MouseEvent e);

	public abstract void onMouseMoved(MouseEvent e);

	public abstract void onKeyPressed(KeyEvent e);

	/**
	 * Get bounds of this AComponent
	 * 
	 * @return bounds of this AComponent
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Set the x position of this AComponent
	 * 
	 * @param x:
	 *            new x position of this AComponent
	 */
	public void setX(int x) {
		bounds.x = x;
	}

	/**
	 * Get the x position of this AComponent
	 * 
	 * @return the current x position of this AComponent
	 */
	public int getX() {
		return bounds.x;
	}

	/**
	 * Set the y position of this AComponent
	 * 
	 * @param y:
	 *            new y position of this AComponent
	 */
	public void setY(int y) {
		bounds.y = y;
	}

	/**
	 * Get the y position of this AComponent
	 * 
	 * @return the current y position of this AComponent
	 */
	public int getY() {
		return bounds.y;
	}

	/**
	 * Set bounds of this AComponent
	 * 
	 * @param r:
	 *            the bounds of this AComponent to be set to
	 */
	public void setBounds(Rectangle r) {
		bounds = r;
	}

	public void update() {
	}

}
