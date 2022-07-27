package edu.iastate.cs228.hw1;

/**
 * A rabbit eats grass and lives no more than three years.
 * 
 * @author Sylvia Nguyen
 *
 */

public class Rabbit extends Animal {
	/**
	 * Creates a Rabbit object.
	 *
	 * @param p: plain
	 * @param r: row position
	 * @param c: column position
	 * @param a: age
	 */
	public Rabbit(Plain p, int r, int c, int a) {
		// TODO
		super(p, r, c, a);

	}

// Rabbit occupies the square.
	public State who() {
// TODO
		return State.RABBIT;
	}

	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a
	 * fox.
	 *
	 * @param pNew plain of the next cycle
	 * @return Living new life form occupying the same square
	 */
	public Living next(Plain pNew) {
		// TODO
		//
		// See Living.java for an outline of the function.
		// See the project description for the survival rules for a rabbit.
		int[] population = new int[NUM_LIFE_FORMS];
		this.census(population);

		if (age == RABBIT_MAX_AGE) {
			pNew.grid[row][column] = new Empty(pNew, this.row, this.column);
		} else if (population[GRASS] == 0) {
			pNew.grid[row][column] = new Empty(pNew, this.row, this.column);
		} else if (population[BADGER] + population[FOX] >= population[RABBIT] && population[FOX] > population[BADGER]) {
			pNew.grid[row][column] = new Fox(pNew, this.row, this.column, 0);
		} else if (population[BADGER] > population[RABBIT]) {
			pNew.grid[row][column] = new Badger(pNew, this.row, this.column, 0);
		} else {
			pNew.grid[row][column] = new Rabbit(pNew, this.row, this.column, age + 1);
		}
		return pNew.grid[row][column];

	}
}
