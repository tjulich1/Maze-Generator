// Trent Julich ~ 4 March 2019

package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
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

	/** Title of application, displayed at top of window. **/
	private static final String TITLE = "Maze Generator";

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
	}

	//////////////////////////
	///// Public Methods /////
	//////////////////////////

	/**
	 * Method called to initialize and view GUI.
	 */
	public void start() {
		initializeFrame();
		mazePane.setPreferredSize(new Dimension(this.getHeight() - 200, this.getHeight() - 200));
		this.add(this.mazePane);
		this.setVisible(true);
		generator = new MazeGenerator(mazePane.getSize());

		// Test Maze

		generator.generateMaze(100);
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
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(screenSize.width - 50, screenSize.height - 50);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());

		// Set Background color of main frame.
		final Container c = this.getContentPane();
		c.setBackground(Color.DARK_GRAY);

		this.setTitle(TITLE);
		this.setResizable(false);
	}

}
