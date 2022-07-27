package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 * @author Sylvia Nguyen
 *
 */

public class VideoStore {// might need to check to initialization
	protected SplayTree<Video> inventory = new SplayTree<Video>(); // all the videos at the store

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor sets inventory to an empty tree.
	 */
	public VideoStore() {
		// no need to implement.
	}

	/**
	 * Constructor accepts a video file to create its inventory. Refer to Section
	 * 3.2 of the project description for details regarding the format of a video
	 * file.
	 * 
	 * Calls setUpInventory().
	 * 
	 * @param videoFile no format checking on the file
	 * @throws FileNotFoundException
	 */
	public VideoStore(String videoFile) throws FileNotFoundException {
		setUpInventory(videoFile);
		// TODO
	}

	/**
	 * Accepts a video file to initialize the splay tree inventory. To be efficient,
	 * add videos to the inventory by calling the addBST() method, which does not
	 * splay.
	 * 
	 * Refer to Section 3.2 for the format of video file.
	 * 
	 * @param videoFile correctly formated if exists
	 * @throws FileNotFoundException
	 */
	public void setUpInventory(String videoFile) throws FileNotFoundException {
		File file = new File(videoFile);
		Scanner scanFile = new Scanner(file);

		while (scanFile.hasNextLine()) {

			String singleLine = scanFile.nextLine();

			Scanner scan = new Scanner(singleLine);
			String movieTitle = "";
			int amount = 1;
			while (scan.hasNext()) {

				String temp = scan.next();
				if (temp.charAt(0) != '(') {
					movieTitle += temp + " ";
				} else if (temp.charAt(0) == '(') {
					int i = 1;
					String num = "";
					while (i < temp.length() - 1) {
						num += temp.charAt(i);
						i++;
					}

					amount = Integer.parseInt(num);
				}

			}
			if (amount > 0) {
				Video v = new Video(movieTitle.trim(), amount);
				inventory.addBST(v);

			}

			scan.close();

		}

		scanFile.close();
		// TODO
	}

	// ------------------
	// Inventory Addition
	// ------------------

	/**
	 * Find a Video object by film title.
	 * 
	 * @param film
	 * @return
	 */
	public Video findVideo(String film) {

		Video v = new Video(film);
		if (inventory.contains(v) == false) {
			return null;
		}

		// TODO
		return inventory.findElement(v);
	}

	/**
	 * Updates the splay tree inventory by adding a number of video copies of the
	 * film. (Splaying is justified as new videos are more likely to be rented.)
	 * 
	 * Calls the add() method of SplayTree to add the video object.
	 * 
	 * a) If true is returned, the film was not on the inventory before, and has
	 * been added. b) If false is returned, the film is already on the inventory.
	 * 
	 * The root of the splay tree must store the corresponding Video object for the
	 * film. Update the number of copies for the film.
	 * 
	 * @param film title of the film
	 * @param n    number of video copies
	 */
	public void addVideo(String film, int n) {

		Video v = new Video(film, n);

		if (inventory.add(v) == false) {

			inventory.getRoot().addNumCopies(n);

		}

		// TODO
	}

	/**
	 * Add one video copy of the film.
	 * 
	 * @param film title of the film
	 */
	public void addVideo(String film) {
		addVideo(film, 1);

		// TODO
	}

	/**
	 * Update the splay trees inventory by adding videos. Perform binary search
	 * additions by calling addBST() without splaying.
	 * 
	 * The videoFile format is given in Section 3.2 of the project description.
	 * 
	 * @param videoFile correctly formated if exists
	 * @throws FileNotFoundException
	 */
	public void bulkImport(String videoFile) throws FileNotFoundException {
		File file = new File(videoFile);
		Scanner scanFile = new Scanner(file);

		while (scanFile.hasNextLine()) {

			String singleLine = scanFile.nextLine();

			Scanner scan = new Scanner(singleLine);
			String movieTitle = "";
			int amount = 1;
			while (scan.hasNext()) {

				String temp = scan.next();
				if (temp.charAt(0) != '(') {
					movieTitle += temp + " ";
				} else if (temp.charAt(0) == '(') {
					int i = 1;
					String num = "";
					while (i < temp.length() - 1) {
						num += temp.charAt(i);
						i++;
					}

					amount = Integer.parseInt(num);
				}

			}
			if (amount > 0) {
				Video v = new Video(movieTitle.trim(), amount);
				inventory.addBST(v);

			}
			scan.close();
		}

		scanFile.close();

		// TODO
	}

	// ----------------------------
	// Video Query, Rental & Return
	// ----------------------------

