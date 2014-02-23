package spreadsheet;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import utils.Formula;

/**
 * The Cell class is the representation of
 * a single cell inside of a grid.
 * Each cell is an observer that can observe
 * many other cells in order to keep it's formula
 * updated.
 * 
 * @author Comp354 Team 3
 */
public class Cell extends Observable implements Observer, java.io.Serializable{

	//Attributes
	private String _value = "0";			//The value is stored as a string to keep track of both formulas and integer
	private double _evaluatedValue = 0.0;	//Hold the value after the evaluation is completed
	private String _col;
	private int _row;
	private Grid _grid;
	private boolean _validValue = true;		//states if the value can be computed
	private ArrayList<Cell> _observedCells;
    
	/**
	 * Parameterized constructor
	 * 
	 * @param col	The column of the cell
	 * @param row	The row of the cell
	 * @param grid	The grid in which the cell is
	 */
	public Cell( String col, int row, Grid grid){
		_observedCells = new ArrayList<Cell>();
		_col = col;
		_row = row;
		_grid = grid;
		
	}
    
	/**
	 * When an observed cell changes, call evaluate()
	 * to make sure that it's value is up to date
	 */
	@Override public void update(Observable o, Object arg){
		this.evaluate();
	}	
   
	/**
	 * Setter for the value. Stops observing any cell.
	 * Sets it's new value.
	 * If the new value is a formula, get a list of the
	 * cell references inside of the formula. If the formula
	 * isn't circular, starts observing them. Else set the
	 * value as invalid
	 * 
	 * @param value	The new value
	 */
	public void setValue(String value){
		_value = value;
		if(validateValue()){//Validate the value
			_validValue = true;
			if(!this.isNumeric(_value)){
				try{
					for(Cell cell : _observedCells){//Stops observing old cells
						cell.deleteObserver(this);
					}
					_observedCells = Formula.listReferencedCells(this);//if exception: thrown here <--
					for(Cell cell : _observedCells){//starts observing the new cells
						cell.addObserver(this);
					}
				}catch(Exception e){//Circular reference or invalid formula
					System.out.println(e.getMessage());
					_validValue = false;
				}
			}
			this.evaluate();//Start the evaluation of the cell value
		}else{
			_validValue = false;
		}
	}
    
    /**
     * Make sure that the value only has valid characters.
     * 
     * @return	true if valid, false otherwise
     */
	private boolean validateValue(){
		
		String str = _value.trim(); // take off all the spaces for easier calculation

		if( isNumeric(str) ){
			return true;
		}else{
			return isValidChar(str) ;
		}
	}
	
	
	/**
	 * If the value is numeric, simply convert it into a
	 * double. If the value is a formula, call Formula.evaluateFormula.
	 * If the formula cannot be evaluated, the value will be set to invalid
	 * and evaluated value will be set to 0.
	 * After the evaluation, notify all the cell that observers it
	 * 
	 * @return	The evaluated value or 0 if the formula cannot be evaluated
	 * @see		Formula
	 */
	public double evaluate(){
		if(_validValue){
			//if the string is an integer
			if (this.isNumeric(_value)){
				_evaluatedValue = Double.parseDouble(_value);
				_validValue = true;
				this.setChangeAndNotify();
				return _evaluatedValue;
			}else{//else call the formula function
				// call the Formula.evaluateFormula function
				try{
					_evaluatedValue = Formula.evaluateFormula(this);
					this.setChangeAndNotify();
					_validValue = true;
					return _evaluatedValue;
				}catch(Exception e){
					System.out.println(e.getMessage());
					_validValue = false;
					_evaluatedValue = 0;
					this.setChangeAndNotify();
					return _evaluatedValue;
				}
			}
		}else{
			this.setChangeAndNotify();
			return 0;
		}
		
		
		
	}
	
	
	/**
	 * Display the evaluated value of a single cell
	 */
	public void display() {
		if(_validValue)
			System.out.println(_evaluatedValue);
		else
			System.out.println("INVALID VALUE: " + _value);
	}
	
	/**
	 * Get the string of the display
	 * 
	 * @return The evaluated value
	 */
	public String getDisplay(){
		if(_validValue)
			return String.valueOf(_evaluatedValue);
		else
			return"INVALID VALUE: " + _value;
		
	}
	
	/**
	 * Check if the string can be converted to a number
	 * 
	 * @param s The string
	 * @return	True if it is a number, false otherwise
	 */
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	} 
	
	/**
	 * check if the formula string stars with "=" and contains:
	 * "+"	"-"		"*"		"/"		"("		")"
	 * "."	"A-Z"	"0-9"	" "
	 * 
	 * @param temp	The string
	 * @return		True if the value is valid, false otherwise
	 */
	public boolean isValidChar(String temp) {
		int counter = 0; // "(", " )" counter
		
		int len = temp.length();
		char c = temp.charAt(0);
		if( c != '='){
			//if the first character is not "="
			return false;
	    }
		
		for(int i=1;i<len;i++) {
		    c = temp.charAt(i);
		    // Test for all positive cases
		
			if( ('0'<=c && c<='9') ||( 'A'<=c && c<='Z' ) || 
					c=='-' || c=='+'|| c=='*'||  c=='/'|| 
					c=='(' || c==')' || c=='.' || c==' '){
				
				if(c=='(') {
					counter ++;
					continue;
				}
				if(c==')'){
			        	counter --;
			        	continue;
			    }
			}else{
			    return false;
			}
		
		}//the end of for
		
	    // if the "(" and ")" are not in pairs
	 	if(counter != 0 ){
	 		return false;
	 	}else{
	 		return true;
	 	}
	}
	
	
	/**
	 * notify all observers that a change happened
	 * in the value of this cell
	 */
	private void setChangeAndNotify(){
		this.setChanged();
		this.notifyObservers();
		
		_grid.getSSTable().setValueAt(_evaluatedValue, _row, Grid.colToNumber(_col));
		
	}
	
	//getters
	public String getValue(){return _value;}
	public double getEvaluatedValue(){return _evaluatedValue;}
	
	public String getCol(){return _col;}
	public int getRow(){return _row;}
    
	public Grid getGrid(){return _grid;}
	public ArrayList<Cell> getObservedCells(){return _observedCells;}
	
	public boolean isValidValue(){return _validValue;}
		

	
}
