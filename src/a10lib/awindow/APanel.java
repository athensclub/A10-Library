package a10lib.awindow;

import java.awt.*;

import javax.swing.*;

public class APanel extends JPanel{
	
	private AStage stage;
	
	public APanel(AStage s) {
		setStage(s);
		setFocusable(true);
	}
	
	public AStage getStage() {
		return stage;
	}
	
	public void setStage(AStage s) {
		stage = s;
		
		if(this.getMouseListeners().length > 0) {
			this.removeMouseListener(this.getMouseListeners()[0]);
		}
		if(this.getKeyListeners().length > 0) {
			this.removeKeyListener(this.getKeyListeners()[0]);
		}
		if(this.getMouseMotionListeners().length>0) {
			this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
		}
		
		addMouseListener(s);
		addKeyListener(s);
		addMouseMotionListener(s);
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		AGraphics g = new AGraphics(gr);
		stage.update();
		stage.renderBackground(g);
		stage.render(g);
	}
	
}
