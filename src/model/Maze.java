// Trent Julich ~ 3 March 2019

package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class representing a Maze. Keeps track of its current state, size, as well as
 * all previous states of the maze (used for rendering).
 */
public final class Maze {

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

    /** Method used to get size of maze. **/
    public int getSize() {
	return this.size;
    }

    /**
     * Method called on a maze to draw it using the given graphics object.
     * 
     * @param g The graphics object to use in rendering.
     */
    public void render(final Graphics g) {
	g.setColor(Color.BLACK);

	int xPos = 1;
	int yPos = 1;
	int scale = 50;

	for (int j = 0; j < size; j++) {
	    xPos = 1;
	    for (int i = 0; i < size; i++) {
		if (currentState[i][j].getNorthWall()) {
		    g.drawLine(xPos, yPos, xPos + scale, yPos);
		}
		if (currentState[i][j].getWestWall()) {
		    g.drawLine(xPos, yPos, xPos, yPos + scale);
		}
		if (currentState[i][j].getSouthWall()) {
		    g.drawLine(xPos, yPos + scale, xPos + scale, yPos + scale);
		}
		if (currentState[i][j].getEastWall()) {
		    g.drawLine(xPos + scale, yPos, xPos + scale, yPos + scale);
		}
		xPos += scale;
	    }
	    yPos += scale;
	}
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
		    currentState[i][j].setWestCell(currentState[i - 1][j]);
		}
		// If west neighbor exists, link it to current cell.
		if (i < size - 1) {
		    currentState[i][j].setEastCell(currentState[i + 1][j]);
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

	int currentX = (int) (Math.random() * size);

	int currentY = (int) (Math.random() * size);

	path.push(new Position(currentX, currentY));

	while (!path.isEmpty()) {
	    if (!checkMoves(currentX, currentY)) {
		final Position previousPosition = path.pop();
		currentX = previousPosition.getXPosition();
		currentY = previousPosition.getYPosition();
	    } else {
		final Position newPosition = makeMove(currentX, currentY);
		currentX = newPosition.getXPosition();
		currentY = newPosition.getYPosition();
		path.push(newPosition);
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
		    moveNorth(x, y);
		    newPosition = new Position(x, y - 1);
		    moveMade = true;
		}
	    } else if (testDirection == 1) {
		if (x < size - 1 && !currentState[x + 1][y].getIsVisited()) {
		    newPosition = new Position(x + 1, y);
		    moveEast(x, y);
		    moveMade = true;
		}
	    } else if (testDirection == 2) {
		if (y < size - 1 && !currentState[x][y + 1].getIsVisited()) {
		    newPosition = new Position(x, y + 1);
		    moveSouth(x, y);
		    moveMade = true;
		}
	    } else if (testDirection == 3) {
		if (x > 0 && !currentState[x - 1][y].getIsVisited()) {
		    newPosition = new Position(x - 1, y);
		    moveWest(x, y);
		    moveMade = true;
		}
	    }
	    testDirection = (int) (Math.random() * 4);
	}
	return newPosition;
    }

    /**
     * Helper method called to simulate a move to the north, changing the current
     * maze state as necessary.
     * 
     * @param x X coordinate before move.
     * @param y Y coordinate before move.
     */
    private void moveNorth(final int x, final int y) {
	currentState[x][y - 1].setVisited(true);
	currentState[x][y - 1].setSouthWall(false);
	currentState[x][y].setNorthWall(false);
    }

    /**
     * Helper method called to simulate a move to the east, changing the current
     * maze state as necessary.
     * 
     * @param x X coordinate before move.
     * @param y Y coordinate before move.
     */
    private void moveEast(final int x, final int y) {
	currentState[x + 1][y].setVisited(true);
	currentState[x + 1][y].setWestWall(false);
	currentState[x][y].setEastWall(false);
    }

    /**
     * Helper method called to simulate a move to the south, changing the current
     * maze state as necessary.
     * 
     * @param x X coordinate before move.
     * @param y Y coordinate before move.
     */
    private void moveSouth(final int x, final int y) {
	currentState[x][y + 1].setVisited(true);
	currentState[x][y + 1].setNorthWall(false);
	currentState[x][y].setSouthWall(false);
    }

    /**
     * Helper method called to simulate a move to the west, changing the current
     * maze state as necessary.
     * 
     * @param x X coordinate before move.
     * @param y Y coordinate before move.
     */
    private void moveWest(final int x, final int y) {
	currentState[x - 1][y].setVisited(true);
	currentState[x - 1][y].setEastWall(false);
	currentState[x][y].setWestWall(false);
    }

}
