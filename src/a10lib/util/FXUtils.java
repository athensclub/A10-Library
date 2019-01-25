package a10lib.util;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public final class FXUtils {

    private FXUtils() {
    }

    /**
     * rotate the given graphics by the given degree
     * 
     * @param gc
     * @param angle
     * @param px
     * @param py
     */
    public static void rotate(GraphicsContext gc, double angle, double px, double py) {
	gc.setTransform(new Affine(new Rotate(angle, px, py)));
    }

    
    

    /**
     * Get the Point2D object where its x is minimum x position relative to parent
     * that still has it's children and its y is minimum y position that still has
     * it's children
     * 
     * @param parent
     *            the parent to find children min position
     * @return the Point2D object where its x is minimum x position relative to
     *         parent that still has it's children and its y is minimum y position
     *         that still has it's children
     */
    public static Point2D minPosition(Parent parent) {
	double minX = Double.MAX_VALUE;
	double minY = Double.MAX_VALUE;
	for (int i = 0; i < parent.getChildrenUnmodifiable().size(); i++) {
	    Node child = parent.getChildrenUnmodifiable().get(i);
	    Bounds childBounds = child.localToParent(child.getBoundsInLocal());
	    minX = Math.min(minX, childBounds.getMinX());
	    minY = Math.min(minY, childBounds.getMinY());
	}
	return new Point2D(minX, minY);
    }

    /**
     * Get the Point2D object where its x is maximum x position relative to parent
     * that still has it's children and its y is maximum y position that still has
     * it's children
     * 
     * @param parent
     *            the parent to find children maximum position
     * @return the Point2D object where its x is maximum x position relative to
     *         parent that still has it's children and its y is maximum y position
     *         that still has it's children
     */
    public static Point2D maxPosition(Parent parent) {
	double maxX = -Double.MAX_VALUE;
	double maxY = -Double.MAX_VALUE;
	for (int i = 0; i < parent.getChildrenUnmodifiable().size(); i++) {
	    Node child = parent.getChildrenUnmodifiable().get(i);
	    Bounds childBounds = child.localToParent(child.getBoundsInLocal());
	    maxX = Math.max(maxX, childBounds.getMinX() + childBounds.getWidth());
	    maxY = Math.max(maxY, childBounds.getMinY() + childBounds.getHeight());
	}
	return new Point2D(maxX, maxY);
    }

    /**
     * Set the size of the given parent to be the maximum x,y bounds of its children
     * 
     * @param parent
     *            the parent to resize
     */
    public static void packSize(Region parent) {
	Point2D max = maxPosition(parent);
	parent.setPrefSize(max.getX(), max.getY());
    }

    /**
     * Set the width of the given parent to be the minimum x bounds of its children
     * 
     * @param parent
     *            the parent to pack its children
     */
    public static void packSizeX(Region parent) {
	Point2D max = maxPosition(parent);
	parent.setPrefWidth(max.getX());
    }

    /**
     * Translate all the children with the same value so that the left-most object x
     * position is 0 then pack the width of its parent to fits perfectly
     * 
     * @param parent
     *            the parent to pack its children
     */
    public static void packAllX(Region parent) {
	Point2D min = minPosition(parent);
	Translate translate = new Translate(-min.getX(), 0);
	parent.getChildrenUnmodifiable().forEach(child -> {
	    child.getTransforms().add(0, translate);
	});
	packSizeX(parent);
    }

    /**
     * Set the height of the parent to be maximum y bounds of its children
     * 
     * @param parent
     *            the parent to pack its children
     */
    public static void packSizeY(Region parent) {
	Point2D max = maxPosition(parent);
	parent.setPrefHeight(max.getY());
    }

    /**
     * Translate all the children with the same value so that top-most object y
     * position is at 0 then set its parent height to fit perfectly
     * 
     * @param parent
     *            the parent to pack its children
     */
    public static void packAllY(Region parent) {
	Point2D min = minPosition(parent);
	Translate translate = new Translate(0, -min.getY());
	parent.getChildrenUnmodifiable().forEach(child -> {
	    child.getTransforms().add(0, translate);
	});
	packSizeY(parent);
    }

    /**
     * Pack all the children inside the given parent by the same amount so that the
     * node with lowest minX translated minX is at 0 and node with lowest minY
     * translated minY is at 0 and also pack the parent to match with the new size
     * if the parent is region
     * 
     * @param parent
     *            the parent to pack it children
     */
    public static void packAll(Region parent) {
	Point2D min = minPosition(parent);
	// translate
	Translate translate = new Translate(-min.getX(), -min.getY());
	for (int i = 0; i < parent.getChildrenUnmodifiable().size(); i++) {
	    Node child = parent.getChildrenUnmodifiable().get(i);
	    child.getTransforms().add(0, translate);
	}
	// packing
	packSize(parent);
    }

    /**
     * 
     * @return random javafx Color
     */
    public static Color randomColor() {
	ARandom random = ARandom.defaultRandom();
	return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

}
