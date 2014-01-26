import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class Grid {

	// Attributes
	private Hashtable _cells;
	private Cell _selectedCell;
	private String col;
	private int row;
	private int _maxWidth;
	private int _maxHeight;
	private int currentWidth;
	private int currentHeight;
	
	// Constructor
	public Grid(){
		_cells = new Hashtable(); // generates hashtable for cells
		_cells.put(col, row);
		_selectedCell = new Cell(col, row, this );
		_maxWidth = 10;
		_maxHeight = 10;
		currentWidth = 0;
		currentHeight = 0;
	}
	
	// parameterized constructor 
	public Grid(int _maxWidth, int _maxHeight) {
		super();
		this._maxWidth = _maxWidth;
		this._maxHeight = _maxHeight;
	}

	/* 
	 * getter to get a cell, will take column and a row and 
	set the values in the hashtable, then return that cell
	*/
	public Hashtable getCell(String col, int row) {
		this.col = col;
		this.row = row;
		_cells.put(col, row);
		return _cells;
	}
	
	/*
	 * method to remove cell
	 */
	public void removeCell(String col, int row){
		_cells.remove(col);
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
		if(stringToNumber(col)< _maxWidth && row < _maxHeight) {
			_selectedCell = new Cell (col, row, this);
			currentHeight = stringToNumber(col);
			currentWidth = row;
			return null;
		}		
		else 
			return null;
	}

	/*
	 * getter to get the selelcted cell
	 */
	public Cell getSelectedCell() {
		return _selectedCell;
	}

	/*
	 * method to remove the selelcted cell
	 */
	public void removeSelectedCell() {
		_cells.remove(_selectedCell);
	}

	/*
	 * getter to get MaxWidth
	 */
	public int getMaxWidth() {
		return _maxWidth;
	}

	/*
	 * setter to set MaxWidth
	 */
	public void setMaxWidth(int maxWidth) {
		this._maxWidth = maxWidth;
	}

	/*
	 * getter to get MaxHeight
	 */
	public int getMaxHeight() {
		return _maxHeight;
	}

	/*
	 * setter to set MaxHeight
	 */
	public void setMaxHeight(int maxHeight) {
		this._maxHeight = maxHeight;
	}
	
	/*
	 * getter to get CurrentWidth
	 */
	public int getCurrentWidth() {
		return currentWidth;
	}

	/*
	 * getter to get CurrentHeight
	 */
	public int getCurrentHeight() {
		return currentHeight;
	}

	/*
	 * Display grid with values
	 */
	public String Display() {
		return "Grid [Cells=" + _cells + ", column " + col + ", row=" + row + " has " + _cells.get(col) +"]";
	}
	
	/*
	 * method to clear the grid
	 */
	public void clear() {
		_cells.clear();
	}
	
	/*
	 * method to save the grid to a file
	 */
	public void save(String fileName){
		try {
			FileOutputStream fileOut = new FileOutputStream("grid.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_cells);
			out.close();
			fileOut.close();
		}
		catch(FileNotFoundException e){
	    e.printStackTrace();
    	} 
		catch (IOException e) {
        e.printStackTrace();
    }
	}
	
	/*
	 * method to load from an existing file
	 */
	public void load(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream("grid.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            _cells = (Hashtable)in.readObject();
            in.close();
            fileIn.close();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
