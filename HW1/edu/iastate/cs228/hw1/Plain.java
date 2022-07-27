package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * The plain is represented as a square grid of size width x width.
 * 
 * @author Sylvia Nguyen
 *
 */
public class Plain {

	private int width; // grid size: width X width

	public Living[][] grid;

	/**
	 * Default constructor reads from a file
	 */
	public Plain(String inputFileName) throws FileNotFoundException {
		// TODO
		//
		// Assumption: The input file is in correct format.
		//
		// You may create the grid plain in the following steps:
		//
		// 1) Reads the first line to determine the width of the grid.
		//
		// 2) Creates a grid object.
		//
		// 3) Fills in the grid according to the input file.
		//
		// Be sure to close the input file when you are done.
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file);
		String line = scan.nextLine();
		Scanner scan2 = new Scanner(line);
		width = 0;
		while (scan2.hasNext()) {
			scan2.next();
			width++;
		}

		grid = new Living[width][width];

		scan.close();
		scan2.close();

		Scanner scanner = new Scanner(file);
		int row = 0;
		int col = 0;

		while (scanner.hasNextLine()) {
			String indLine = scanner.nextLine();
			Scanner scanner2 = new Scanner(indLine);
			while (scanner2.hasNext()) {
				String word = scanner2.next();

				if (word.charAt(0) == 'B') {
					int age = Character.getNumericValue(word.charAt(1));
					grid[row][col] = new Badger(this, row, col, age);
					col++;

				}
				if (word.charAt(0) == 'F') {
					int age = Character.getNumericValue(word.charAt(1));
					grid[row][col] = new Fox(this, row, col, age);
					col++;

				}
				if (word.charAt(0) == 'R') {
					int age = Character.getNumericValue(word.charAt(1));
					grid[row][col] = new Rabbit(this, row, col, age);
					col++;

				}
				if (word.charAt(0) == 'G') {
					grid[row][col] = new Grass(this, row, col);
					col++;
				}
				if (word.charAt(0) == 'E') {
					grid[row][col] = new Empty(this, row, col);
					col++;
				}

			}
			scanner2.close();
			row++;
			col = 0;
		}

		scanner.close();

	}

	/**
	 * Constructor that builds a w x w grid without initializing it.
	 *
	 * @param width the grid
	 */
	public Plain(int w) {
		// TODO
		width = w;
		grid = new Living[width][width];

	}

	public int getWidth() {
// TODO
		return width; // to be modified
	}

	/**
	 * Initialize the plain by randomly assigning to every square of the grid one of
	 * BADGER, FOX, RABBIT, GRASS, or EMPTY.
	 *
	 * Every animal starts at age 0.
	 */
	public void randomInit() {
		Random generator = new Random();
		grid = new Living[width][width];
// 0 - Badger, 1 - Fox, 2 - Rabbit, 3 - Grass, 4 - Empty

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				int fillSpot = generator.nextInt(5);

				if (fillSpot == 0) {
					grid[i][j] = new Badger(this, i, j, 0);

				}
				if (fillSpot == 1) {
					grid[i][j] = new Fox(this, i, j, 0);

				}
				if (fillSpot == 2) {
					grid[i][j] = new Rabbit(this, i, j, 0);

				}
				if (fillSpot == 3) {
					grid[i][j] = new Grass(this, i, j);

				}
				if (fillSpot == 4) {
					grid[i][j] = new Empty(this, i, j);

				}

			}
		}

// TODO
	}

	/**
	 * Output the plain grid. For each square, output the first letter of the living
	 * form occupying the square. If the living form is an animal, then output the
	 * age of the animal followed by a blank space; otherwise, output two blanks.
	 */
	public String toString() {
		String stringHolder = "";

// TODO
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int age = 0;
				if (grid[i][j].who() == State.BADGER) {
					age = (((Animal) grid[i][j]).myAge());
					stringHolder += "B" + age + " ";
				} else if (grid[i][j].who() == State.FOX) {
					age = (((Animal) grid[i][j]).myAge());
					stringHolder += "F" + age + " ";
				} else if (grid[i][j].who() == State.RABBIT) {
					age = (((Animal) grid[i][j]).myAge());
					stringHolder += "R" + age + " ";
				} else if (grid[i][j].who() == State.EMPTY) {

					stringHolder += "E" + "  ";
				} else if (grid[i][j].who() == State.GRASS) {

					stringHolder += "G" + "  ";
				}

			}
			stringHolder += "\n";
		}

		return stringHolder;
	}

	/**
	 * Write the plain grid to an output file. Also useful for saving a randomly
	 * generated plain for debugging purpose.
	 *
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException {
		// TODO
		//
		// 1. Open the file.
		//
		// 2. Write to the file. The five life forms are represented by characters
		// B, E, F, G, R. Leave one blank space in between. Examples are given in
		// the project description.
		//
		// 3. Close the file.
		Scanner scan = new Scanner(System.in);
		File newFile = new File(outputFileName);
		PrintWriter out = new PrintWriter(newFile);

		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			out.println(line);
		}
		out.close();
		scan.close();

	}
}
