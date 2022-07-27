package edu.iastate.cs228.hw1;

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is
 * eaten.
 * 
 * @author Sylvia Nguyen
 */
public class Grass extends Living {
	public Grass(Plain p, int r, int c) {
		// TODO
		plain = p;
		row = r;
		column = c;

	}

	public State who() {
// TODO  
		return State.GRASS;
	}

	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast
	 * enough to take over Grass.
	 */
	public Living next(Plain pNew) {
		// TODO
		//
		// See Living.java for an outline of the function.
		// See the project description for the survival rules for grass.
		int[] population = new int[NUM_LIFE_FORMS];

		census(population);

		if (population[RABBIT] >= population[GRASS] * 3) {
			pNew.grid[row][column] = new Empty(pNew, this.row, this.column);
		} else if (population[RABBIT] >= 3) {
			pNew.grid[row][column] = new Rabbit(pNew, this.row, this.column, 0);
		} else {
			pNew.grid[row][column] = new Grass(pNew, this.row, this.column);
		}

		return pNew.grid[row][column];
	}
}