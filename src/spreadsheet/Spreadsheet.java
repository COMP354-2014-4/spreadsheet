/**
 * COMP 354 (Team 3) Spreadsheet app
 * 
 * @author Justin Dupuis
 */
package spreadsheet;

/**
 * @author Justin Dupuis
 * @version 2014-02-09
 * 
 * The master class of the spreadsheet application.
 * Launches the GUI
 * 
 */
public class Spreadsheet {

	/**
	 * @param args No parameters used.
	 */
	public static void main(String[] args) {
		//TODO: On open, allow the user to choose between "New" and "Open". Either a new grid should be created or one should be generated from the file.
		
		//Create a blank back-end grid
		Grid grid = new Grid(SSTable.intDefaultColumns, SSTable.intDefaultRows);
		
		//Create the GUI and pass it the grid to display
		SSGUI gui = new SSGUI(grid);

		//Display welcome message
		gui.displayMessage("Welcome to the Team 3 spreadsheet app!");
	}
}
