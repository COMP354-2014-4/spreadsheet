import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class Grid {

	// Attributes
	private Hashtable Cells;
	private Cell selectedCell;
	private String col;
	private int row;
	private int maxWidth;
	private int maxHeight;
	private int currentWidth;
	private int currentHeight;
	
	// Constructor
	public Grid(){
		Hashtable Cells = new Hashtable(); // generates hashtable for cells
		Cells.put(col, row);
		Cell selectedCell = new Cell( col,  row, this );
		String col = "A";
		int row = 1;
		int maxWidth = 10;
		int maxHeight = 10;
		int currentWidth = 0;
		int currentHeight = 0;
	}
	
	// parameterized constructor 
	public Grid(int maxWidth, int maxHeight) {
		super();
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	/* 
	 * getter to get a cell, will take column and a row and 
	set the values in the hashtable, then return that cell
	*/
	public Hashtable getCells(String col, int row) {
		this.col = col;
		this.row = row;
		Cells.put(col, row);
		return Cells;
	}

	/*
	 * setter to set the cells in hashtable
	 */
	public void setCells(@SuppressWarnings("rawtypes") Hashtable cells) {
		Cells = cells;
	}
	
	/*
	 * method to change string "column" to int
	 */
	public int stringToNumber(String str) {
		  char[] ls = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		  Map<Character, Integer> m = new HashMap<Character, Integer>();
		  int j = 1;
		  for(char c: ls) {
		    m.put(c, j++);
		  }
		  int i = 0;
		  int mul = 1;
		  for(char c: new StringBuffer(str).reverse().toString().toCharArray()) {
		    i += m.get(c) * mul;
		    mul *= ls.length;
		  }
		  return i;
		}
	
	/*
	 * method to select a cell, if the cell is within given range
	 * and does not exist, will create the cell. If not within range
	 * return null
	 */
	@SuppressWarnings("rawtypes")
	public Hashtable selectCell(String col, int row){
		if(stringToNumber(col)< maxWidth && row < maxHeight) {
			selectedCell = new Cell (col, row, this);
			currentHeight = stringToNumber(col);
			currentWidth = row;
		}		
		else 
			return null;
	}

	/*
	 * getter to get the selelcted cell
	 */
	public Cell getSelectedCell() {
		return selectedCell;
	}

	/*
	 * setter to set the selelcted cell
	 */
	public void setSelectedCell(Cell selectedCell) {
		this.selectedCell = selectedCell;
	}

	/*
	 * getter to get MaxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/*
	 * setter to set MaxWidth
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/*
	 * getter to get MaxHeight
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/*
	 * setter to set MaxHeight
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	/*
	 * getter to get CurrentHeight
	 */
	

	public String Display() {
		return "Grid [Cells=" + Cells + ", col=" + col + ", row=" + row + "]";
	}

	
	
}
