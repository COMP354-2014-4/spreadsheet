package spreadsheet;

/* Import Statements */
import java.util.ArrayDeque;

/**
 * 
 * @author Norman Mirotchnick
 * This class implements the Undo and Redo functionality for the 
 *  Spreadsheet application
 */

public class UndoRedo {
	private int stackSize = 25;
	private ArrayDeque<Cell> undoStack = new ArrayDeque<Cell>(stackSize);
	private ArrayDeque<Cell> redoStack = new ArrayDeque<Cell>(stackSize);
	private boolean undoStackIsEmpty = true;
	private boolean redoStackIsEmpty = true;
	
	//** Notes: **// 
	// -Need to implement a copy constructor in Cell class //
	
	/**
	 * Method that takes care of undo request
	 * @param cell
	 */
	public Cell undoAction(Cell cell) {
	  Cell cellReturn;
	  
	  //take passed cell and add COPY to redo stack
	  redoStack.addFirst(cell);
	  
	  //pop from undo stack and return COPY of the cell
	  cellReturn = undoStack.removeFirst();
	  
	  return cellReturn;
	}
	
	/**
	 * Method that takes care of redo request
	 * @param cell
	 */
	public Cell redoAction(Cell cell) {
	  Cell cellReturn;
	  
	  //take passed cell and add COPY to undo stack
	  undoStack.addFirst(cell);
	  
	  //pop from redo stack and return COPY of the cell
	  cellReturn = redoStack.removeFirst();
	  
	  return cellReturn;  
	}
	
  /**
   * Method that takes care of request that is neither undo or redo
   *   -pushes to undo stack
   *   -flushed redo stack
   * @param cell
   */
  public void noUndoRedoAction(Cell cell) {
    //if redo is not empty, flush it
    if (!redoStackIsEmpty) {
      redoFlush();
    }
    
    //add passed cell to undo stack
      //make deep copy of cell before adding to stack 
    
  }
  
  
  
  /**
   * Method to reset redo stack and fill it stack with null values
   */
  private void redoFlush() {
    redoStack.clear();
  }
	
}
