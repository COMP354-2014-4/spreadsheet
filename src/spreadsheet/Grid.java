package spreadsheet;

import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


import spreadsheet.Cell;

/**
 * The Grid class represent a single spreadsheet. It is stored in
 * a hash table to keep an infinite scrolling spreadsheet to take
 * too much memory
 * 
 * @author Comp354 Team 3
 */
public class Grid implements  java.io.Serializable{

	// Attributes
	private Hashtable<String, Cell> _cells;
	private Cell _selectedCell;
	private int _maxWidth;
	private int _maxHeight;
	
	//Current width and height are keeped upped to date
	//as cell are accessed
	private int _currentWidth;
	private int _currentHeight;
	
	/**
	 * Default constructor. Set the max size to
	 * 10 x 10, create the cell A1 and select it
	 */
	public Grid(){
		_cells = new Hashtable<String, Cell>(); // generates hashtable for cells
		_selectedCell = new Cell("A", 1, this );
		_cells.put("A" + 1, _selectedCell);
		_maxWidth = 10;
		_maxHeight = 10;
		_currentWidth = 1;
		_currentHeight = 1;
	}
	
	/**
	 * Default constructor. Set the max size to
	 * maxWidth X maxHeight, create the cell A1 and select it
	 * 
	 * @param maxWidth	The maximum width of the spreadsheet. -1 = infinite
	 * @param maxHeight	The maximum height of the spreadsheet. -1 = infinite
	 */
	public Grid(int maxWidth, int maxHeight) {
		_cells = new Hashtable<String, Cell>(); // generates hashtable for cells
		_selectedCell = new Cell("A", 1, this );
		_cells.put("A" + 1, _selectedCell);
		_maxWidth = maxWidth;
		_maxHeight = maxHeight;
		_currentWidth = 1;
		_currentHeight = 1;
	}
	
	/**
	 * Try to access a single cell inside of the spreadsheet.
	 * If the cell doesn't exist, it will create it as long
	 * as it is inside of the bound(maxWidth/maxHeight)
	 * 
	 * @param col	Column of the cell. Format: "AZ", "A"
	 * @param row	Row of the cell.
	 * @return		The cell if it's inside of the bounds, null otherwise
	 */
	public Cell getCell(String col, int row) {
		int intCol = colToNumber(col);
		if(intCol <= _maxWidth && intCol >= 1 && row <= _maxHeight && row >= 1 ){
			Cell foundCell = _cells.get( col + row );
			if( foundCell == null ){
				foundCell = new Cell( col, row, this );
				_cells.put(col + row, foundCell);
				
				if(intCol > _currentWidth)
					_currentWidth = Math.min(intCol, _maxWidth);
				if(row > _currentHeight)
					_currentHeight = Math.min(row, _maxHeight);
				
			}
			return foundCell;
		}else{
			return null;
		}
	}
	
	/**
	 * Changes the hash table to the one specified
	 * 
	 * @param cells The new hash table for the grid
	 */
	public void setCells( Hashtable<String, Cell> cells ) {
		_cells = cells;
	}
	
	/**
	 * Select a cell if it is within bounds. If the cell
	 * doesn't exist it will be created. If the cell is out
	 * of bound, the selected cell won't change.
	 * 
	 * @param col	Column of the cell. Format: "AZ", "A"
	 * @param row	Row of the cell.
	 * @return		The cell at location (col, row). Null if out of bound.
	 */
	public Cell selectCell(String col, int row){
		Cell selectedCell = this.getCell(col, row);
		if( selectedCell != null )
			return _selectedCell = selectedCell;
		return null;
		
	}
	
	/**
	 * Remove the selected cell from the grid as long as the
	 * cell isn't observed by any other
	 */
	public void removeSelectedCell() {
		this.removeCell( _selectedCell.getCol(), _selectedCell.getRow() );
	}
	
