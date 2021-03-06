package a10lib.awindow;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * No Longer Supported.Switched to javafx version<br>
 * 
 * A main class for using awindow GUI package use this class to hold AStage
 * 
 * @author Athensclub
 *
 */
@Deprecated
public class AWindow implements Runnable, MouseTracker, KeyTracker {

    private AFrame frame;

    private float fps;

    /**
     * Default width of every AWindow
     */
    public static final int DEFAULT_WIDTH = 800;

    /**
     * Default height of every AWindow
     */
    public static final int DEFAULT_HEIGHT = 600;

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

    public void setCursorIcon(AImage img) {
	frame.getPanel().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img.getImage(), new Point(0, 0), ""));
    }

    public void setCursorIcon(Cursor c) {
	frame.getPanel().setCursor(c);
    }

    public Rectangle getBounds() {
	return new Rectangle(0, 0, getWidth(), getHeight());
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
	setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
     * Get the current AFrame of this AWindow
     * 
     * @return the current AFrame of this AWindow
     */
    public AFrame getFrame() {
	return frame;
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

    @Override
    public int getMouseX() {
	return frame.getPanel().getMouseX();
    }

    @Override
    public int getMouseY() {
	return frame.getPanel().getMouseY();
    }

    @Override
    public boolean isHoldingKey(int code) {
	return frame.getPanel().isHoldingKey(code);
    }

}
