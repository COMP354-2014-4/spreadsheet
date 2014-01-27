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
		char[] chars = str.toUpperCase().toCharArray();

	    int sum = 0;

	    for (int i = 0; i < chars.length; i++)
	    {
	        sum *= 26;
	        sum += (chars[i] - 'A' + 1);
	    }

	    return sum;
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
		this.removeCell( _selectedCell.getCol(), _selectedCell.getRow() );
	}
	
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
	
	public int getMaxWidth() {return _maxWidth;}
	public int getMaxHeight() {return _maxHeight;}
	
	public int getCurrentWidth() {return _currentWidth;}
	public int getCurrentHeight() {return _currentHeight;}
	
	
	public void setMaxWidth(int maxWidth) {_maxWidth = maxWidth;}
	public void setMaxHeight(int maxHeight) {_maxHeight = maxHeight;}
	
	
	/*
	* Display grid with values
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
	
	private String centerPad(String string, int size){
		int stringLength = string.length();
		int padding = size - stringLength;
		int leftPad = padding - padding/2;
		int rightPad = padding - leftPad;
		
		
		return createPad(rightPad) + string + createPad(leftPad);
	}
	
	private String createPad(int size){
		String s = "";
		for(int i = 0; i < size; i++)
			s+=" ";
		return s;
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
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}catch (IOException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
	}
}