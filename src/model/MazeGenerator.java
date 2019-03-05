// Trent Julich ~ 3 March 2019

package model;

/**
 * Maze class, containing methods used to generate mazes.
 * 
 * @author Trenton Julich
 * @version 2 March 2019
 */
public class MazeGenerator {

	//////////////////
	///// Fields /////
	//////////////////

	/** 2D array representing the Maze. Contains MazeCells. **/
	private Maze currentMaze;

	///////////////////////
	///// Constructors /////
	///////////////////////

	/**
	 * Constructor for new Maze Generator.
	 */
	public MazeGenerator() {

	}

	///////////////////////////
	///// Public Methods /////
	///////////////////////////

	/**
	 * Method used to return the last maze generated.
	 * 
	 * @return The last Maze generated.
	 */
	public Maze getMaze() {
		return this.currentMaze;
	}

}
