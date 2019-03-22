package model;

/**
 * Class used to represent a position within a maze. Keeps track of the x and y
 * coordinate, corresponding to rows and columns in a maze.
 * 
 * @author Trent Julich
 * @version 9 March 2019
 */
public class Position {

	//////////////////
	///// Fields /////
	//////////////////

	/** X coordinate of position in maze. **/
	private int xCoordinate;

	/** Y coordinate of position in maze. **/
	private int yCoordinate;

	////////////////////////
	///// Constructors /////
	////////////////////////

	/**
	 * Constructor for new position. Takes x and y coordinates representing current
	 * position in a 2D plane.
	 * 
	 * @param x The x component of position.
	 * @param y The y component of position.
	 */
	public Position(final int x, final int y) {
		this.xCoordinate = x;
		this.yCoordinate = y;
	}

	//////////////////////////
	///// Public Methods /////
	//////////////////////////

	/**
	 * Method to get the x coordinate of this position.
	 * 
	 * @return The x coordinate of position.
	 */
	public int getXPosition() {
		return this.xCoordinate;
	}

	/**
	 * Method to get the y coordinate of this position.
	 * 
	 * @return The y coordinate of position.
	 */
	public int getYPosition() {
		return this.yCoordinate;
	}

	public String toString() {
		return "(" + xCoordinate + ", " + yCoordinate + ")";
	}

}