	/**
	 * Remove the cell at location (col, row) from the grid as long as the
	 * cell isn't observed by any other. If it is the selected cell, selected
	 * cell will be set to null
	 * 
	 * @param col	Column of the cell. Format: "AZ", "A"
	 * @param row	Row of the cell.
	 */
	public void removeCell(String col, int row) {
		Cell cell = this.getCell(col, row);
		
		
		for(Cell observed : cell.getObservedCells()){
			observed.deleteObserver(cell);
		}
		cell.setValue("0");
		
		if( cell.countObservers() == 0 ){
			_cells.remove( col + row );
			if(_selectedCell == this.getCell(col, row))
				_selectedCell = null;
		}
	}
	
	/**
	 * Empties the Grid
	 */
	public void clear() {
		_cells.clear();
	}
	
	//Basic getters
	public Cell getSelectedCell() {return _selectedCell;}
	
	public int getMaxWidth() {return _maxWidth;}
	public int getMaxHeight() {return _maxHeight;}
	
	public int getCurrentWidth() {return _currentWidth;}
	public int getCurrentHeight() {return _currentHeight;}
	
	//Basic mutators
	public void setMaxWidth(int maxWidth) {_maxWidth = maxWidth;}
	public void setMaxHeight(int maxHeight) {_maxHeight = maxHeight;}
	
	
	/**
	 * Display the grid inside of the console
	 */
	public void Display() {
		System.out.println("GRID");
		System.out.println("---------------------------");
		for(int i = 0; i <= _currentHeight; i++){
			for(int c = 0; c <= _currentWidth; c++){				
				System.out.print("|");
				if(i == 0){
					if(c == 0){
						System.out.print(centerPad("", 10));
					}else{
						System.out.print(centerPad(numToCol(c), 10));
					}
				}else{
					if(c == 0){
						System.out.print(centerPad(String.valueOf(i), 10));
					}else{
						System.out.print(centerPad(this.getCell(numToCol(c), i).getDisplay(), 10));
						
					}
				}
			}
			System.out.print("|\n" );
		}
	}
	
	/**
	 * Utility method to convert a number to
	 * its equivalent column name
	 * 
	 * @param columnIndex	the integer representation of the column
	 * @return				the string representation of the column
	 */
	private String numToCol(int columnIndex){ 
		if (columnIndex == 0)
			return null;
		columnIndex--;
		int base = 26;
		StringBuffer b = new StringBuffer();
		do {
			int digit = columnIndex % base + 65;
			b.append(Character.valueOf((char) digit));
			columnIndex = (columnIndex / base) - 1;
		} while (columnIndex >= 0);
		
		return b.reverse().toString();

	}
	
	/**
	 * Utility method to convert a column name to
	 * its equivalent integer
	 * 
	 * @param str	the string representation of the column
	 * @return		the integer representation of the column
	 */
	private int colToNumber(String str) {
		char[] chars = str.toUpperCase().toCharArray();

	    int sum = 0;

	    for (int i = 0; i < chars.length; i++)
	    {
	        sum *= 26;
	        sum += (chars[i] - 'A' + 1);
	    }

	    return sum;
	}
	
	/**
	 * Utility method to add space padding
	 * to the left and right of a string
	 * 
	 * @param string	the string to pad
	 * @param size		the total number of character you want the string to be
	 * @return			the final string with the pading
	 */
	private String centerPad(String string, int size){
		int stringLength = string.length();
		int padding = size - stringLength;
		int leftPad = padding - padding/2;
		int rightPad = padding - leftPad;
		
		
		return createPad(rightPad) + string + createPad(leftPad);
	}
	
	/**
	 * Utility method to create a string of
	 * x spaces where x is the size
	 * 
	 * @param size	number of space
	 * @return		string of size spaces
	 */
	private String createPad(int size){
		String s = "";
		for(int i = 0; i < size; i++)
			s+=" ";
		return s;
	}
	
	
	
	/**
	 * Save the grid to the file
	 * 
	 * @param fileName	The filename without the extension
	 */
	public boolean save(String fileName){
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName + ".sav");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_cells);
			out.close();
			fileOut.close();
			return true;
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Load a grid from the specified file
	 * 
	 * @param fileName	The filename without the extension
	 */
	public boolean load(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream(fileName + ".sav");
		    ObjectInputStream in = new ObjectInputStream(fileIn);
		    _cells = (Hashtable<String, Cell>)in.readObject();
		    in.close();
		    fileIn.close();
			return true;
		}catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}