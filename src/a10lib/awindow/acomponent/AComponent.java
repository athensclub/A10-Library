package a10lib.awindow.acomponent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import a10lib.awindow.AGraphics;
import a10lib.awindow.AWindow;

/**
 * No longer supported.Switched to javafx version
 * 
 * @author Athensclub
 *
 */
@Deprecated
public abstract class AComponent {

    protected static int actionEventID;

    private Rectangle bounds = new Rectangle(0, 0, 0, 0);

    private Color background = Color.GRAY;

    private Cursor cursor = Cursor.getDefaultCursor();

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
    public void update(AWindow w) {

    }

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
     * 
     * @param w:
     *            the new width of this AComponent
     */
    public void setWidth(int w) {
	bounds.width = w;
    }

    /**
     * Get the current width of this AComponent
     * 
     * @return the current width of this AComponent
     */
    public int getWidth() {
	return bounds.width;
    }

    /**
     * Set the position of this AComponent
     * 
     * @param p:
     *            the new position of this AComponent
     */
    public void setPosition(Point p) {
	setPosition(p.x, p.y);
    }

    /**
     * Set the position of this AComponent
     * 
     * @param x:
     *            the new x position of this AComponent
     * @param y:
     *            the new y position of this AComponent
     */
    public void setPosition(int x, int y) {
	setX(x);
	setY(y);
    }

    /**
     * Set the height of this AComponent
     * 
     * @param h:
     *            the new height of this AComponent
     */
    public void setHeight(int h) {
	bounds.height = h;
    }

    /**
     * Get the current height of this component
     * 
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
     * Get the mouse cursor of this AComponent
     * 
     * @return the mouse cursor of this AComponent
     */
    public Cursor getCursor() {
	return cursor;
    }

    /**
     * Set the current mouse cursor of this AComponent
     * 
     * @param c:
     *            the new mouse cursor of this AComponent
     */
    public void setCursor(Cursor c) {
	cursor = c;
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

    /**
     * Set the bounds of this AComponent
     * 
     * @param x
     *            the new x position of this AComponent
     * @param y
     *            the new y position of this AComponent
     * @param width
     *            the new width of this AComponenet
     * @param height
     *            the new height of this AComponent
     */
    public void setBounds(int x, int y, int width, int height) {
	bounds.x = y;
	bounds.y = y;
	bounds.width = width;
	bounds.height = height;
    }

}
