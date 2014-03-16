package spreadsheet;

/* Import Statementa */
import java.util.ArrayDeque;

/**
 * 
 * @author n_mirot
 * This class implements the Undo and Redo functionality for the 
 *  Spreadsheet application
 */

public class UndoRedo {
	private int stackSize = 25;
	private ArrayDeque<Cell> undoStack = new ArrayDeque<Cell>(stackSize);
	private ArrayDeque<Cell> redoStack = new ArrayDeque<Cell>(stackSize);
	
	//Notes: 
	// Need to implement a copy constructor in Cell class
	
}
