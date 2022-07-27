package edu.iastate.cs228.hw2;

/**
 * A Point Class
 * 
 * @author Sylvia Nguyen
 *
 */

public class Point implements Comparable<Point> {
	private int x;
	private int y;

	public static boolean xORy; // compare x coordinates if xORy == true and y coordinates otherwise
								// To set its value, use Point.xORy = true or false.

	public Point() // default constructor
	{

		// x and y get default value 0
	}

	/**
	 * Point constructor
	 * 
	 * @param x the x - coordinate
	 * @param y the y - coordinate
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Copy constructor (deep copy)
	 * 
	 * @param p - the point being copied
	 */
	public Point(Point p) { // copy constructor
		x = p.getX();
		y = p.getY();
	}

	/**
	 * Get the x-coordinate
	 * 
	 * @return x
	 */

	public int getX() {
		return x;
	}

	/**
	 * Get the y-coordinate
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the value of the static instance variable xORy.
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		Point.xORy = xORy;
		// TODO
	}

	/**
	 * Overriding the equal method for Point class
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compare this point with a second point q depending on the value of the static
	 * variable xORy
	 * 
	 * @param q
	 * @return -1 if (xORy == true && (this.x < q.x || (this.x == q.x && this.y <
	 *         q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x
	 *         < q.x))) 0 if this.x == q.x && this.y == q.y) 1 otherwise
	 */
	public int compareTo(Point q) {
		if ((xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y)))
				|| (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) {

			return -1;

		}
		if (this.x == q.x && this.y == q.y) {
			return 0;
		}

		return 1;
		// TODO;
	}

	/**
	 * Output a point in the standard form (x, y).
	 */
	@Override
	public String toString() {
		String pointOutput;

		pointOutput = "(" + x + ", " + y + ")";

		// TODO
		return pointOutput;
	}
}
