package edu.iastate.cs228.hw2;

/**
 *  
 * @author Sylvia Nguyen
 *
 */

/**
 * 
 * This class implements selection sort.
 *
 */

public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "selection sort";
		// TODO
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		for (int i = 0; i < points.length - 1; ++i) {
			int minIndex = i;
			for (int j = i + 1; j < points.length; ++j) {
				if (pointComparator.compare(points[j], points[minIndex]) == -1) {
					minIndex = j;
				}
			}
			swap(i, minIndex);
		}
		// TODO
	}
}
