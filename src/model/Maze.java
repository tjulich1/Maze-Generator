// Trent Julich ~ 3 March 2019

package model;

public final class Maze {

	private MazeCell[][] currentState;

	private int size;

	public Maze(final int size) {
		this.size = size;
		currentState = new MazeCell[size][size];
		fillWithNewCells();
		linkCells();
	}

	/**
	 * Method used to initialize the maze with new cells.
	 */
	private void fillWithNewCells() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// Fill current maze state with empty cells.
				currentState[i][j] = new MazeCell();
			}
		}
	}

	/**
	 * Method used to connect each cell with its neighbors.
	 */
	private void linkCells() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// If east neighbor exists, link it to current cell.
				if (i > 0) {
					currentState[i][j].setEastCell(currentState[i - 1][j]);
				}
				// If west neighbor exists, link it to current cell.
				if (i < size - 1) {
					currentState[i][j].setWestCell(currentState[i + 1][j]);
				}
				// If north neighbor exists, link it to current cell.
				if (j > 0) {
					currentState[i][j].setNorthCell(currentState[i][j - 1]);
				}
				// If south neighbor exists, link it to current cell.
				if (j < size - 1) {
					currentState[i][j].setSouthCell(currentState[i][j + 1]);
				}
			}
		}
	}

}
