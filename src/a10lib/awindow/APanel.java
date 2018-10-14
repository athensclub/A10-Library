package a10lib.awindow;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class APanel extends JPanel implements MouseTracker,KeyTracker{

	/**
	 * eclipse generated
	 */
	private static final long serialVersionUID = -4533453072384743924L;

	private AStage stage;
	
	private int mouseX,mouseY;
	
	private ArrayList<Integer> holdingKeys;

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
		holdingKeys = new ArrayList<>();
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

		// remove all current listeners
		while (this.getMouseListeners().length > 0) {
			this.removeMouseListener(this.getMouseListeners()[0]);
		}
		while (this.getMouseMotionListeners().length > 0) {
			this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
		}
		while (this.getKeyListeners().length > 0) {
			this.removeKeyListener(this.getKeyListeners()[0]);
		}

		// add listeners for component & key tracker
		addMouseListener(new MouseListener() {
			@Override public void mouseClicked(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {s.onMouseReleased(e);}});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override public void mouseDragged(MouseEvent e) {}
			@Override public void mouseMoved(MouseEvent e) {}});
		
		addKeyListener(new KeyListener() {
			@Override public void keyPressed(KeyEvent e) {s.onKeyPressed(e);holdingKeys.add(e.getKeyCode());}
			@Override public void keyReleased(KeyEvent e) {s.onKeyReleased(e);holdingKeys.remove(Integer.valueOf(e.getKeyCode()));}
			@Override public void keyTyped(KeyEvent e) {}
		});

		// add listener for stage
		addMouseListener(s);
		addKeyListener(s);
		addMouseMotionListener(s);
	}
	
	//not used by front end
	private void update() {
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		Point thisPos = getLocationOnScreen();
		mouseX = mousePos.x - thisPos.x;
		mouseY = mousePos.y - thisPos.y;
	}

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		AGraphics g = new AGraphics(gr);
		update();
		stage.updateComponents();
		stage.update();
		stage.renderBackground(g);
		stage.renderComponents(g);
		stage.render(g);
	}

	@Override
	public int getMouseX() {
		return mouseX;
	}

	@Override
	public int getMouseY() {
		return mouseY;
	}

	@Override
	public boolean isHoldingKey(int code) {
		return holdingKeys.contains(code);
	}

}
