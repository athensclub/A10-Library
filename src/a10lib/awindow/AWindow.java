package a10lib.awindow;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class AWindow implements Runnable {

	private AFrame frame;

	private float fps;

	public static final float DEFAULT_FPS = 30;

	public AWindow(String title, AStage s) {
		this(title);
		frame.setStage(s);
	}

	public AWindow(String title) {
		frame = new AFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFPS(AWindow.DEFAULT_FPS);
	}

	public AWindow() {
		this("");
	}

	public void setTitle(String str) {
		frame.setTitle(str);
	}

	public String getTitle() {
		return frame.getTitle();
	}

	public void setIcon(AImage img) {
		frame.setIcon(img);
	}

	public Image getIconImage() {
		return frame.getIconImage();
	}

	public AImage getIcon() {
		return frame.getIcon();
	}

	public void setSize(int w, int h) {
		frame.getPanel().setPreferredSize(new Dimension(w, h));
		frame.pack();
	}

	public int getWidth() {
		return frame.getPanel().getWidth();
	}

	public int getHeight() {
		return frame.getPanel().getHeight();
	}

	public void setStage(AStage s) {
		if (frame.getStage() != null) {
			frame.getStage().setWindow(null);
		}
		s.setWindow(this);
		frame.setStage(s);
	}

	public void setFPS(float f) {
		fps = f;
	}

	public float getFPS() {
		return fps;
	}

	public AStage getStage() {
		return frame.getStage();
	}

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

	public void start() {
		frame.setResizable(false);
		frame.setVisible(true);
		Thread t = new Thread(this, frame.getTitle());
		t.start();
	}

}
