package a10lib.awindow;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AFrame extends JFrame {

	/**
	 * eclipse generated
	 */
	private static final long serialVersionUID = -48033537759175832L;

	private APanel panel;

	private AImage icon;

	/**
	 * Create new instance of AFrame
	 */
	public AFrame() {
		this("");
	}

	/**
	 * Get the current AStage that this AFrame is containing
	 * 
	 * @return the current stage that this AFrame is containing
	 */
	public AStage getStage() {
		return panel.getStage();
	}

	/**
	 * Get the APanel that this AFrame is containing
	 * 
	 * @return the APanel that this AFrame is containing
	 */
	public APanel getPanel() {
		return panel;
	}

	/**
	 * Create new new instance of AFrame with the given title
	 * 
	 * @param str:
	 *            the title of this AFrame
	 */
	public AFrame(String str) {
		super(str);
		panel = new APanel(null);
		add(panel);
	}

	/**
	 * Set the AStage that this AFrame will contain
	 * 
	 * @param s:
	 *            the AStage that this AFrame will contain
	 */
	public void setStage(AStage s) {
		panel.setStage(s);
	}

	/**
	 * Set the icon image of this AFrame
	 * 
	 * @param img:
	 *            the image that is used as icon of this AFrame
	 */
	public void setIcon(AImage img) {
		super.setIconImage(img.getImage());
		icon = img;
	}

	/**
	 * Get the icon image of this AFrame
	 * 
	 * @return the icon image of this AFrame
	 */
	public AImage getIcon() {
		return icon;
	}

	/**
	 * Render one frame into this AFrame
	 */
	public void render() {
		SwingUtilities.invokeLater(()->{
			panel.repaint();
		});
	}

}
