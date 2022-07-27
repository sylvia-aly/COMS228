package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Wildlife class performs a simulation of a grid plain with squares
 * inhabited by badgers, foxes, rabbits, grass, or none.
 *
 * @author Sylvia Nguyen
 */
public class Wildlife {
	/**
	 * Update the new plain from the old plain in one cycle.
	 *
	 * @param pOld old plain
	 * @param pNew new plain
	 */
	public static void updatePlain(Plain pOld, Plain pNew) {
		// TODO
		//
		// For every life form (i.e., a Living object) in the grid pOld, generate
		// a Living object in the grid pNew at the corresponding location such that
		// the former life form changes into the latter life form.
		//
		// Employ the method next() of the Living class.
		for (int i = 0; i < pOld.grid.length; i++) {
			for (int j = 0; j < pOld.grid[0].length; j++) {

				pNew.grid[i][j].next(pOld);

			}
		}

		for (int i = 0; i < pNew.grid.length; i++) {
			for (int j = 0; j < pNew.grid[0].length; j++) {

				pNew.grid[i][j] = pOld.grid[i][j];

			}
		}

	}

	/**
	 * Repeatedly generates plains either randomly or from reading files. Over each
	 * plain, carries out an input number of cycles of evolution.
	 *
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

// TODO
//
// Generate wildlife simulations repeatedly like shown in the
// sample run in the project description.
//
// 1. Enter 1 to generate a random plain, 2 to read a plain from an input
// file, and 3 to end the simulation. (An input file always ends with
// the suffix .txt.)
//
// 2. Print out standard messages as given in the project description.
//
// 3. For convenience, you may define two plains even and odd as below.
// In an even numbered cycle (starting at zero), generate the plain
// odd from the plain even; in an odd numbered cycle, generate even
// from odd.

		Plain even; // the plain after an even number of cycles
		Plain odd; // the plain after an odd number of cycles

// 4. Print out initial and final plains only. No intermediate plains should
// appear in the standard output. (When debugging your program, you can
// print intermediate plains.)
//
// 5. You may save some randomly generated plains as your own test cases.
//
// 6. It is not necessary to handle file input & output exceptions for this
// project. Assume data in an input file to be correctly formated.

		System.out.println("Simulation of Wildlife of the Plain");
		System.out.println("keys: 1 (random plain) 2 (file input) 3 (exit)");
		int trialTracker = 1;
		System.out.print("Trial " + trialTracker + ": ");

		Scanner scan = new Scanner(System.in);
		Scanner scanner = new Scanner(System.in);
		int userInput = scan.nextInt();
		while (userInput == 1 || userInput == 2) {
			trialTracker++;
			if (userInput == 1) {
				System.out.println("Random plain");
				System.out.print("Enter grid width: ");
				int gridWidth = scan.nextInt();

				System.out.print("Enter the number of cycles: ");
				int cycle = scan.nextInt();
				System.out.print("\n\n");
				System.out.println("Initial plain: \n\n");

				even = new Plain(gridWidth);
				odd = new Plain(gridWidth);
				odd.randomInit();
				for (int i = 0; i < gridWidth; i++) {
					for (int j = 0; j < gridWidth; j++) {
						even.grid[i][j] = odd.grid[i][j];
					}
				}

				System.out.println(even.toString());
				for (int i = 0; i < cycle; i++) {
					if (i % 2 == 0) {

						updatePlain(even, odd);

					} else {

						updatePlain(odd, even);

					}
				}

				System.out.println("\nFinal plain:\n\n");
				if (cycle % 2 != 0) {
					System.out.println(odd.toString());
				} else {
					System.out.println(even.toString());
				}

			}

			if (userInput == 2) {
				scanner = new Scanner(System.in);
				System.out.println("Plain input from a file");
				System.out.print("File name: ");
				String fileName = scanner.nextLine();
				even = new Plain(fileName);
				odd = new Plain(fileName);

				System.out.print("Enter the number of cycles: ");
				int cycle = scanner.nextInt();
				while (cycle <= 0) {
					cycle = scanner.nextInt();
				}
				System.out.print("\n\n");
				System.out.println("Initial plain: \n\n");
				System.out.println(even.toString());
				for (int i = 0; i < cycle; i++) {
					if (i % 2 == 0) {

						updatePlain(even, odd);

					}
					if (i % 2 != 0) {

						updatePlain(odd, even);

					}
				}

				System.out.println("\nFinal plain:\n\n");
				if (cycle % 2 != 0) {
					System.out.println(odd.toString());
				} else {
					System.out.println(even.toString());
				}

			}

			System.out.print("\nTrial " + trialTracker + ": ");
			userInput = scan.nextInt();

		}
		scan.close();
		scanner.close();
	}

}
