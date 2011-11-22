package p4vision;

/**
 * P4Blob.java
 * This file is a modification of original file Blob.java by Lot Amorós for make it serializable.
 * 
 * (by) Douglas Edric Stanley & Cousot Stéphane
 * (cc) some right reserved
 *
 * Part of the Processing/Java OpenCV Libraries project, for the Atelier Hypermedia, art 
 * school of Aix-en-Provence, and for the Processing and Java community of course.
 *
 * THIS FILE IS RELEASED UNDER A CREATIVE COMMONS ATTRIBUTION 3.0 LICENSE
 * ‹ http://creativecommons.org/licenses/by/3.0/ ›
 */

import hypermedia.video.Blob;
import java.awt.*;

/**
 * A storage object containing a blob detected by OpenCV. Returned by
 * <code>blobs()</code> method.
 */
public class P4blob implements java.io.Serializable {
	private static final long serialVersionUID = 1359039846705303728L;

	/** The area of the blob in pixels */
	public float area = 0f;
	/** The length of the perimeter in pixels */
	public float length = 0f;
	/** The centroid or barycenter of the blob */
	public Point centroid = new Point();
	/** The containing rectangle of the blob */
	public Rectangle rectangle = new Rectangle();
	/** The list of points defining the shape of the blob */
	public Point[] points = new Point[0];
	/** Is this blob a hole inside of another blob? */
	public boolean isHole = false;

	/**
	 * A list of color int, containing the image pixels created by loadPixels()
	 * 
	 * @see #loadPixels
	 */
	public int[] pixels = new int[0];

	/**
	 * Create a new Blob with the default properties.
	 */
	public P4blob() {
	/*	this.area = 0;
		this.length = 0;
		this.centroid = new Point(widthVisual/2,);
		this.rectangle = rect;
		this.points = points;
		this.isHole = isHole;*/
	}

	/**
	 * Create a new Blob with the given properties.
	 * 
	 * @param area
	 *            the shape area
	 * @param length
	 *            the contour length
	 * @param centroid
	 *            the shape barycentre point
	 * @param rect
	 *            the shape rectangle
	 * @param points
	 *            the contour points
	 * @param isHole
	 *            true whether this blob is completly inside a bigger one
	 */
	public P4blob(float area, float length, Point centroid, Rectangle rect,
			Point[] points, boolean isHole) {
		this.area = area;
		this.length = length;
		this.centroid = centroid;
		this.rectangle = rect;
		this.points = points;
		this.isHole = isHole;
	}
	
	/**
	 * Create a new P4Blob from a original blob from opencv
	 * @param b
	 */
	public P4blob(Blob b){
		this.area = b.area;
		this.length = b.length;
		this.centroid = b.centroid;
		this.rectangle = b.rectangle;
		this.points = b.points;
		this.isHole = b.isHole;
	}

	public P4blob(P4blob b) {
		this.area = b.area;
		this.length = b.length;
		this.centroid = b.centroid;
		this.rectangle = b.rectangle;
		this.points = b.points;
		this.isHole = b.isHole;
	}
}
