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
	
	/**
	 * Method that takes care of undo request
	 * @return Cell
	 */
	public Cell undoAction() {
	  Cell cellReturn = null;
	  
	  if(!undoStackEmpty()) {
      //pop from undo stack and return COPY of the cell
      //make deep copy of cell
      cellReturn = new Cell(undoStack.removeFirst());
      System.out.println("Pulled from Undo " + undoStack.size());
      //update state of undoStackIsEmpty
      undoStackEmpty();
  
  	  //make copy of cell from undo and add to redo stack
  	  //make deep copy of cell
  	  Cell copyCell = new Cell(cellReturn);
  	  //add COPY to redo stack
  	  redoStack.addFirst(copyCell);
  	  //update state of redoStackIsEmpty
  	  redoStackEmpty();
	  }
	  
	  //return cell
	  return cellReturn;
	}
	
	/**
	 * Method that takes care of redo request
   * @return Cell
	 */
	public Cell redoAction() {
	  Cell cellReturn = null;
    
	  if(!redoStackEmpty()) {
      //pop from redo stack and return COPY of the cell
      //make deep copy of cell
      cellReturn = new Cell(redoStack.removeFirst());
      System.out.println("Pulled from Redo " + redoStack.size());
      //update state of redoStackIsEmpty
      redoStackEmpty();
      
  	  //take cell from redo and add COPY to undo stack
  	  //make deep copy of cell
  	  Cell copyCell = new Cell(cellReturn);
  	  //add COPY to undo stack
  	  undoStack.addFirst(copyCell);
  	  //update state of undoStackIsEmpty
  	  undoStackEmpty();
    }   

	  //return cell
	  return cellReturn;
	}
	
  /**
   * Method that takes care of request that is neither undo or redo
   *   -pushes to undo stack
   *   -flushes redo stack
   * @param cell
   */
  public void noUndoRedoAction(Cell cell) {
    //if redo is not empty, flush it
    if (!redoStackIsEmpty) {
      redoFlush();
    }
    
    //add passed cell to undo stack
    //make deep copy of cell
    Cell copyCell = new Cell(cell);
    //add to undo stack
    undoStack.addFirst(copyCell);
    System.out.println("Setting Undo " + undoStack.size());
  	//update state of undoStackIsEmpty
  	undoStackEmpty();
  }
  
  /**
   * Method to reset redo stack to null values
   */
  private void redoFlush() {
    redoStack.clear();
    redoStackIsEmpty = true;
  }
  
  /**
   * Updates and returns current state of redoStackIsEmpty
   * @return boolean redoStackIsEmpty
   */
  private boolean redoStackEmpty() {
	  if(redoStack.size() > 0) {
		  redoStackIsEmpty = false;
	  } else {
		  redoStackIsEmpty = true;
	  }
	  return redoStackIsEmpty;
  }

  /**
   * Updates and returns current state of undoStackIsEmpty
   * @return boolean redoStackIsEmpty
   */
  private boolean undoStackEmpty() {
	  if(undoStack.size() > 0) {
		  undoStackIsEmpty = false;
	  } else {
		  undoStackIsEmpty = true;
	  }
	  return undoStackIsEmpty;
  }
	
}
