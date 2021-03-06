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
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.MazeGenerator;

/**
 * Main front end component for Graphical Maze Generator program. Main window
 * that the user interacts with.
 * 
 * @author Trent Julich
 * @version 9 March 2019
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

	/** JPanel that holds size control and run button. **/
	private JPanel toolPane;

	/** Timer used when rendering the generation of the maze. **/
	private Timer timer;

	/** Button used to skip to rendering the final maze. **/
	private final JButton skipButton;

	////////////////////////
	///// Constructors /////
	////////////////////////

	/**
	 * Default constructor for new GUI.
	 */
	public GUI() {
		this.mazePane = new RenderPane();
		this.toolPane = new JPanel();
		this.skipButton = new JButton("Skip");

		// Creates timer which ticks 60 times per second.
		this.timer = new Timer(1000 / 60, new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				// Takes one step through maze generation, then renders the next frame.
				generator.getMaze().tick();
				mazePane.renderMaze(generator.getMaze());
				if (generator.getMaze().isLastFrame()) {
					timer.stop();
					skipButton.setEnabled(false);
				}
			}
		});
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
		// Pack down to smallest size with components, then add 50 pixel padding to
		// height and width.
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
		// Captures the size of the users screen and uses it to set the
		// size of the window.
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width - 50, screenSize.height - 50);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setIconImage(new ImageIcon("./resources/maze.png").getImage());
		this.setTitle(TITLE);
		this.setResizable(false);

		final Container c = this.getContentPane();
		c.setBackground(Color.DARK_GRAY);
	}

	/**
	 * Used to setup the panel on which the mazes will be rendered. Includes setting
	 * size, etc.
	 */
	private void setupRenderPane() {
		final Dimension size = new Dimension(this.getHeight() - 200, this.getHeight() - 200);
		mazePane.setPreferredSize(size);
		mazePane.setSize(size);
		generator = new MazeGenerator(mazePane.getSize());
	}

	/**
	 * Method used to setup the tool panel that contains controls over the maze
	 * generator.
	 */
	private void setupToolPane() {

		// sets size and layout of tool pane.
		final int paneHeight = 500;
		final int paneWidth = 100;
		toolPane.setSize(new Dimension(paneWidth, paneHeight));
		toolPane.setPreferredSize(new Dimension(paneWidth, paneHeight));
		toolPane.setLayout(new GridBagLayout());

		// create spinner for the different sizes of mazes.
		final int max = 100;
		final int min = 10;
		final int step = 1;
		final int start = 50;
		final JSpinner sizes = new JSpinner();
		SpinnerModel sizeModel = new SpinnerNumberModel(start, min, max, step);
		sizes.setModel(sizeModel);

		// create run button used to start rendering maze or skip to final rendering
		// step.
		final JButton runButton = new JButton("Run");

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (generator.getMaze() != null) {
					skipButton.setEnabled(true);
					generator.getMaze().setSkip(false);
					generator.generateMaze((Integer) sizes.getModel().getValue());
					timer.start();
				} else {
					skipButton.setEnabled(true);
					generator.generateMaze((Integer) sizes.getModel().getValue());
					timer.start();
				}
			}
		});

		skipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				skipButton.setEnabled(false);
				timer.stop();
				generator.getMaze().setSkip(true);
				mazePane.renderMaze(generator.getMaze());
			}
		});
		skipButton.setEnabled(false);

		// constraint to make each component have its own row, and add padding between.
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		toolPane.add(skipButton, c);
		c.insets = new Insets(30, 0, 0, 0);
		c.gridy++;
		toolPane.add(runButton, c);
		c.gridy++;
		toolPane.add(sizes, c);
		c.gridy++;
		toolPane.add(createSlider(), c);
	}

	/**
	 * Used to add all components to the main GUI.
	 */
	private void addComponents() {
		this.add(mazePane);

		/*
		 * Constraint used to add a 25 pixel buffer between the maze pane and the tool
		 * panel.
		 */
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 25, 0, 0);

		this.add(toolPane, c);
	}

	/**
	 * Used to create a JSlider used to control the speed that the maze is rendered
	 * at.
	 */
	private JSlider createSlider() {
		final JSlider slider = new JSlider();

		// Make slider up and down.
		slider.setOrientation(JSlider.VERTICAL);

		// Create table with labels for slider.
		final Hashtable<Integer, JLabel> labels = new Hashtable<>();
		labels.put(1, new JLabel("Slow"));
		labels.put(250, new JLabel("Fast"));

		// Set slider values.
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(250);
		slider.setMinorTickSpacing(10);

		// When slider is changed, update timer tick speed.
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				timer.setDelay(1000 / slider.getValue());
			}
		});
		return slider;
	}

}
