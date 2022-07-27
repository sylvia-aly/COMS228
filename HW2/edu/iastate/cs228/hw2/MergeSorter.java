package edu.iastate.cs228.hw2;

/**
 *  
 * @author Sylvia Nguyen
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "mergesort";
		// TODO
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {

		mergeSortRec(points);
		// TODO
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		int n = pts.length;
		if (n < 2) {
			return;
		}
		int middle = n / 2;
		Point[] L = new Point[middle];
		Point[] R = new Point[n - middle];
		for (int i = 0; i < middle; i++) {
			L[i] = new Point(pts[i]);
		}

		for (int i = middle; i < n; i++) {
			R[i - middle] = new Point(pts[i]);
		}
		mergeSortRec(L);
		mergeSortRec(R);
		merge(pts, L, R);

	}

	private void merge(Point[] arr, Point[] left, Point[] right) {
		int leftLength = left.length;
		int rightLength = right.length;

		int i = 0;
		int j = 0;
		int k = 0;
		while (i < leftLength && j < rightLength) {
			if (pointComparator.compare(left[i], right[j]) == 0 || pointComparator.compare(left[i], right[j]) == -1) {
				arr[k] = left[i];
				i++;

			} else {
				arr[k] = right[j];
				j++;
			}
			k++;
		}
		while (i < leftLength) {
			arr[k] = left[i];
			i++;
			k++;
		}
		while (j < rightLength) {
			arr[k] = right[j];
			j++;
			k++;
		}

	}

	// Other private methods in case you need ...

}
