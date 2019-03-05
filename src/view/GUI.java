// Trent Julich ~ 4 March 2019

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

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

	////////////////////////
	///// Constructors /////
	////////////////////////

	/**
	 * Default constructor for new GUI.
	 */
	public GUI() {
		this.mazePane = new RenderPane();
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
		mazePane.setBackground(Color.BLACK);
		this.add(this.mazePane);
		this.setVisible(true);
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
	}

}
