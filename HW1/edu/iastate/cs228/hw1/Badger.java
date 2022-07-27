package edu.iastate.cs228.hw1;

/**
 * A badger eats a rabbit and competes against a fox.
 * 
 * @author Sylvia Nguyen
 */
public class Badger extends Animal {
	/**
	 * Constructor
	 * 
	 * @param p: plain
	 * @param r: row position
	 * @param c: column position
	 * @param a: age
	 */
	public Badger(Plain p, int r, int c, int a) {
		// TODO
		super(p, r, c, a);

	}

	/**
	 * A badger occupies the square.
	 */
	public State who() {
// TODO
		return State.BADGER;

	}

	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group
	 * of foxes.
	 * 
	 * @param pNew plain of the next cycle
	 * @return Living life form occupying the square in the next cycle.
	 */
	public Living next(Plain pNew) {
		// TODO
		//
		// See Living.java for an outline of the function.
		// See the project description for the survival rules for a badger.

		int population[] = new int[NUM_LIFE_FORMS];
		this.census(population);

		if (age == BADGER_MAX_AGE) {
			pNew.grid[row][column] = new Empty(pNew, row, column);

		} else if (population[BADGER] == 1 && population[FOX] > 1) {
			pNew.grid[row][column] = new Fox(pNew, row, column, 0);
			;

		} else if ((population[BADGER] + population[FOX]) > population[RABBIT]) {
			pNew.grid[row][column] = new Empty(pNew, row, column);

		} else {
			pNew.grid[row][column] = new Badger(pNew, row, column, age + 1);
		}
		return pNew.grid[row][column];

	}
}
