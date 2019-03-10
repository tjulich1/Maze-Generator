package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import model.Maze;

/**
 * Panel that maze will be rendered onto.
 * 
 * @author Trent Julich
 * @version 9 March 2019
 */
public class RenderPane extends JPanel {

	///////////////////
	///// Fields /////
	//////////////////

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -4288898232042098135L;

	/** The maze that is to be rendered. **/
	private Maze currentMaze;

	////////////////////////
	///// Constructors /////
	////////////////////////

	/**
	 * Constructor for Render Pane
	 */
	public RenderPane() {
		super();
	}

	//////////////////////////
	///// Public Methods /////
	//////////////////////////

	/**
	 * Method called to draw the given maze to the screen.
	 * 
	 * @param maze The maze to render.
	 */
	public void renderMaze(final Maze maze) {
		this.currentMaze = maze;
		this.repaint();
	}

	@Override
	public void paintComponent(final Graphics g) {

		// fills background with white.
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		// draws a maze if there is one to draw.
		if (currentMaze != null) {
			currentMaze.render(g);
		}

	}

}
