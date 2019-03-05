// Trent Julich ~ 3 March 2019

package model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class representing a Maze. Keeps track of its current state, size, as well as
 * all previous states of the maze (used for rendering).
 */
public final class Maze {

	private int unvisitedCells;

	//////////////////
	///// Fields /////
	//////////////////

	/** The current state of the maze. **/
	private MazeCell[][] currentState;

	/** The number of rows and columns in the maze. **/
	private int size;

	/** Stack used to keep track of path through maze when generating the maze. **/
	private final Deque<Position> path;

	////////////////////////
	///// Constructors /////
	////////////////////////

	/**
	 * Constructor for an empty maze of dimension "size"x"size".
	 * 
	 * @param size The number of rows and columns in the maze.
	 */
	public Maze(final int size) {
		this.size = size;
		this.currentState = new MazeCell[size][size];
		this.path = new ArrayDeque<Position>();
	}

	//////////////////////////
	///// Public Methods /////
	//////////////////////////

	/**
	 * Method called to start maze generation.
	 */
	public void generate() {
		fillWithNewCells();
		linkCells();
		createMaze();
	}

	///////////////////////////
	///// Private Methods /////
	///////////////////////////

	/**
	 * Helper method used to initialize the maze with new cells.
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
	 * Helper method used to connect each cell with its neighbors.
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

	/**
	 * Helper method which is called from generate method, which creates a finished
	 * maze.
	 */
	private void createMaze() {

		unvisitedCells = size * size;

		int currentX = (int) (Math.random() * size);

		int currentY = (int) (Math.random() * size);

		while (unvisitedCells > 0) {
			if (!checkMoves(currentX, currentY)) {
				final Position previousPosition = path.pop();
				currentX = previousPosition.getXPosition();
				currentY = previousPosition.getYPosition();
			} else {
				final Position newPosition = makeMove(currentX, currentY);
				currentX = newPosition.getXPosition();
				currentY = newPosition.getYPosition();
				path.push(newPosition);
				unvisitedCells--;
			}
		}
	}

	/**
	 * Checks maze cells surrounding current cells and checks for open moves.
	 * 
	 * @param x The x coordinate of current position.
	 * @param y The y coordinate of current position.
	 * @return True if there is an open move adjacent to current position.
	 */
	private boolean checkMoves(final int x, final int y) {
		// Check all 4 directions. Returns true if at least one position is open.
		return (y < size - 1 && !currentState[x][y + 1].getIsVisited())
				|| (y > 0 && !currentState[x][y - 1].getIsVisited())
				|| (x < size - 1 && !currentState[x + 1][y].getIsVisited())
				|| (x > 0 && !currentState[x - 1][y].getIsVisited());
	}

	/**
	 * Called to return a position adjacent to the current position which has not
	 * been visited yet.
	 * 
	 * @param x The current x position.
	 * @param y The current y position.
	 * @return New valid position that has not been visited yet.
	 */
	private Position makeMove(final int x, final int y) {
		boolean moveMade = false;
		Position newPosition = new Position(x, y);
		int testDirection = (int) (Math.random() * 4);

		while (!moveMade) {
			if (testDirection == 0) {
				if (y > 0 && !currentState[x][y - 1].getIsVisited()) {
					newPosition = new Position(x, y - 1);
					currentState[x][y - 1].setVisited(true);
					currentState[x][y - 1].setSouthWall(false);
					currentState[x][y].setNorthWall(false);
					moveMade = true;
				}
			} else if (testDirection == 1) {
				if (x < size - 1 && !currentState[x + 1][y].getIsVisited()) {
					newPosition = new Position(x + 1, y);
					currentState[x + 1][y].setVisited(true);
					currentState[x + 1][y].setWestWall(false);
					currentState[x][y].setEastWall(false);
					moveMade = true;
				}
			} else if (testDirection == 2) {
				if (y < size - 1 && !currentState[x][y + 1].getIsVisited()) {
					newPosition = new Position(x, y + 1);
					currentState[x][y + 1].setVisited(true);
					currentState[x][y + 1].setNorthWall(false);
					currentState[x][y].setSouthWall(false);
					moveMade = true;
				}
			} else if (testDirection == 3) {
				if (x > 0 && !currentState[x - 1][y].getIsVisited()) {
					newPosition = new Position(x - 1, y);
					currentState[x - 1][y].setVisited(true);
					currentState[x - 1][y].setEastWall(false);
					currentState[x][y].setWestWall(false);
					moveMade = true;
				}
			}
			testDirection = (int) (Math.random() * 4);
		}
		return newPosition;
	}

}
