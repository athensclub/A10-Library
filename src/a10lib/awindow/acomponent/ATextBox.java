package a10lib.awindow.acomponent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import a10lib.ALib;
import a10lib.awindow.AGraphics;
import a10lib.awindow.AWindow;

public class ATextBox extends AComponent {

	private String text;

	private Color textColor = Color.BLACK;

	private boolean isMouseOver;

	private int fontSize = 16;

	private boolean editable,editing;

	// index of string to be rendered as first character
	private int idx;

	// index of string that cursor is highlighting
	private int cursorIDX;

	/**
	 * create new instance of ATextBox with given text
	 * 
	 * @param str:
	 *            text to be put on this ATextBox
	 */
	public ATextBox(String str) {
		setText(str);
		setBackgroundColor(Color.WHITE);
	}

	/**
	 * Create new instance of ATextBox
	 */
	public ATextBox() {
		this("");
	}

	/**
	 * Set the current font size of this ATextBox
	 * 
	 * @param f:
	 *            the new font size of this ATextBox
	 */
	public void setFontSize(int f) {
		fontSize = f;
	}

	/**
	 * Get the current font size of this ATextBox
	 * 
	 * @return the current font size of this ATextBox
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * Set if this ATextBox can be edited
	 * 
	 * @param b:
	 *            boolean of if this ATextBox can be edited
	 */
	public void setEditable(boolean b) {
		editable = b;
	}

	/**
	 * Get if this ATextBox can be edited
	 * 
	 * @return if this ATextBox can be edited
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Set the current color of this ATextBox's text
	 * 
	 * @param c:
	 *            the new color of this ATextBox's text
	 */
	public void setTextColor(Color c) {
		textColor = c;
	}

	/**
	 * Get the current color of this ATextBox's text
	 * 
	 * @return the current color of this ATextBox's text
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * Get the current text of this ATextBox
	 * 
	 * @return the current text of this ATextBox
	 */
	public String getText() {
		return text;
	}

	/**
	 * Append the character to the current text
	 * 
	 * @param c:
	 *            the character to be append to the current text
	 */
	public void append(char c) {
		setText(getText() + c);
		cursorIDX++;
	}

	/**
	 * Set the current text of this ATextBox
	 * 
	 * @param str:
	 *            text to be put on this ATextBox
	 */
	public void setText(String str) {
		text = str;
	}

	private void updateCursor(AGraphics g) {
		int charCount = renderableCharCount(g, idx);
		int result = cursorIDX - charCount;
		if (cursorIDX > idx + charCount) {
			idx = result;
		}
		if (idx < 0) {
			idx = 0;
		}

	}

	private int renderableCharCount(AGraphics g, int i) {

		return renderableCharCount(g, i, text.length());
	}

	private int renderableCharCount(AGraphics g, int from, int to) {

		int count = 0;
		int bwidth = getBounds().width;

		while (bwidth > 0) {

			if (count > text.length()) {
				break;
			}

			if (from == to) {
				break;
			}

			int cwidth = g.stringWidth(Character.toString(text.charAt(from)));

			// System.out.print(cwidth + ",");

			if (cwidth < bwidth) {
				count++;
				bwidth -= cwidth;
			} else {
				break;
			}

			from++;
		}

		// System.out.println();

		return count;
	}

	private void renderCursor(AGraphics g, int charCount) {

		int spaceWidth = g.stringWidth(" ");

		g.setStrokeWidth(3);

		Rectangle bounds = getBounds();

		g.setColor(textColor);

		int x1 = 0, i = idx;

		if (text.length() == 0) {
			g.drawLine(bounds.x, bounds.y + bounds.height, bounds.x + spaceWidth, bounds.y + bounds.height);
			return;
		}

		for (; i < charCount - 1; i++) {
			x1 += g.stringWidth(Character.toString(text.charAt(i)));
		}

		int cwidth = g.stringWidth(Character.toString(text.charAt(i)));

		g.drawLine(bounds.x + x1 + (cwidth > spaceWidth ? (cwidth - spaceWidth) / 2 : 0), bounds.y + bounds.height,
				bounds.x + x1 + spaceWidth + (cwidth > spaceWidth ? (cwidth - spaceWidth) / 2 : 0),
				bounds.y + bounds.height);
	}

	private void boundsSize(AGraphics g) {
		setHeight(g.stringHeight());
	}

	@Override
	public void render(AGraphics g) {
		if (g.getFontSize() != fontSize) {
			g.setFontSize(fontSize);
		}
		boundsSize(g);
		int charCount = renderableCharCount(g, idx);
		if (editing) {
			updateCursor(g);
			renderCursor(g, charCount);
		}
		Rectangle bounds = getBounds();
		g.setColor(getBackgroundColor());
		g.fillRect(bounds);
		g.setColor(textColor);
		g.drawYCenteredText(text.substring(idx, idx + charCount), bounds.x, bounds.y + (g.stringHeight() / 2));
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		if (isMouseOver && editable) {
			editing = true;
		} else {
			editing = false;
		}
	}

	@Override
	public void onKeyPressed(KeyEvent e) {}

	@Override
	public void onKeyReleased(KeyEvent e) {
		if (editing) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (text.length() > 0) {
					backspace();
				}
			} else if (ALib.isASCII(e.getKeyChar())) {
				append(e.getKeyChar());
			}
		}
	}

	private void backspace() {
		setText(new StringBuilder(text).deleteCharAt(cursorIDX - 1).toString());
		cursorIDX--;
	}

	@Override
	public void update(AWindow w) {

		if (!editable && editing) {
			editing = false;
		}

		if (getBounds().contains(w.getMouseX(), w.getMouseY())) {
			isMouseOver = true;
		} else {
			isMouseOver = false;
		}

		if (isMouseOver && editable) {
			w.setCursorIcon(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		} else {
			w.setCursorIcon(Cursor.getDefaultCursor());
		}

	}

}
