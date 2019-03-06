package a10lib.math;

import java.util.Arrays;

/**
 * A class for geometry calculations
 * 
 * @author Athensclub
 *
 */
public final class Geometry {

    private Geometry() {
    }

    /**
     * Get the distance between two points
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double distance(double x1, double y1, double x2, double y2) {
	if (x1 == x2) {
	    return Math.abs(y1 - y2);
	}
	if (y1 == y2) {
	    return Math.abs(x1 - x2);
	}
	return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Test whether a point hit a rectangle
     * 
     * @param px
     *            point x position
     * @param py
     *            point y position
     * @param rx
     *            rectangle x position
     * @param ry
     *            rectangle y position
     * @param rw
     *            rectangle width
     * @param rh
     *            rectangle height
     * @return whether a point hit a rectangle
     */
    public static boolean pointHitRect(double px, double py, double rx, double ry, double rw, double rh) {
	return px >= rx && px <= rx + rw && py >= ry && py <= ry + rh;
    }

    /**
     * Test whether a point hit a rectangle
     * 
     * @param px
     *            point x position
     * @param py
     *            point y position
     * @param rect
     *            the bounds of square.Must be array of length 4 with the following
     *            data in order:<br>
     *            <code>[x, y, width, height]</code>
     * @return whether a point hit a rectangle
     */
    public static boolean pointHitRect(double px, double py, double[] rect) {
	if (rect.length != 4) {
	    throw new IllegalArgumentException(
		    "Square hit test with bounds array length != 4: " + rect + ":" + Arrays.toString(rect));
	}
	return pointHitRect(px, py, rect[0], rect[1], rect[2], rect[3]);
    }

}
