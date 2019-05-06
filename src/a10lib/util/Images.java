package a10lib.util;

import java.awt.image.BufferedImage;

/**
 * A utilities class for image processing.
 * 
 * @author Athensclub
 *
 */
public final class Images {

    public static final FlipType FLIP_VERTICAL = FlipType.VERTICAL, FLIP_HORIZONTAL = FlipType.HORIZONTAL;

    public static enum FlipType {

	VERTICAL((x, w) -> x, (y, w) -> w - y - 1), HORIZONTAL((x, w) -> w - x - 1, (y, w) -> y);

	private FlipIndexFunction indexX, indexY;

	private FlipType(FlipIndexFunction x, FlipIndexFunction y) {
	    indexX = x;
	    indexY = y;
	}

    }

    @FunctionalInterface
    private static interface FlipIndexFunction {

	public int getIndex(int i, int width);

    }

    private Images() {

    }

    /**
     * Flip (mirror) the image with the specified flip type. Creates new image.
     * 
     * @param image
     * @param type
     */
    public static BufferedImage flip(BufferedImage image, FlipType type) {
	int width = image.getWidth();
	int height = image.getHeight();
	BufferedImage result = new BufferedImage(width, height,image.getType());
	for (int x = 0; x < image.getWidth(); x++) {
	    for (int y = 0; y < image.getHeight(); y++) {
		result.setRGB(x, y, image.getRGB(type.indexX.getIndex(x,width), type.indexY.getIndex(y,height)));
	    }
	}
	return result;
    }

}
