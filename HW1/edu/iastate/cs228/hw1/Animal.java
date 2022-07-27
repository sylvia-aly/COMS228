package edu.iastate.cs228.hw1;

/**
 * 
 * This class is to be extended by the Badger, Fox, and Rabbit classes.
 * 
 * @author Sylvia Nguyen
 *
 */

public abstract class Animal extends Living implements MyAge {
	protected int age; // age of the animal

	/**
	 * Constructor
	 * 
	 * @param p: plain
	 * @param r: row position
	 * @param c: column position
	 * @param a: age
	 */

	public Animal(Plain p, int r, int c, int a) {
		plain = p;
		row = r;
		column = c;
		age = a;

	}

	@Override
	/**
	 *
	 * @return age of the animal
	 */
	public int myAge() {
// TODO
		return age;
	}
}
