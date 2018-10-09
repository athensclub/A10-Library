package a10lib.awindow;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class AWindow implements Runnable {

	private AFrame frame;
	
	private float fps;

	/**
	 * Default FPS of every AWindow
	 */
	public static final float DEFAULT_FPS = 30;

	/**
	 * Create new instance of AWindow having title and AStage
	 * 
	 * @param title:
	 *            the title of this AWindow
	 * @param s:
	 *            the AStage that this AWindow will contain
	 */
	public AWindow(String title, AStage s) {
		this(title);
		frame.setStage(s);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(0,0,getWidth(),getHeight());
	}

	/**
	 * Create new instance of AWindow having title
	 * 
	 * @param title:
	 *            the title of this AWindow
	 */
	public AWindow(String title) {
		frame = new AFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFPS(AWindow.DEFAULT_FPS);
	}

	/**
	 * Create new instance of AWindow
	 */
	public AWindow() {
		this("");
	}

	/**
	 * Set the title of this AWindow
	 * 
	 * @param str:
	 *            the new title of this window
	 */
	public void setTitle(String str) {
		frame.setTitle(str);
	}

	/**
	 * Get the current title of this AWindow
	 * 
	 * @return the current title of this AWindow
	 */
	public String getTitle() {
		return frame.getTitle();
	}

	/**
	 * Set Icon image of this AWindow
	 * 
	 * @param img
	 *            the icon image of this AWindow
	 */
	public void setIcon(AImage img) {
		frame.setIcon(img);
	}

	/**
	 * Get the current icon image of this AWindow
	 * 
	 * @return the current icon image of this AWindow
	 */
	public AImage getIcon() {
		return frame.getIcon();
	}

	/**
	 * Set new size of this AWindow
	 * 
	 * @param w:
	 *            the new width of this AWindow
	 * @param h:
	 *            the new height of this AWindow
	 */
	public void setSize(int w, int h) {
		frame.getPanel().setPreferredSize(new Dimension(w, h));
		frame.pack();
	}

	/**
	 * Get the current width of this AWindow
	 * 
	 * @return the current width of this AWindow
	 */
	public int getWidth() {
		return frame.getPanel().getWidth();
	}

	/**
	 * Get the current height of this AWindow
	 * 
	 * @return the current height of this AWindow
	 */
	public int getHeight() {
		return frame.getPanel().getHeight();
	}

	/**
	 * Set the stage that this AWindow will contain
	 * 
	 * @param s:
	 *            the stage that this AWindow will contain
	 */
	public void setStage(AStage s) {
		
		if (frame.getStage() != null) {
			frame.getStage().setWindow(null);
		}
		s.setWindow(this);
		frame.setStage(s);
	}

	/**
	 * Set the frame rate of this AWindow
	 * 
	 * @param f:
	 *            the frame rate of this AWindow
	 */
	public void setFPS(float f) {
		fps = f;
	}

	/**
	 * Get the current frame rate of this AWindow
	 * 
	 * @return the current frame rate of this AWindow
	 */
	public float getFPS() {
		return fps;
	}

	/**
	 * Get the current AStage of this AWindow
	 * 
	 * @return the current AStage of this AWindow
	 */
	public AStage getStage() {
		return frame.getStage();
	}

	/**
	 * Create new instance of AWindow having AStage
	 * 
	 * @param s:
	 *            the stage that this AWindow will contain
	 */
	public AWindow(AStage s) {
		this("", s);
	}

	@Override
	public void run() {

		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while (true) {
			double tickPerSecond = 1000000000 / fps;

			now = System.nanoTime();
			delta += (now - lastTime) / tickPerSecond;
			lastTime = now;

			if (delta >= 1) {
				frame.render();
				delta--;
			}

		}

	}

	/**
	 * Start Running and Showing this AWindow
	 */
	public void start() {
		frame.setResizable(false);
		frame.setVisible(true);
		Thread t = new Thread(this, frame.getTitle());
		t.start();
	}

}
