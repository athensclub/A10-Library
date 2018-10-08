package a10lib.awindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * 
 * @author Athensclub
 *
 */
public class AGraphics {

	private Graphics g;

	/**
	 * For creating instance of AGraphics using java.awt.Graphics as renderer
	 * 
	 * @param g
	 */
	public AGraphics(Graphics g) {
		setGraphics(g);
	}

	/**
	 * Draw a text with center alignment
	 * 
	 * @param s:
	 *            String that is used to draw
	 * @param x:
	 *            x position of the center of the text
	 * @param y:
	 *            y position of the center of the text
	 */
	public void drawCenteredText(String s, int x, int y) {
		int n = x - (stringWidth(s) / 2);
		int m = y - (stringHeight() / 2);
		drawText(s, n, m);
	}

	/**
	 * Draw an oval using x,y as center point
	 * 
	 * @param cx:
	 *            x position of the center of oval
	 * @param cy:
	 *            y position of the center of oval
	 * @param w:
	 *            the diameter of the oval in the x axis
	 * @param h:
	 *            the diameter of the oval in the y axis
	 */
	public void drawCenteredOval(int cx, int cy, int w, int h) {
		drawOval(cx - (w / 2), cy - (h / 2), w, h);
	}

	/**
	 * Fill an oval using x,y as center point
	 * 
	 * @param cx:
	 *            x position of the center of oval
	 * @param cy:
	 *            y position of the center of oval
	 * @param w:
	 *            the diameter of the oval in the x axis
	 * @param h:
	 *            the diameter of the oval in the y axis
	 */
	public void fillCenteredOval(int cx, int cy, int w, int h) {
		fillOval(cx - (w / 2), cy - (h / 2), w, h);
	}

	/**
	 * Calculate width of a string that is drawn in this renderer
	 * 
	 * @param str:
	 *            the string to calculate it's width
	 * @return The width (in pixels) of the given string
	 */
	public int stringWidth(String str) {
		return g.getFontMetrics().stringWidth(str);
	}

	/**
	 * Get the height of string that this renderer will draw
	 * 
	 * @return The height (in pixels) of every string that this renderer will draw
	 */
	public int stringHeight() {
		return g.getFontMetrics().getHeight();
	}

	/**
	 * Set the renderer that this AGraphics will use
	 * 
	 * @param g:
	 *            the renderer that this AGraphics will use
	 */
	public void setGraphics(Graphics g) {
		this.g = g;
	}

	/**
	 * Set the renderer that this AGraphics is using
	 * 
	 * @return the renderer that this AGraphics is using
	 */
	public Graphics getGraphics() {
		return g;
	}

	/**
	 * Set the color that this renderer will use
	 * 
	 * @param c:
	 *            The color that this renderer will use
	 */
	public void setColor(Color c) {
		g.setColor(c);
	}

	/**
	 * Set the color that this renderer will use
	 * 
	 * @param red:
	 *            the red value of the color (0-255)
	 * @param green:
	 *            the green value of the color (0-255)
	 * @param blue:
	 *            the blue value of the color (0-255)
	 */
	public void setColor(int red, int green, int blue) {
		setColor(new Color(red, green, blue));
	}

	/**
	 * Fill an Rectangle using x,y as top-left corner point
	 * 
	 * @param x:
	 *            the x position of the top-left corner of the rectangle
	 * @param y:
	 *            the y position of the top-left corner of the rectangle
	 * @param w:
	 *            the width of the rectangle
	 * @param h:
	 *            the height of the rectangle
	 */
	public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x, y, w, h);
	}

	/**
	 * Draw an Rectangle using x,y as top-left corner point
	 * 
	 * @param x:
	 *            the x position of the top-left corner of the rectangle
	 * @param y:
	 *            the y position of the top-left corner of the rectangle
	 * @param w:
	 *            the width of the rectangle
	 * @param h:
	 *            the height of the rectangle
	 */
	public void drawRect(int x, int y, int w, int h) {
		g.drawRect(x, y, w, h);
	}

	/**
	 * Draw an oval using x,y as top-left corner corner point
	 * 
	 * @param cx:
	 *            x position of the top-left corner of oval
	 * @param cy:
	 *            y position of the top-left corner of oval
	 * @param w:
	 *            the diameter of the oval in the x axis
	 * @param h:
	 *            the diameter of the oval in the y axis
	 */
	public void drawOval(int x, int y, int dw, int dh) {
		g.drawOval(x, y, dw, dh);
	}

	/**
	 * Fill an oval using x,y as top-left corner corner point
	 * 
	 * @param cx:
	 *            x position of the top-left corner of oval
	 * @param cy:
	 *            y position of the top-left corner of oval
	 * @param w:
	 *            the diameter of the oval in the x axis
	 * @param h:
	 *            the diameter of the oval in the y axis
	 */
	public void fillOval(int x, int y, int dw, int dh) {
		g.fillOval(x, y, dw, dh);
	}

	/**
	 * Draw a given text
	 * 
	 * @param s:
	 *            String that is used to draw
	 * @param x:
	 *            x position of the center of the text
	 * @param y:
	 *            y position of the center of the text
	 */
	public void drawText(String txt, int x, int y) {
		g.drawString(txt, x, y);
	}

	/**
	 * Draw an image
	 * 
	 * @param img:
	 *            the image that is going to be drawn
	 * @param x:
	 *            the x position of the top-left corner of the image
	 * @param y:
	 *            the y position of the top-left corner of the image
	 */
	public void drawImage(AImage img, int x, int y) {
		g.drawImage(img.getImage(), x, y, null);
	}

	/**
	 * Set the size of the font of this renderer
	 * 
	 * @param s:
	 *            the new font size
	 */
	public void setFontSize(int s) {
		setFont(new Font(getFont().getFontName(), Font.PLAIN, s));
	}

	/**
	 * Set the new font for this renderer
	 * 
	 * @param f:
	 *            the font that this renderer will use
	 */
	public void setFont(Font f) {
		g.setFont(f);
	}

	/**
	 * Get the current font that this renderer is using
	 * 
	 * @return the current font that this renderer is using
	 */
	public Font getFont() {
		return g.getFont();
	}

	/**
	 * Check whether this renderer is rendering the same component as another
	 * renderer
	 * 
	 * @return Whether this renderer is using same java.awt.Graphics as the other
	 *         renderer
	 */
	@Override
	public boolean equals(Object o) {
		AGraphics ag = (AGraphics) o;
		if (ag.g.equals(g)) {
			return true;
		}
		return false;
	}

}
