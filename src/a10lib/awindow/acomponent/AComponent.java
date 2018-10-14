package a10lib.awindow.acomponent;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import a10lib.awindow.AGraphics;
import a10lib.awindow.AWindow;

public abstract class AComponent {

	private Rectangle bounds = new Rectangle(0, 0, 0, 0);

	private Color background = Color.GRAY;

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
	
	public abstract void onKeyReleased(KeyEvent e);

	public abstract void onMouseReleased(MouseEvent e);

	public abstract void onKeyPressed(KeyEvent e);

	/**
	 * update the AComponent, called by APanel
	 * 
	 * @param w:
	 *            AWindow containing this AComponent
	 */
	public abstract void update(AWindow w);

	/**
	 * Get bounds of this AComponent
	 * 
	 * @return bounds of this AComponent
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Set the width of this AComponent
	 * @param w: the new width of this AComponent
	 */
	public void setWidth(int w) {
		setBounds(new Rectangle(bounds.x,bounds.y,w,bounds.height));
	}
	
	/**
	 * Get the current width of this component
	 * @return the current width of this component
	 */
	public int getWidth(){
		return bounds.width;
	}
	
	/**
	 * Set the height of this AComponent
	 * @param h: the new height of this AComponent
	 */
	public void setHeight(int h) {
		setBounds(new Rectangle(bounds.x,bounds.y,bounds.width,h));
	}
	
	/**
	 * Get the current height of this component
	 * @return the current height of this component
	 */
	public int getHeight() {
		return bounds.height;
	}

	/**
	 * Set the x position of this AComponent
	 * 
	 * @param x:
	 *            new x position of this AComponent
	 */
	public void setX(int x) {
		setBounds(new Rectangle(x,bounds.y,bounds.width,bounds.height));
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
		setBounds(new Rectangle(bounds.x,y,bounds.width,bounds.height));
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
		bounds.x = r.y;
		bounds.y = r.y;
		bounds.width = r.width;
		bounds.height = r.height;
	}
	
	public void setBounds(int x,int y,int width,int height) {
		setBounds(new Rectangle(x,y,width,height));
	}

	public void update() {
	}

}
