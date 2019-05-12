package model;

import java.awt.Dimension;

/**
 * Maze generator class, containing methods used to create mazes.
 * 
 * @author Trenton Julich
 * @version 9 March 2019
 */
public class MazeGenerator {

	//////////////////
	///// Fields /////
	//////////////////

	/** 2D array representing the Maze. Contains MazeCells. **/
	private Maze currentMaze;

	/** Size of the render panel that generated mazes will be drawn to. **/
	private Dimension renderPanelSize;

	///////////////////////
	///// Constructors /////
	///////////////////////

	/**
	 * Constructor for new Maze Generator.
	 */
	public MazeGenerator(final Dimension renderPanelSize) {
		this.renderPanelSize = renderPanelSize;
	}

	///////////////////////////
	///// Public Methods /////
	///////////////////////////

	/**
	 * Called to create a new maze of the specified size, as well as saving all
	 * previous states of the maze.
	 * 
	 * @param size The size of the maze.
	 */
	public void generateMaze(final int size) {
		currentMaze = new Maze(size);
		currentMaze.setRenderPanelSize(renderPanelSize);
		currentMaze.generate();
	}

	/**
	 * Method used to return the last maze generated.
	 * 
	 * @return The last Maze generated.
	 */
	public Maze getMaze() {
		return this.currentMaze;
	}

}
