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
	 * @param grid 
	 * @return Cell to be restored
	 */
	public Cell undoAction(Grid grid) {
	  Cell cellReturn = new Cell("-1", -1, null);
	  
	  if(!undoStackIsEmpty) {
      //pop from undo stack and return COPY of the cell
      //make deep copy of cell from stack
      cellReturn = new Cell(undoStack.removeFirst());
      
      //update state of undoStackIsEmpty
      undoStackEmpty();
      
      //get col and row from cell popped from undo stack
      String col = cellReturn.getCol();
      int row = cellReturn.getRow();
      
      //get deep copy of the current state of the cell being undone
      Cell cellCurrent = new Cell(grid.getCell(col, row));
  
  	  //make copy of current cell and add to redo stack
  	  //make deep copy of current cell
  	  Cell copyCell = new Cell(cellCurrent);
  	  
  	  //add the copy to redo stack
  	  redoStack.addFirst(copyCell);
  	  
  	  //update state of redoStackIsEmpty
  	  redoStackEmpty();
	  }
	  
	  //return cell
	  return cellReturn;
	}
	
	/**
	 * Method that takes care of redo request
	 * @param grid 
   * @return Cell to be restored
	 */
	public Cell redoAction(Grid grid) {
	  Cell cellReturn = new Cell("-1", -1, null);
    
	  if(!redoStackIsEmpty) {
      //pop from redo stack and return COPY of the cell
      //make deep copy of cell
      cellReturn = new Cell(redoStack.removeFirst());
      
      //update state of redoStackIsEmpty
      redoStackEmpty();

      //get col and row from redo cell
      String col = cellReturn.getCol();
      int row = cellReturn.getRow();
      
      //get deep copy of the current state of the cell being redone
      Cell cellCurrent = new Cell(grid.getCell(col, row));
      
  	  //make copy of current cell and add to undo stack
  	  //make deep copy of current cell
  	  Cell copyCell = new Cell(cellCurrent);

  	  //add the copy to undo stack
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
