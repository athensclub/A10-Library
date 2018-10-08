package a10lib.awindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class AStage implements MouseListener, KeyListener, MouseMotionListener {

	private AWindow window;

	/**
	 * How this stage is rendered
	 * 
	 * @param g:
	 *            the renderer that is used to render this stage
	 */
	public abstract void render(AGraphics g);

	/**
	 * The method that run every frame before the rendering
	 */
	public void update() {
	}

	/**
	 * How this stage background is rendered
	 * 
	 * @param g:
	 *            the renderer that is used to render this stage's background
	 */
	public void renderBackground(AGraphics g) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Set the AWindow that is containing this stage
	 * 
	 * @param p:
	 *            the AWindow that is containing this stage
	 */
	public final void setWindow(AWindow p) {
		window = p;
	}

	/**
	 * Get the AWindow that is containing this stage
	 * 
	 * @return the AWindow that is containing this stage
	 */
	public final AWindow getWindow() {
		return window;
	}

}
