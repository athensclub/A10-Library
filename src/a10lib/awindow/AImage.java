package a10lib.awindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AImage {
	
	private BufferedImage img;
	
	public AImage(BufferedImage i) {
		setImage(i);
	}
	
	public void setImage(BufferedImage i) {
		img = i;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public static AImage read(String path) {
		try {
			BufferedImage i = ImageIO.read(new File(path));
			return new AImage(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void resize(int newWidth,int newHeight) {
		BufferedImage result = new BufferedImage(newWidth, newHeight, 6);
 
        // scales the input image to the output image
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        img = result;
	}

}
