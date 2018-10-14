package a10lib.awindow.acomponent;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import a10lib.awindow.AGraphics;
import a10lib.awindow.AWindow;

public class AButton extends AComponent implements Listenable {

	private Color mouseOverBackground, textColor;

	private String text;

	private boolean isMouseOver;

	private ArrayList<Runnable> listeners;

	private ArrayList<Runnable> mouseOverListeners;

	/**
	 * Create new instance of AButton
	 */
	public AButton() {
		this("");
	}

	/**
	 * Set the current color of this AButton's text
	 * 
	 * @param c:
	 *            the new color of this AButton's text
	 */
	public void setTextColor(Color c) {
		textColor = c;
	}

	/**
	 * Get the current color of this AButton's text
	 * 
	 * @return the current color of this AButton's text
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * Set the current background color of this AButton when mouse cursor is over
	 * this AButton
	 * 
	 * @param c:
	 *            the new background color of this AButton when mouse cursor is over
	 *            this AButton
	 */
	public void setMouseOverBackgroundColor(Color c) {
		mouseOverBackground = c;
	}

	/**
	 * Set the current background color of this AButton when mouse cursor is over
	 * this AButton
	 * 
	 * @param red:
	 *            the red value of the new the new background color of this AButton
	 *            when mouse cursor is over this AButton (0-255)
	 * @param green:
	 *            the green value of the the new background color of this AButton
	 *            when mouse cursor is over this AButton (0-255)
	 * @param blue:
	 *            the blue value of the the new background color of this AButton
	 *            when mouse cursor is over this AButton (0-255)
	 */
	public void setMouseOverBackgroundColor(int red, int green, int blue) {
		setMouseOverBackgroundColor(new Color(red, green, blue));
	}

	/**
	 * Get the current background color of this AButton when mouse cursor is over
	 * this AButton
	 * 
	 * @return the current background color of this AButton when mouse cursor is
	 *         over this AButton
	 */
	public Color getMouseOverBackgroundColor() {
		return mouseOverBackground;
	}

	/**
	 * Create new instance of AButton with text
	 * 
	 * @param text:
	 *            the text of this AButton
	 */
	public AButton(String text) {
		setBackgroundColor(Color.GRAY);
		mouseOverBackground = Color.DARK_GRAY;
		textColor = Color.BLACK;
		setText(text);
		listeners = new ArrayList<>();
		mouseOverListeners = new ArrayList<>();
	}

	/**
	 * Add listener when mouse is over this AButton
	 * 
	 * @param r:
	 *            listener to run when mouse is over this AButton
	 */
	public void addMouseOverListener(Runnable r) {
		mouseOverListeners.add(r);
	}

	/**
	 * Remove the listener when mouse is over this button
	 * 
	 * @param r:
	 *            listener to run when mouse is over this AButton that is going to
	 *            be removed
	 */
	public void removeMouseOverListener(Runnable r) {
		mouseOverListeners.remove(r);
	}

	/**
	 * Get all the listener that is run when mouse is over this AButton
	 * 
	 * @return all the listener that is run when mouse is over this AButton
	 */
	public ArrayList<Runnable> getMouseOverListeners() {
		return mouseOverListeners;
	}

	/**
	 * Get the current text of this AButton
	 * 
	 * @return the current text of this AButton
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text of this AButton
	 * 
	 * @param str:
	 *            the string to set to this button's text
	 */
	public void setText(String str) {
		text = str;
	}

	private void calculateBounds(AGraphics g) {
		Rectangle bounds = getBounds();
		bounds.width = g.stringWidth(text) + 10;
		bounds.height = g.stringHeight() + 10;
	}

	@Override
	public void render(AGraphics g) {
		calculateBounds(g);
		Rectangle bounds = getBounds();
		if (isMouseOver) {
			g.setColor(mouseOverBackground);
		} else {
			g.setColor(getBackgroundColor());
		}
		g.fillRect(bounds);
		g.setColor(textColor);
		g.drawText(text, bounds.x + 5, bounds.y + (g.stringHeight()));
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		if (isMouseOver) {
			for (Runnable r : listeners) {
				r.run();
			}
			isMouseOver = false;
		}
	}

	@Override
	public void onKeyPressed(KeyEvent e) {

	}

	@Override
	public void addListener(Runnable r) {
		listeners.add(r);
	}

	@Override
	public void removeListener(Runnable r) {
		listeners.remove(r);
	}

	@Override
	public ArrayList<Runnable> getListeners() {
		return listeners;
	}

	@Override
	public void update(AWindow w) {
		if (getBounds().contains(w.getMouseX(), w.getMouseY())) {
			isMouseOver = true;
			for (Runnable r : mouseOverListeners) {
				r.run();
			}
		} else {
			isMouseOver = false;
		}
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
