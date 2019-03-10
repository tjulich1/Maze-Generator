package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class representing a Maze. Keeps track of its current state, size, as well as
 * all previous states of the maze (used for rendering).
 * 
 * @author Trent Julich
 * @version 9 March 2019
 */
public final class Maze {

	//////////////////
	///// Fields /////
	//////////////////

	/** The current state of the maze. **/
	private MazeCell[][] currentState;

	/** The number of rows and columns in the maze. **/
	private int size;

	/** The size of the render pane that this maze will be drawn to. **/
	private Dimension renderPanelSize;

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
		this.renderPanelSize = new Dimension(0, 0);
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
		createEntrance();
		createExit();
	}

	/** Method used to get size of maze. **/
	public int getSize() {
		return this.size;
	}

	/**
	 * Method used to assign the size of the panel that this maze will be rendered
	 * to. Should be done before render method is called. Ensures proper scaling of
	 * the maze in proportion to the size of the window.
	 * 
	 * @param renderPanelSize Dimension of the panel on which the maze is being
	 *                        rendered.
	 */
	public void setRenderPanelSize(final Dimension renderPanelSize) {
		this.renderPanelSize = renderPanelSize;
	}

	/**
	 * Method called on a maze to draw it using the given graphics object.
	 * setRenderPanelSize method should be invoked prior to calling this method to
	 * ensure proper scaling of the maze relative to the size of the viewing window.
	 * 
	 * @param g The graphics object to use in rendering.
	 */
	public void render(final Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);

		// Change scale and starting position so that entire maze can fit on maze pane.
		int scale = (renderPanelSize.height - 10) / this.size;
		int xPos = (renderPanelSize.width - (scale * this.size)) / 2;
		int yPos = (renderPanelSize.height - (scale * this.size)) / 2;

		// Check and draw each maze cell wall (if it exists).
		for (int j = 0; j < size; j++) {
			xPos = (renderPanelSize.width - (scale * this.size)) / 2;
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

		// Deque used as a stack to keep track of path through maze.
		final Deque<Position> path = new ArrayDeque<>();

		// Pick random starting positions in the maze.
		int currentX = (int) (Math.random() * size);
		int currentY = (int) (Math.random() * size);

		// Starting position.
		Position currentPosition = new Position(currentX, currentY);

		// Push initial position onto stack.
		path.push(currentPosition);

		while (!path.isEmpty()) {
			// If there are no moves from current position, go back to last position.
			if (!checkMoves(currentPosition)) {
				currentPosition = path.pop();
			} // Else move to new random location adjacent to current location.
			else {
				currentPosition = makeMove(currentPosition);
				path.push(currentPosition);
			}
		}
	}

	/**
	 * Checks maze cells surrounding current cells and checks for open moves.
	 * 
	 * @param position The current position from which a move is wanting to be made.
	 * @return True if there is an open move adjacent to current position.
	 */
	private boolean checkMoves(final Position position) {
		final int x = position.getXPosition();
		final int y = position.getYPosition();

		// Check all 4 directions. Returns true if at least one position is open.
		return (y < size - 1 && !currentState[x][y + 1].getIsVisited())
				|| (y > 0 && !currentState[x][y - 1].getIsVisited())
				|| (x < size - 1 && !currentState[x + 1][y].getIsVisited())
				|| (x > 0 && !currentState[x - 1][y].getIsVisited());
	}

	/**
	 * Called to return a random position adjacent to the current position which has
	 * not been visited yet. Method assumes there is at least one valid move able to
	 * be made.
	 * 
	 * @param x The current x position.
	 * @param y The current y position.
	 * @return New valid position that has not been visited yet.
	 */
	private Position makeMove(final Position position) {
		boolean moveMade = false;
		final int x = position.getXPosition();
		final int y = position.getYPosition();
		Position newPosition = position;

		// Choose a random direction to attempt to travel in.
		int testDirection = (int) (Math.random() * 4);

		/*
		 * While a move has not been made, choose a random direction to attempt to move
		 * in. First check if that direction is valid, then check if the adjacent cell
		 * in that direction has NOT been visited. If not, move into it. Otherwise, try
		 * another random direction.
		 */
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

	/**
	 * Used to create a random entrance at the top of the maze.
	 */
	private void createEntrance() {
		final int entrance = (int) (Math.random() * size);
		currentState[entrance][0].setNorthWall(false);
	}

	/**
	 * Used to create a random exit at the bottom of the maze.
	 */
	private void createExit() {
		final int exit = (int) (Math.random() * size);
		currentState[exit][size - 1].setSouthWall(false);
	}

}