	/**
	 * Search the splay tree inventory to determine if a video is available.
	 * 
	 * @param film
	 * @return true if available
	 */
	public boolean available(String film) {
		// TODO

		if (inventory.contains(new Video(film, 1)) == true && inventory.getRoot().getNumAvailableCopies() > 0) {

			return true;

		}

		return false;
	}

	/**
	 * Update inventory.
	 * 
	 * Search if the film is in inventory by calling findElement(new Video(film,
	 * 1)).
	 * 
	 * If the film is not in inventory, prints the message "Film <film> is not in
	 * inventory", where <film> shall be replaced with the string that is the value
	 * of the parameter film. If the film is in inventory with no copy left, prints
	 * the message "Film <film> has been rented out".
	 * 
	 * If there is at least one available copy but n is greater than the number of
	 * such copies, rent all available copies. In this case, no
	 * AllCopiesRentedOutException is thrown.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException    if n <= 0 or film == null or
	 *                                     film.isEmpty()
	 * @throws FilmNotInInventoryException if film is not in the inventory
	 * @throws AllCopiesRentedOutException if there is zero available copy for the
	 *                                     film.
	 */
	public void videoRent(String film, int n)
			throws IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {

		if (n <= 0 || film == null || film.isEmpty()) {
			throw new IllegalArgumentException("Film " + film + " has an invalid request");
		}

		Video a = inventory.findElement(new Video(film, 1));
		if (a == null) {
			throw new FilmNotInInventoryException("Film " + film + " is not in inventory");
		}
		// AllCopiesRentedOutException will be thrown
		a.rentCopies(n);

		// TODO
	}

	/**
	 * Update inventory.
	 * 
	 * 1. Calls videoRent() repeatedly for every video listed in the file. 2. For
	 * each requested video, do the following: a) If it is not in inventory or is
	 * rented out, an exception will be thrown from videoRent(). Based on the
	 * exception, prints out the following message: "Film <film> is not in
	 * inventory" or "Film <film> has been rented out." In the message, <film> shall
	 * be replaced with the name of the video. b) Otherwise, update the video record
	 * in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please
	 * read Section 3.4 of the project description.
	 * 
	 * @param videoFile correctly formatted if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of copies of any film is <=
	 *                                     0
	 * @throws FilmNotInInventoryException if any film from the videoFile is not in
	 *                                     the inventory
	 * @throws AllCopiesRentedOutException if there is zero available copy for some
	 *                                     film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException,
			FilmNotInInventoryException, AllCopiesRentedOutException {
		File file = new File(videoFile);
		Scanner scanFile = new Scanner(file);
		// variable a of string will be the value that holds the concatenate string of
		// errors
		String a = "";
		int[] t = new int[3];
		while (scanFile.hasNextLine()) {

			String singleLine = scanFile.nextLine();

			Scanner scan = new Scanner(singleLine);
			String movieTitle = "";
			int amount = 1;
			while (scan.hasNext()) {

				String temp = scan.next();
				if (temp.charAt(0) != '(') {
					movieTitle += temp + " ";
				} else if (temp.charAt(0) == '(') {
					int i = 1;
					String num = "";
					while (i < temp.length() - 1) {
						num += temp.charAt(i);
						i++;
					}

					amount = Integer.parseInt(num);
				}

			}

			try {
				videoRent(movieTitle.trim(), amount);
			} catch (IllegalArgumentException e) {
				a += "Film " + movieTitle.trim() + " has an invalid request" + "\n";
				t[0] = 1;
			} catch (FilmNotInInventoryException e) {
				a += "Film " + movieTitle.trim() + " is not in inventory" + "\n";
				t[1] = 1;
			} catch (AllCopiesRentedOutException e) {
				a += "Film " + movieTitle.trim() + " has been rented out" + "\n";
				t[2] = 1;
			}

			scan.close();

		}
		scanFile.close();
		if (t[0] == 1) {

			throw new IllegalArgumentException(a);

		} else if (t[1] == 1) {
			throw new FilmNotInInventoryException(a);

		} else if (t[2] == 1) {

			throw new AllCopiesRentedOutException(a);

		}

		// TODO
	}

	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of
	 * rented copies while ignoring the extra copies.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException    if n <= 0 or film == null or
	 *                                     film.isEmpty()
	 * @throws FilmNotInInventoryException if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException {
		if (n <= 0 || film == null || film.isEmpty()) {
			throw new IllegalArgumentException("Film " + film + " has an invalid request");
		}

		Video v = inventory.findElement(new Video(film, 1));
		if (v == null) {
			throw new FilmNotInInventoryException("Film " + film + " is not in inventory");
		}

		v.returnCopies(n);

		// TODO
	}

	/**
	 * Update inventory.
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn()
	 * does. See Section 3.4 of the project description on how to handle multiple
	 * exceptions.
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any
	 *                                     film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in
	 *                                     inventory
	 */
	public void bulkReturn(String videoFile)
			throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException {

		File file = new File(videoFile);
		Scanner scanFile = new Scanner(file);
		// variable a of string will be the value that holds the concatenate string of
		// errors
		String a = "";
		int[] t = new int[2];
		while (scanFile.hasNextLine()) {

			String singleLine = scanFile.nextLine();

			Scanner scan = new Scanner(singleLine);
			String movieTitle = "";
			int amount = 1;
			while (scan.hasNext()) {

				String temp = scan.next();
				if (temp.charAt(0) != '(') {
					movieTitle += temp + " ";
				} else if (temp.charAt(0) == '(') {
					int i = 1;
					String num = "";
					while (i < temp.length() - 1) {
						num += temp.charAt(i);
						i++;
					}

					amount = Integer.parseInt(num);
				}

			}
			try {
				videoReturn(movieTitle.trim(), amount);
			} catch (IllegalArgumentException e) {
				a += "Film " + movieTitle.trim() + " has an invalid request" + "\n";
				t[0] = 1;
			} catch (FilmNotInInventoryException e) {
				a += "Film " + movieTitle.trim() + " is not in inventory" + "\n";
				t[1] = 1;
			}

			scan.close();

		}
		scanFile.close();
		if (t[0] == 1) {

			throw new IllegalArgumentException(a);

		} else if (t[1] == 1) {

			throw new FilmNotInInventoryException(a);

		}

		// TODO
	}

