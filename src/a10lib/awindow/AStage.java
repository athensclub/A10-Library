package a10lib.awindow;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import a10lib.awindow.acomponent.AComponent;

public abstract class AStage implements MouseListener, KeyListener, MouseMotionListener {

	private AWindow window;

	private ArrayList<AComponent> components = new ArrayList<>();

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
		g.setColor(Color.WHITE);
		g.fillRect(window.getBounds());
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
	public void keyTyped(KeyEvent e) {
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

	// not to be used by front end
	protected void renderComponents(AGraphics g) {
		for (AComponent c : getComponents()) {
			c.render(g);
		}
	}

	// not to be used by front end
	protected final void onMouseReleased(MouseEvent e) {
		for (AComponent c : getComponents()) {
			c.onMouseReleased(e);
		}
	}

	// not to be used by front end
	protected final void updateComponents() {
		for(AComponent c : getComponents()) {
			c.update(window);
		}
	}

	// not to be used by front end
	protected final void onKeyPressed(KeyEvent e) {
		for (AComponent c : getComponents()) {
			c.onKeyPressed(e);
		}
	}
	
	//not to be used by front end
	protected final void onKeyReleased(KeyEvent e) {
		for(AComponent c : getComponents()) {
			c.onKeyReleased(e);
		}
	}

	/**
	 * Add new AComponent to this AStage
	 * 
	 * @param c:
	 *            AComponent to be added to this AStage
	 */
	public void addComponent(AComponent c) {
		components.add(c);
	}

	/**
	 * Remove AComponent from this AStage
	 * 
	 * @param c:
	 *            AComponent to be removed from this AStage
	 */
	public void removeComponent(AComponent c) {
		components.add(c);
	}

	/**
	 * Get all the component of this AStage
	 * 
	 * @return all the component of this AStage
	 */
	public ArrayList<AComponent> getComponents() {
		return components;
	}

}
