package edu.iastate.cs228.hw1;

/**
 * Empty squares are competed by various forms of life.
 * 
 * @author Sylvia Nguyen
 */
public class Empty extends Living {
	// TODO
	public Empty(Plain p, int r, int c) {
		plain = p;
		row = r;
		column = c;

	}

	public State who() {
// TODO
		return State.EMPTY;
	}

	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or
	 * Grass, or remain empty.
	 * 
	 * @param pNew plain of the next life cycle.
	 * @return Living life form in the next cycle.
	 */
	public Living next(Plain pNew) {
		// TODO
		//
		// See Living.java for an outline of the function.
		// See the project description for corresponding survival rules.
		int population[] = new int[NUM_LIFE_FORMS];
		census(population);
		if (population[RABBIT] > 1) {
			pNew.grid[row][column] = new Rabbit(pNew, this.row, this.column, 0);
		} else if (population[FOX] > 1) {
			pNew.grid[row][column] = new Fox(pNew, this.row, this.column, 0);
		} else if (population[BADGER] > 1) {
			pNew.grid[row][column] = new Badger(pNew, this.row, this.column, 0);
		} else if (population[GRASS] > 0) {
			pNew.grid[row][column] = new Grass(pNew, this.row, this.column);
		} else {
			pNew.grid[row][column] = new Empty(pNew, this.row, this.column);
		}
		return pNew.grid[row][column];

	}
}
