// Trent Julich ~ 4 March 2019

package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import model.MazeGenerator;

/**
 * Main front end component for Graphical Maze Generator program. Main window
 * that the user interacts with.
 * 
 * @author Trent Julich
 * @version 4 March 2019
 */
public class GUI extends JFrame {

    //////////////////
    ///// Fields /////
    //////////////////

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -7174075501199373132L;

    /** The pane that the maze will be drawn to. **/
    private RenderPane mazePane;

    /** MazeGenerator object used to create and request mazes. **/
    private MazeGenerator generator;

    ////////////////////////
    ///// Constructors /////
    ////////////////////////

    /**
     * Default constructor for new GUI.
     */
    public GUI() {
	this.mazePane = new RenderPane();
	generator = new MazeGenerator();
    }

    //////////////////////////
    ///// Public Methods /////
    //////////////////////////

    /**
     * Method called to initialize and view GUI.
     */
    public void start() {
	initializeFrame();
	mazePane.setPreferredSize(new Dimension(800, 600));
	this.add(this.mazePane);
	this.setVisible(true);

	// Test Maze

	generator.generateMaze(10);
	mazePane.renderMaze(generator.getMaze());

	// Test Maze
    }

    ///////////////////////////
    ///// Private Methods /////
    ///////////////////////////

    /**
     * Helper method called to initialize default values for frame.
     */
    private void initializeFrame() {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(1000, 800);
	this.setLayout(new GridBagLayout());

	// Set Background color of main frame.
	final Container c = this.getContentPane();
	c.setBackground(Color.LIGHT_GRAY);
    }

}
