/*
 * This is the class of Cell
 * wrote by Chang Wei
 */

import java.util.Observer;
import java.util.Observable;

public class Cell 
{

	/*variables*/
	private String _value = "0";
	private double _evaluatedValue = 0.0;
	private String _col;
	private int _row;
	private Grid _grid;
    
	/* constructor */
	public Cell( String col, int row, Object grid)
	{
		_col = col;
		_row = row;
		
		//test if the object is type of Grid
		if(grid instanceof Grid)
		{
			_grid = (Grid)grid;
		}
		else
		System.out.println("Wrong object parameter!");
		
	}
    
    /* accessors */
	
	// return the value String
	public String getValue()
	{
		return this._value;
	}
	
	//return the column String
	public String getCol()
	{
		return this._col;
	}
	
	//return the number of row
	public int getRow()
	{
		return this._row;
	}
    
	//return the Grid
	public Grid getGrid()
	{
		return this._grid;
	}
	
	
    /* mutators */
	public void setValue(String value)
	{
		this._value = value;
	}
    
    /* additional methods */
	
	public boolean validateValue()
	{
		// TODO
		return true;
	}
	
	
	public double evaluate()
	{
		//   TODO
		return 0;
	}
	
	
	public void display()
	{
		System.out.println(_evaluatedValue);
	}
	
	
	
}
