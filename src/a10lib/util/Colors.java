package a10lib.util;

import static a10lib.math.Maths.*;
import static java.lang.Math.*;

/**
 * A utilities class for color processing
 * 
 * @author Athensclub
 *
 */
public final class Colors {

    private Colors() {}
    
    /**
     * Merge 3 number from 0 to 255 to one integer value that contain 3 8-bits
     * values
     * 
     * @param r
     *              the first component of color (0-255).For RGB color, it is red
     *              component.For Y'UV color,it is Y' component of color.
     * @param g
     *              the second component of color (0-255).For RGB color,it is green
     *              component.For Y'UV color,it is U component of color.
     * @param b
     *              the third component of color (0-255).For RGB color,it is blue
     *              component.For Y'UV color,it is V component of color.
     * @return
     */
    public static int bits8ToInt(int r, int g, int b) {
	return (r << 16) | (g << 8) | b;
    }

    /**
     * Get value of first component of merged 3 8-bits int value.For RGB merged int
     * value, this will return red component.For Y'UV color,this will return Y'
     * component.
     * 
     * @param col
     *                the merged 3 8-bits int value to get first component from.
     * @return
     */
    public static int firstFromInt(int col) {
	return (col >> 16) & 0xFF;
    }

    /**
     * Get value of second component of merged 3 8-bits int value.For RGB merged int
     * value, this will return green component.For Y'UV color,this will return U
     * component.
     * 
     * @param col
     *                the merged 3 8-bits int value to get second component from
     * @return
     */
    public static int secondFromInt(int col) {
	return (col >> 8) & 0xFF;
    }

    /**
     * Get value of second component of merged 3 8-bits int value.For RGB merged int
     * value, this will return blue component.For Y'UV color,this will return V
     * component.
     * 
     * @param col
     *                the merged 3 8-bits int value to get third component from
     * @return
     */
    public static int thirdFromInt(int col) {
	return col & 0xFF;
    }

    /**
     * Convert Y'UV color to RGB color.This implementation use NTSC version.
     * 
     * @param y
     * @param u
     * @param v
     * @return
     */
    public static int yuvToRGB(int y, int u, int v) {
	int c298 = 298 * (y - 16);
	int d = u - 128;
	int e = v - 128;
	return bits8ToInt(clamp((c298 + 409 * e + 128) >> 8, 0, 255),
		clamp((c298 - 100 * d - 208 * e + 128) >> 8, 0, 255), clamp((c298 + 516 * d + 128) >> 8, 0, 255));
    }

    /**
     * 
     * Convert Y'UV color to RGB color.This implementation use NTSC version.
     * 
     * @param yuv
     * @return
     */
    public static int yuvToRGB(int yuv) {
	return yuvToRGB(firstFromInt(yuv), secondFromInt(yuv), thirdFromInt(yuv));
    }

    /**
     * Convert RGB color to Y'UV color.This implementation use NTSC bit shifting
     * algorithm.
     * 
     * @see https://en.wikipedia.org/wiki/YUV
     * 
     * @return
     */
    public static int rgbToYUV(int r, int g, int b) {
	return bits8ToInt(((66 * r + 129 * g + 25 * b + 128) >> 8) + 16,
		((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128, ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128);
    }

    /**
     * Convert RGB color to Y'UV color.This implementation use NTSC bit shifting
     * algorithm.
     * 
     * @see https://en.wikipedia.org/wiki/YUV
     * 
     * @return
     */
    public static int rgbToYUV(int rgb) {
	return rgbToYUV(firstFromInt(rgb), secondFromInt(rgb), thirdFromInt(rgb));
    }

    /**
     * Convert 3 8-bits represented integer to 3 number
     * 
     * @param color
     * @return
     */
    public static String toString(int color) {
	return "color[" + firstFromInt(color) + "," + secondFromInt(color) + "," + thirdFromInt(color) + "]";
    }
    
    /**
     * Compare similarity between two colors.Two color must be the same type to function properly (RGB,Y'UV,etc.)
     * @param color1
     * @param color2
     * @return
     */
    public static int distance(int color1,int color2) {
	return abs(firstFromInt(color1)-firstFromInt(color2)) + abs(secondFromInt(color1)-secondFromInt(color2)) + abs(thirdFromInt(color1)-thirdFromInt(color2));
    }

}
