package edu.iastate.cs228.hw2;
/**
 * 
 * @author Sylvia Nguyen
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.File;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class RotationalPointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected String outputFileName; // "select.txt", "insert.txt", "merge.txt", or "quick.txt"

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[]. Set
	 * outputFileName.
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public RotationalPointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		points = new Point[pts.length];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(pts[i]);
		}
		sortingAlgorithm = algo;
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			outputFileName = "select.txt";
		}
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			outputFileName = "insert.txt";
		}
		if (sortingAlgorithm == Algorithm.MergeSort) {
			outputFileName = "merge.txt";
		}
		if (sortingAlgorithm == Algorithm.QuickSort) {
			outputFileName = "quick.txt";
		}

	}

	/**
	 * This constructor reads points from a file. Set outputFileName.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected RotationalPointScanner(String inputFileName, Algorithm algo)
			throws FileNotFoundException, InputMismatchException {
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file);
		int count = 0;
		while (scan.hasNextInt()) {
			scan.nextInt();
			count++;
		}
		scan.close();
		if (count % 2 != 0) {
			throw new InputMismatchException();

		}

		points = new Point[count / 2];
		Scanner scanner = new Scanner(file);
		for (int i = 0; i < points.length; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();

			points[i] = new Point(x, y);

		}

		scanner.close();
		sortingAlgorithm = algo;
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			outputFileName = "select.txt";
		}
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			outputFileName = "insert.txt";
		}
		if (sortingAlgorithm == Algorithm.MergeSort) {
			outputFileName = "merge.txt";
		}
		if (sortingAlgorithm == Algorithm.QuickSort) {
			outputFileName = "quick.txt";
		}
		// TODO
	}

	/**
	 * Carry out three rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates. d) Sort points[] again by the polar angle with respect to
	 * medianCoordinatePoint.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting. Copy the
	 * sorting result back onto the array points[] by calling the method getPoints()
	 * in AbstractSorter.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {

		// TODO

		AbstractSorter aSorter;
		if (sortingAlgorithm == Algorithm.InsertionSort) {

			aSorter = new InsertionSorter(points);
			// This section focus on sorting the points by their x-coordinate
			aSorter.setComparator(0);
			long startTime1 = System.nanoTime();
			aSorter.sort();
			long endTime1 = System.nanoTime();
			aSorter.getPoints(points);
			int x = aSorter.getMedian().getX();
			// This section focus on sorting the points by their y-coordinate
			aSorter.setComparator(1);
			long startTime2 = System.nanoTime();
			aSorter.sort();
			long endTime2 = System.nanoTime();
			aSorter.getPoints(points);
			int y = aSorter.getMedian().getY();
			// This section focus on sorting the points by the polar angles w.r.t the median
			// coordinate point
			medianCoordinatePoint = new Point(x, y);
			aSorter.setReferencePoint(medianCoordinatePoint);
			aSorter.setComparator(2);
			long startTime3 = System.nanoTime();
			aSorter.sort();
			long endTime3 = System.nanoTime();
			aSorter.getPoints(this.points);
			scanTime = (endTime1 - startTime1) + (endTime2 - startTime2) + (endTime3 - startTime3);

		}
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
			// This section focus on sorting the points by their x-coordinate
			aSorter.setComparator(0);
			long startTime1 = System.nanoTime();
			aSorter.sort();
			aSorter.getPoints(this.points);
			long endTime1 = System.nanoTime();
			aSorter.getPoints(points);
			int x = aSorter.getMedian().getX();
			// This section focus on sorting the points by their y-coordinate
			aSorter.setComparator(1);
			long startTime2 = System.nanoTime();
			aSorter.sort();
			long endTime2 = System.nanoTime();
			aSorter.getPoints(points);
			int y = aSorter.getMedian().getY();
			medianCoordinatePoint = new Point(x, y);
			aSorter.setReferencePoint(medianCoordinatePoint);
			// This section focus on sorting the points by the polar angles w.r.t the median
			// coordinate point
			aSorter.setComparator(2);
			long startTime3 = System.nanoTime();
			aSorter.sort();
			long endTime3 = System.nanoTime();
			aSorter.getPoints(this.points);
			scanTime = (endTime1 - startTime1) + (endTime2 - startTime2) + (endTime3 - startTime3);
		}
		if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
			// This section focus on sorting the points by their x-coordinate
			aSorter.setComparator(0);
			long startTime1 = System.nanoTime();
			aSorter.sort();
			long endTime1 = System.nanoTime();
			aSorter.getPoints(this.points);
			int x = aSorter.getMedian().getX();
			// This section focus on sorting the points by their y-coordinate
			aSorter.setComparator(1);
			long startTime2 = System.nanoTime();
			aSorter.sort();
			long endTime2 = System.nanoTime();
			aSorter.getPoints(this.points);
			int y = aSorter.getMedian().getY();
			medianCoordinatePoint = new Point(x, y);
			aSorter.setReferencePoint(medianCoordinatePoint);
			// This section focus on sorting the points by the polar angles w.r.t the median
			// coordinate point
			aSorter.setComparator(2);
			long startTime3 = System.nanoTime();
			aSorter.sort();
			long endTime3 = System.nanoTime();
			aSorter.getPoints(this.points);
			scanTime = (endTime1 - startTime1) + (endTime2 - startTime2) + (endTime3 - startTime3);
		}
		if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
			// This section focus on sorting the points by their x-coordinate
			aSorter.setComparator(0);
			long startTime1 = System.nanoTime();
			aSorter.sort();
			long endTime1 = System.nanoTime();
			aSorter.getPoints(this.points);
			int x = aSorter.getMedian().getX();
			// This section focus on sorting the points by their y-coordinate
			aSorter.setComparator(1);
			long startTime2 = System.nanoTime();
			aSorter.sort();
			long endTime2 = System.nanoTime();
			aSorter.getPoints(this.points);
			int y = aSorter.getMedian().getY();
			medianCoordinatePoint = new Point(x, y);
			aSorter.setReferencePoint(medianCoordinatePoint);
			// This section focus on sorting the points by the polar angles w.r.t the median
			// coordinate point
			aSorter.setComparator(2);
			long startTime3 = System.nanoTime();
			aSorter.sort();
			long endTime3 = System.nanoTime();
			aSorter.getPoints(this.points);
			scanTime = (endTime1 - startTime1) + (endTime2 - startTime2) + (endTime3 - startTime3);
		}

		// create an object to be referenced by aSorter according to sortingAlgorithm.
		// for each of the three
		// rounds of sorting, have aSorter do the following:
		//
		// a) call setComparator() with an argument 0, 1, or 2. in case it is 2, must
		// have made
		// the call setReferencePoint(medianCoordinatePoint) already.
		//
		// b) call sort().
		//
		// sum up the times spent on the three sorting rounds and set the instance
		// variable scanTime.

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		String a = "";
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			a += "SelectionSort " + points.length + " " + scanTime;
		}
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			a += "InsertionSort " + points.length + " " + scanTime;
		}
		if (sortingAlgorithm == Algorithm.MergeSort) {
			a += "MergeSort " + points.length + " " + scanTime;
		}
		if (sortingAlgorithm == Algorithm.QuickSort) {
			a += "QuickSort " + points.length + " " + scanTime;
		}

		return a;
		// TODO
	}

	/**
	 * Write points[] after a call to scan(). When printed, the points will appear
	 * in order of polar angle with respect to medianCoordinatePoint with every
	 * point occupying a separate line. The x and y coordinates of the point are
	 * displayed on the same line with exactly one blank space in between.
	 */
	@Override
	public String toString() {
		String pointsToFileFormat = "";
		for (int i = 0; i < points.length; i++) {
			pointsToFileFormat += points[i].getX() + " " + points[i].getY() + "\n";

		}
		return pointsToFileFormat;
		// TODO
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException {

		String pointsToFile = this.toString();
		Scanner in = new Scanner(pointsToFile);
		File outFile = new File(outputFileName);
		PrintWriter out = new PrintWriter(outFile);

		while (in.hasNextLine()) {
			String line = in.nextLine();
			out.println(line);
		}
		out.close();
		in.close();
		// TODO
	}

	/**
	 * This method is called after each scan for visually check whether the result
	 * is correct. You just need to generate a list of points and a list of
	 * segments, depending on the value of sortByAngle, as detailed in Section 4.1.
	 * Then create a Plot object to call the method myFrame().
	 */
	public void draw() {

		// Removing duplicates to have unique points
		Point[] temp1 = new Point[points.length];
		int countDuplicates = 0;
		int j = 0;
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].equals(points[i + 1]) == false) {
				temp1[j] = points[i];
				j++;
			} else {
				countDuplicates += 1;
			}

		}

		temp1[j] = points[points.length - 1];

		// Array without duplicates and null
		Point[] temp2 = Arrays.copyOf(temp1, points.length - countDuplicates);

		int numSegs = (temp2.length * 2); // number of segments to draw

		// Based on Section 4.1, generate the line segments to draw for display of the
		// sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order.
		Segment[] segments = new Segment[numSegs];

		// TODO

		// Count how many segments are needed

		int count = 0;
		for (int i = 0; i < temp2.length; i++) {
			if (temp2[i].equals(temp2[temp2.length - 1]) == false) {
				segments[count] = new Segment(temp2[i], temp2[i + 1]);
			} else {
				segments[count] = new Segment(temp2[i], temp2[0]);
			}
			count++;
		}

		for (int i = 0; i < temp2.length; i++) {
			segments[count] = new Segment(medianCoordinatePoint, temp2[i]);
			count++;
		}

		String sort = null;

		switch (sortingAlgorithm) {
		case SelectionSort:
			sort = "Selection Sort";
			break;
		case InsertionSort:
			sort = "Insertion Sort";
			break;
		case MergeSort:
			sort = "Mergesort";
			break;
		case QuickSort:
			sort = "Quicksort";
			break;
		default:
			break;
		}

		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, sort);

	}

}
	

