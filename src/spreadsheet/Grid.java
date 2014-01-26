package spreadsheet;

import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import spreadsheet.Cell;


public class Grid implements  java.io.Serializable{

	// Attributes
	private Hashtable<String, Cell> _cells;
	private Cell _selectedCell;
	private int _maxWidth;
	private int _maxHeight;
	private int _currentWidth;
	private int _currentHeight;
	
	// Constructor
	public Grid(){
		_cells = new Hashtable<String, Cell>(); // generates hashtable for cells
		_selectedCell = new Cell("A", 1, this );
		_cells.put("A" + 1, _selectedCell);
		_maxWidth = 10;
		_maxHeight = 10;
		_currentWidth = 1;
		_currentHeight = 1;
	}
	
	// parameterized constructor 
	public Grid(int maxWidth, int maxHeight) {
		_cells = new Hashtable<String, Cell>(); // generates hashtable for cells
		_selectedCell = new Cell("A", 1, this );
		_cells.put("A" + 1, _selectedCell);
		_maxWidth = maxWidth;
		_maxHeight = maxHeight;
		_currentWidth = 1;
		_currentHeight = 1;
	}
	
	/* 
	* getter to get a cell, will take column and a row and 
	set the values in the hashtable, then return that cell
	*/
	public Cell getCell(String col, int row) {
		int intCol = colToNumber(col);
		if(intCol <= _maxWidth && intCol >= 1 && row <= _maxHeight && row >= 1 ){
			Cell foundCell = _cells.get( col + row );
			if( foundCell == null ){
				foundCell = new Cell( col, row, this );
				_cells.put(col + row, foundCell);
				
				if(intCol > _maxWidth)
					_maxWidth = intCol;
				if(row > _maxHeight)
					_maxHeight = row;
				
			}
			return foundCell;
		}else{
			return null;
		}
	}
	
	/*
	* setter to set the cells in hashtable
	*/
	public void setCells( Hashtable<String, Cell> cells ) {
		_cells = cells;
	}
	
	/*
	* method to change string "column" to int
	*/
	private int colToNumber(String str) {
		char[] ls = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		Map<Character, Integer> m = new HashMap<Character, Integer>();
		int j = 1;
		for(char c: ls) {
			m.put(c, j++);
		}
		
		int i = 0;
		int mul = 1;
		for(char c: new StringBuffer(str).reverse().toString().toUpperCase().toCharArray()) {
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
	public Cell selectCell(String col, int row){
		Cell selectedCell = this.getCell(col, row);
		if( selectedCell != null )
			return _selectedCell = selectedCell;
		return null;
		
	}
	
	/*
	* getter to get the selelcted cell
	*/
	public Cell getSelectedCell() {
		return _selectedCell;
	}
	
	
	public void removeSelectedCell() {
		_cells.remove( _selectedCell.getCol() + _selectedCell.getRow() );        /////TO MODIFY (NEED TO CORRECTLY INTERACT WITH CELLS TO AVOID DESYNC)
		_selectedCell = null;
	}
	
	public void removeCell(String col, int row) {                                /////TO MODIFY (NEED TO CORRECTLY INTERACT WITH CELLS TO AVOID DESYNC)
		_cells.remove( col + row );
		if(_selectedCell == this.getCell(col, row))
			_selectedCell = null;
	}
	
	public int getMaxWidth() {return _maxWidth;}
	public int getMaxHeight() {return _maxHeight;}
	
	public int getCurrentWidth() {return _currentWidth;}
	public int getCurrentHeight() {return _currentHeight;}
	
	
	public void setMaxWidth(int maxWidth) {_maxWidth = maxWidth;}
	public void setMaxHeight(int maxHeight) {_maxHeight = maxHeight;}
	
	
	/*
	* Display grid with values
	*/
	public String Display() {
		return "Grid [Cells=" + _cells + "]";                   //TODO///////////////////////////////////////////////////////////
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
			FileOutputStream fileOut = new FileOutputStream(fileName + ".sav");
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
			FileInputStream fileIn = new FileInputStream(fileName + ".sav");
		    ObjectInputStream in = new ObjectInputStream(fileIn);
		    _cells = (Hashtable<String, Cell>)in.readObject();
		    in.close();
		    fileIn.close();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}