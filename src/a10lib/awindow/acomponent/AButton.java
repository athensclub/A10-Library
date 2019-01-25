package a10lib.awindow.acomponent;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import a10lib.awindow.AGraphics;
import a10lib.awindow.AImage;
import a10lib.awindow.AWindow;

/**
 * No longer supported.Switched to javafx version
 * 
 * @author Athensclub
 *
 */
@Deprecated
public class AButton extends AComponent {

    private Color mouseOverBackground, textColor;

    private String text;

    private boolean isMouseOver;

    private AImage iconImage;

    private int fontSize = 16;

    private ArrayList<ActionListener> listeners;

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
     * Set the current font size of this AButton
     * 
     * @param f:
     *            the new font size of this AButton
     */
    public void setFontSize(int f) {
	fontSize = f;
    }

    /**
     * Set Icon image of this AButton
     * 
     * @param img
     *            the icon image of this AButton
     */
    public void setIcon(AImage img) {
	iconImage = img;
    }

    /**
     * Get the current icon image of this AButton
     * 
     * @return the current icon image of this AButton
     */
    public AImage getIcon() {
	return iconImage;
    }

    /**
     * Get the current font size of this AButton
     * 
     * @return the current font size of this AButton
     */
    public int getFontSize() {
	return fontSize;
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

    /**
     * Check if all the icons and text are inside bounds
     * 
     * @param g
     * @param image
     * @return
     */
    private boolean calculateBounds(AGraphics g, AImage image) {
	Rectangle bounds = getBounds();
	int imgw = 0, imgh = 0;
	if (image != null) {
	    imgw = image.getWidth();
	    imgh = image.getHeight();
	}
	int width = g.stringWidth(text) + imgw;
	int height = g.stringHeight();
	if (bounds.height < imgh) {
	    height = imgh;
	}
	if (width > bounds.width || height > bounds.height) {
	    return true;
	}
	return false;
    }

    @Override
    public void render(AGraphics g) {
	g.setFontSize(fontSize);
	AImage image = iconImage;
	boolean overFlow = calculateBounds(g, image);
	Rectangle bounds = getBounds();
	if (isMouseOver) {
	    g.setColor(mouseOverBackground);
	} else {
	    g.setColor(getBackgroundColor());
	}
	g.fillRect(bounds);
	g.setColor(textColor);
	if (!overFlow) {
	    if (iconImage != null) {
		if (!text.equals("") && text != null) {
		    g.drawImage(image, bounds.x, bounds.y);
		    g.drawYCenteredText(text, bounds.x + image.getWidth(), bounds.y + g.stringHeight() / 2);
		} else {
		    g.drawImage(image, bounds.x + (bounds.width / 2 - image.getWidth() / 2), bounds.y);
		}
	    } else {
		g.drawYCenteredText(text, bounds.x, bounds.y + g.stringHeight() / 2);
	    }
	} else {
	    g.drawYCenteredText("...", bounds.x, bounds.y + g.stringHeight() / 2);
	}
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
	if (isMouseOver) {
	    ActionEvent event = new ActionEvent(this, AComponent.actionEventID++, toString(),
		    System.currentTimeMillis(), ActionEvent.ACTION_PERFORMED);
	    for (ActionListener r : listeners) {
		r.actionPerformed(event);
	    }
	    isMouseOver = false;
	}
    }

    public void onKeyPressed(KeyEvent e) {
    }

    /**
     * Add listener to this AButton; listener run when this AButton is clicked
     * 
     * @param r:
     *            the listener to run when the button is clicked
     */
    public void addActionListener(ActionListener r) {
	listeners.add(r);
    }

    /**
     * Remove listener to this AButton; listener run when this AButton is clicked
     * 
     * @param r:
     *            the listener to run when the button is clicked to be removed
     */
    public void removeActionListener(ActionListener r) {
	listeners.remove(r);
    }

    /**
     * Get all listener of this AButton that run when this AButton is clicked
     * 
     * @return all listener of this AButton that run when this AButton is clicked
     */
    public ArrayList<ActionListener> getActionListeners() {
	return listeners;
    }

    @Override
    public void update(AWindow w) {
	super.update(w);
	if (getBounds().contains(w.getMouseX(), w.getMouseY())) {
	    isMouseOver = true;
	    for (Runnable r : mouseOverListeners) {
		r.run();
	    }
	} else {
	    isMouseOver = false;
	}
    }

    public String toString() {
	return getText();
    }

    @Override
    public void onKeyReleased(KeyEvent e) {

    }

}
