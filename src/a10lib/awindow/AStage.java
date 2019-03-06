package a10lib.awindow;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import a10lib.awindow.acomponent.AComponent;

@Deprecated
public abstract class AStage implements MouseListener, KeyListener, MouseMotionListener,MouseTracker {

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
	public int getMouseX() {
		return getWindow().getMouseX();
	}
	
	@Override
	public int getMouseY() {
		return getWindow().getMouseY();
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

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
		int mouseX = window.getMouseX();
		int mouseY = window.getMouseY();
		boolean changedCursor = false;
		for (AComponent c : getComponents()) {
			if(c.getBounds().contains(mouseX, mouseY)) {
				window.setCursorIcon(c.getCursor());
				changedCursor = true;
			}
			c.update(window);
		}
		if(!changedCursor) {
			window.setCursorIcon(Cursor.getDefaultCursor());
		}
	}

	// not to be used by front end
	protected final void onKeyPressed(KeyEvent e) {
		for (AComponent c : getComponents()) {
			c.onKeyPressed(e);
		}
	}

	// not to be used by front end
	protected final void onKeyReleased(KeyEvent e) {
		for (AComponent c : getComponents()) {
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
	 * Add new AComponent to this AStage
	 * 
	 * @param c:
	 *            AComponent to be added to this AStage
	 */
	public void addComponents(AComponent... c) {
		for (AComponent com : c) {
			addComponent(com);
		}
	}

	/**
	 * Remove AComponent from this AStage
	 * 
	 * @param c:
	 *            AComponent to be removed from this AStage
	 */
	public void removeComponents(AComponent... c) {
		for (AComponent com : c) {
			removeComponent(com);
		}
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
