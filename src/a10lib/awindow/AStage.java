package a10lib.awindow;

import java.awt.*;
import java.awt.event.*;


public abstract class AStage implements MouseListener,KeyListener,MouseMotionListener{

	private AWindow window;
	
	public abstract void render(AGraphics g);
	
	public abstract void update();
	
	public void renderBackground(AGraphics g) {}
	
	public final void setWindow(AWindow p) {
		window = p;
	}
	
	public final AWindow getWindow() {
		return window;
	}
	
}
