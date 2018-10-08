package a10lib.awindow;

import java.awt.*;

import javax.swing.*;

public class APanel extends JPanel{
	
	private AStage stage;
	
	/**
	 * Create new instance of APanel containing AStage
	 * @param s: the stage that this APanel is containing
	 */
	public APanel(AStage s) {
		setStage(s);
		setFocusable(true);
	}
	
	/**
	 * Create new instance of APanel
	 */
	public APanel() {
		this(null);
	}
	
	/**
	 * Get the current AStage that this APanel is containing
	 * @return the current AStage that this APanel is containing
	 */
	public AStage getStage() {
		return stage;
	}
	
	/**
	 * Set the AStage that this APanel will be containing
	 * @param s: the AStage that this APanel will be containing 
	 */
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
