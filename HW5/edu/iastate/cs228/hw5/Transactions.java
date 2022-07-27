package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Sylvia Nguyen
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store.
 *
 */
public class Transactions {

	/**
	 * The main method generates a simulation of rental and return activities.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// TODO
		//
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the
		// the project description.
		
		
		// change the name of the file for video store
		VideoStore vs = new VideoStore("videoList1.txt");
		
		
		System.out.println("Transactions at a Video Store");
		System.out.println("keys: 1 (rent)     2 (bulk rent)");
		System.out.println("      3 (return)   4 (bulk return)");
		System.out.println("      5 (summary)  6 (exit)");
		//if the number is not between 1-6, then the user will be asked to input a number again until number is 1-6
		System.out.print("\nTransaction: ");
		Scanner keyValuesc = new Scanner(System.in);
		int key = keyValuesc.nextInt();

		Scanner filmInput = new Scanner(System.in);

		while (key != 6) {
			//single rent
			if (key == 1) {
				System.out.print("Film to rent: ");
				filmInput = new Scanner(System.in);
				String userInput = filmInput.nextLine();

				String filmName = (VideoStore.parseFilmName(userInput));
				int amountRented = (VideoStore.parseNumCopies(userInput));
				try {
					vs.videoRent(filmName, amountRented);

				} catch (IllegalArgumentException e) {
					System.out.println("Film " + filmName + " has an invalid request");
				} catch (AllCopiesRentedOutException e) {
					System.out.println("Film " + filmName + " has been rented out");
				} catch (FilmNotInInventoryException e) {
					System.out.println("Film " + filmName + " is not in inventory");
				}

			}

			// bulk rent
			if (key == 2) {
				System.out.print("Video file (rent): ");
				filmInput = new Scanner(System.in);
				String userInput = filmInput.nextLine();
				try {
					vs.bulkRent(userInput);
				} catch (AllCopiesRentedOutException e) {
					System.out.print(e.getMessage());
				} catch (FilmNotInInventoryException e) {
					System.out.print(e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.print(e.getMessage());
				}

			}
			//single film return 
			else if (key == 3) {
				System.out.print("Film to return: ");
				filmInput = new Scanner(System.in);
				String userInput = filmInput.nextLine();

				String filmName = (VideoStore.parseFilmName(userInput));
				int amountRented = (VideoStore.parseNumCopies(userInput));

				try {
					vs.videoReturn(filmName, amountRented);
				} catch (FilmNotInInventoryException e) {
					System.out.println(e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}

			}

			// bulk return
			else if (key == 4) {
				System.out.print("Video file (return):");
				filmInput = new Scanner(System.in);
				String userInput = filmInput.nextLine();

				try {
					vs.bulkReturn(userInput);
				} catch (FilmNotInInventoryException e) {
					System.out.println(e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}

			}
			// transactions summary
			else if (key == 5) {
				System.out.print(vs.transactionsSummary());

			}

			System.out.print("\nTransaction: ");
			keyValuesc = new Scanner(System.in);
			key = keyValuesc.nextInt();

		}

		keyValuesc.close();
		filmInput.close();

	}
}
