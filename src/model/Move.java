package model;

/**
 * Move class created to represent moves made when generating a maze. String
 * label tells which direction the move was made in the maze (north, south,
 * east, west), as well as the position in the maze AFTER the move was made.
 * 
 * @author Trent Julich
 * @version 11 March 2019
 */
public class Move {

	/** New position after this move was made. **/
	private Position position;

	/** Label telling which direction this move was made in. **/
	private String direction;

	/**
	 * Constructor for Move object. Takes in a ending position after the move is
	 * made, as well as a String label representing which direction the move was
	 * made in.
	 * 
	 * @param position  Position after move was made.
	 * @param direction Direction this move was made in.
	 */
	public Move(final Position position, final String direction) {
		this.position = position;
		this.direction = direction;
	}

	/**
	 * Used to retrieve the position after the move was made.
	 * 
	 * @return Position after move was made.
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Used to retrieve the direction label of this move.
	 * 
	 * @return String representing direction move was made in.
	 */
	public String getDirection() {
		return this.direction;
	}

	@Override
	public String toString() {
		return "(" + this.position.getXPosition() + ", " + this.position.getYPosition() + "), " + this.direction;
	}

}
