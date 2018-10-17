package a10lib.awindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AImage {

	private BufferedImage img;

	/**
	 * Create new instance of AImage containing given java.awt.image.BufferedImage
	 * 
	 * @param i:
	 *            the java.awt.image.BufferedImage that this AImage is containing
	 */
	public AImage(BufferedImage i) {
		setImage(i);
	}

	/**
	 * Set the current java.awt.image.BufferedImage of this AImage
	 * 
	 * @param i:
	 *            the java.awt.image.BufferedImage to be set to this AImage
	 */
	public void setImage(BufferedImage i) {
		img = i;
	}

	/**
	 * Get the current java.awt.image.BufferedImage of this AImage
	 * 
	 * @return the current java.awt.image.BufferedImage of this AImage
	 */
	public BufferedImage getImage() {
		return img;
	}

	/**
	 * Get the current width of this AImage
	 * 
	 * @return the current width of this AImage
	 */
	public int getWidth() {
		return getImage().getWidth();
	}

	/**
	 * Get the current height of this AImage
	 * 
	 * @return the current height of this AImage
	 */
	public int getHeight() {
		return getImage().getHeight();
	}

	/**
	 * Create new instance of AImage by loading Image from the file path given
	 * 
	 * @param path:
	 *            the file path of image
	 * @return new instance of AImage by loading Image from the file path given
	 */
	public static AImage read(String path) {
		try {
			BufferedImage i = ImageIO.read(new File(path));
			return new AImage(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Resize this AImage to the new size
	 * 
	 * @param newWidth:
	 *            width of the new size
	 * @param newHeight:
	 *            height of the new size
	 */
	public void resize(int newWidth, int newHeight) {
		BufferedImage result = new BufferedImage(newWidth, newHeight, 6);

		// scales the input image to the output image
		Graphics2D g2d = result.createGraphics();
		g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
		g2d.dispose();
		img = result;
	}

}
