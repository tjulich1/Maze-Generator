package view;

/**
 * Driver for Maze Generator program, used to start the program.
 * 
 * @author Trent Julich
 * @version 9 March 2019
 */
public class Driver {

	/**
	 * Main method for Maze Generator program.
	 * 
	 * @param args Command line arguments, ignored for this program.
	 */
	public static void main(String[] args) {
		final GUI gui = new GUI();
		gui.start();
	}

}
