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
     * Called to create a new maze of the specified size, as well as saving all
     * previous states of the maze.
     * 
     * @param size The size of the maze.
     */
    public void generateMaze(final int size) {
	currentMaze = new Maze(size);
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
