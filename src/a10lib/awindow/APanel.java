package a10lib.awindow;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class APanel extends JPanel {

	/**
	 * eclipse generated 
	 */
	private static final long serialVersionUID = -4533453072384743924L;
	
	private AStage stage;

	/**
	 * Create new instance of APanel containing AStage
	 * 
	 * @param s:
	 *            the stage that this APanel is containing
	 */
	public APanel(AStage s) {
		super();
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
	 * 
	 * @return the current AStage that this APanel is containing
	 */
	public AStage getStage() {
		return stage;
	}

	/**
	 * Set the AStage that this APanel will be containing
	 * 
	 * @param s:
	 *            the AStage that this APanel will be containing
	 */
	public void setStage(AStage s) {
		stage = s;
		
		//remove all current listeners
		while(this.getMouseListeners().length > 0) {
			this.removeMouseListener(this.getMouseListeners()[0]);
		}
		while(this.getMouseMotionListeners().length > 0) {
			this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
		}
		while(this.getKeyListeners().length > 0) {
			this.removeKeyListener(this.getKeyListeners()[0]);
		}
		
		//add listeners for component
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent e) {
				s.onMouseExited(e);
			}
			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent e) {
				s.onMouseReleased(e);
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {}
			@Override
			public void mouseMoved(MouseEvent e) {
				s.onMouseMoved(e);
			}
		});
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				s.onKeyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		
		//add listener for stage
		addMouseListener(s);
		addKeyListener(s);
		addMouseMotionListener(s);
	}

	@Override
	public void paintComponent(Graphics gr) {
		AGraphics g = new AGraphics(gr);
		stage.update();
		stage.renderBackground(g);
		stage.renderComponents(g);
		stage.render(g);
	}

}
