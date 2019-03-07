// Trent Julich ~ 4 March 2019

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
 * @version 4 March 2019
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
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.BLACK);

		if (currentMaze != null) {
			
			currentMaze.render(g);
		}

	}

}
