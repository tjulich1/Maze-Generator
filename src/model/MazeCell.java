// Trent Julich ~ 24 December 2018

package model;

/**
 * Class representing a maze cell. Contains information regarding walls to each
 * cell, as well as neighboring cells.
 * 
 * @author Trent Julich
 * @version 0.0.1
 */
public class MazeCell {

	//////////////////
	///// Fields /////
	//////////////////

	/**
	 * Booleans representing whether a wall will be rendered on specified side of
	 * cell.
	 **/
	private boolean northWall, eastWall, southWall, westWall;

	/** Boolean representing whether this cell has been visited. **/
	private boolean visited;

	/** The neighboring cells to this cell. **/
	private MazeCell northCell, eastCell, southCell, westCell;

	////////////////////////
	///// Constructors /////
	////////////////////////

	/** Constructor for MazeCell object. **/
	public MazeCell() {
		this.visited = false;
		this.northWall = true;
		this.eastWall = true;
		this.southWall = true;
		this.westWall = true;
	}

	//////////////////////////
	///// Public Methods /////
	//////////////////////////

	public void setNorthWall(final boolean northWall) {
		this.northWall = northWall;
	}

	public void setEastWall(final boolean eastWall) {
		this.eastWall = eastWall;
	}

	public void setSouthWall(final boolean southWall) {
		this.southWall = southWall;
	}

	public void setWestWall(final boolean westWall) {
		this.westWall = westWall;
	}

	public void setNorthCell(final MazeCell northCell) {
		this.northCell = northCell;
	}

	public void setEastCell(final MazeCell eastCell) {
		this.eastCell = eastCell;
	}

	public void setSouthCell(final MazeCell southCell) {
		this.southCell = southCell;
	}

	public void setWestCell(final MazeCell westCell) {
		this.westCell = westCell;
	}

	public void setVisited(final boolean visited) {
		this.visited = visited;
	}

	public boolean getNorthWall() {
		return this.northWall;
	}

	public boolean getEastWall() {
		return this.eastWall;
	}

	public boolean getSouthWall() {
		return this.southWall;
	}

	public boolean getWestWall() {
		return this.westWall;
	}

	public MazeCell getNorthCell() {
		return this.northCell;
	}

	public MazeCell getEastCell() {
		return this.eastCell;
	}

	public MazeCell getSouthCell() {
		return this.southCell;
	}

	public MazeCell getWestCell() {
		return this.westCell;
	}

	public boolean getIsVisited() {
		return this.visited;
	}
}
