package a10lib.awindow;

import javax.swing.JFrame;

public class AFrame extends JFrame {

	private APanel panel;

	private AImage icon;

	public AFrame() {
		this("");
	}

	public AStage getStage() {
		return panel.getStage();
	}

	public APanel getPanel() {
		return panel;
	}

	public AFrame(String str) {
		super(str);
		panel = new APanel(null);
		add(panel);
	}

	public void setStage(AStage s) {
		panel.setStage(s);
	}

	public void setIcon(AImage img) {
		super.setIconImage(img.getImage());
		icon = img;
	}

	public AImage getIcon() {
		return icon;
	}

	public void render() {
		panel.repaint();
	}

}
