// Trent Julich ~ 4 March 2019

package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

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

	/** Button used to create and render a new maze. **/
	private JPanel toolPane;

	////////////////////////
	///// Constructors /////
	////////////////////////

	/**
	 * Default constructor for new GUI.
	 */
	public GUI() {
		this.mazePane = new RenderPane();
		this.toolPane = new JPanel();
	}

	//////////////////////////
	///// Public Methods /////
	//////////////////////////

	/**
	 * Method called to initialize and view GUI.
	 */
	public void start() {
		initializeFrame();
		setupRenderPane();
		setupToolPane();
		addComponents();
		this.pack();
		this.setPreferredSize(new Dimension(this.getWidth() + 50, this.getHeight() + 50));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
		this.setLayout(new GridBagLayout());

		final Container c = this.getContentPane();
		c.setBackground(Color.DARK_GRAY);

		this.setTitle(TITLE);
		this.setResizable(false);
	}

	/**
	 * Used to setup the panel on which the mazes will be rendered. Includes setting
	 * size, etc.
	 */
	private void setupRenderPane() {
		mazePane.setPreferredSize(new Dimension(this.getHeight() - 200, this.getHeight() - 200));
		mazePane.setSize(new Dimension(this.getHeight() - 200, this.getHeight() - 200));
		generator = new MazeGenerator(mazePane.getSize());
	}

	/**
	 * Method used to setup the tool panel that contains controls over the maze
	 * generator.
	 */
	private void setupToolPane() {
		toolPane.setSize(new Dimension(200, this.getHeight() - 200));
		toolPane.setPreferredSize(new Dimension(200, this.getHeight() - 200));
		toolPane.setLayout(new GridBagLayout());

		final JSpinner sizes = new JSpinner();
		SpinnerModel sizeModel = new SpinnerNumberModel(50, 2, 100, 1);
		sizes.setModel(sizeModel);

		final JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				generator.generateMaze((Integer) sizes.getModel().getValue());
				mazePane.renderMaze(generator.getMaze());
			}
		});
		toolPane.add(runButton);
		toolPane.add(sizes);
	}

	/**
	 * Used to add all components to the main GUI.
	 */
	private void addComponents() {
		this.add(mazePane);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 50, 0, 0);
		this.add(toolPane, c);
	}

}