	// ------------------------
	// Methods without Splaying
	// ------------------------

	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos
	 * by film title, whether rented or not. Below is a sample string if printed
	 * out:
	 * 
	 * 
	 * Films in inventory:
	 * 
	 * A Streetcar Named Desire (1) Brokeback Mountain (1) Forrest Gump (1) Psycho
	 * (1) Singin' in the Rain (2) Slumdog Millionaire (5) Taxi Driver (1) The
	 * Godfather (1)
	 * 
	 * 
	 * @return
	 */
	public String inventoryList() {
		Iterator<Video> iter = inventory.iterator();
		String ret = "";
		while (iter.hasNext()) {

			Video v = iter.next();
			ret += v.getFilm() + " (" + v.getNumCopies() + ")";
			ret += "\n";

		}

		// TODO
		return ret;
	}

	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially. For the
	 * string format, see Transaction 5 in the sample simulation in Section 4 of the
	 * project description.
	 * 
	 * @return
	 */
	public String transactionsSummary() {

		// TODO

		String rent = "Rented films: " + "\n\n" + rentedVideosList();

		String remaining = "Films remaining in inventory: " + "\n\n" + unrentedVideosList();
		return rent + "\n" + remaining;
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films:
	 * 
	 * Brokeback Mountain (1) Forrest Gump (1) Singin' in the Rain (2) The Godfather
	 * (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList() {
		Iterator<Video> iter = inventory.iterator();
		String ret = "";
		while (iter.hasNext()) {
			Video v = iter.next();
			if (v.getNumRentedCopies() > 0) {
				ret += v.getFilm() + " (" + v.getNumRentedCopies() + ")";
				ret += "\n";
			}

		}

		// TODO
		return ret;
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator. Prints only the films that have unrented copies.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) Forrest Gump (1) Psycho (1) Slumdog Millionaire
	 * (4) Taxi Driver (1)
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList() {
		// TODO
		Iterator<Video> iter = inventory.iterator();
		String ret = "";

		while (iter.hasNext()) {
			Video v = iter.next();
			if (v.getNumAvailableCopies() > 0) {
				ret += v.getFilm() + " (" + v.getNumAvailableCopies() + ")";
				ret += "\n";

			}

		}

		return ret;
	}

	/**
	 * Parse the film name from an input line.
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) {
		// TODO
		Scanner scan = new Scanner(line);
		String ret = "";
		while (scan.hasNext()) {
			String holder = scan.next();

			if (holder.charAt(0) != '(') {
				ret += holder + " ";
			}

		}
		scan.close();
		return ret.trim();
	}

	/**
	 * Parse the number of copies from an input line.
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) {
		// TODO
		Scanner scan = new Scanner(line);
		int ret = 1;
		while (scan.hasNext()) {
			String holder = scan.next();

			if (holder.charAt(0) == '(') {
				int i = 1;
				String num = "";
				while (i < holder.length() - 1) {
					num += holder.charAt(i);
					i++;
				}

				ret = Integer.parseInt(num);
			}

		}
		scan.close();
		return ret;
	}
}
