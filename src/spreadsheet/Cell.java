package spreadsheet;
/*
 * This is the Cell Class
 * wrote by Chang Wei
 */

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import utils.Formula;


public class Cell extends Observable implements Observer, java.io.Serializable{

	//variables
	private String _value = "0";
	private double _evaluatedValue = 0.0;
	private String _col;
	private int _row;
	private Grid _grid;
	private boolean _validValue = true;
	private ArrayList<Cell> _observedCells;
    
	// constructor 
	public Cell( String col, int row, Grid grid){
		_observedCells = new ArrayList<Cell>();
		_col = col;
		_row = row;
		_grid = grid;
		
	}
    
	@Override public void update(Observable o, Object arg){
		this.evaluate();
		//Cell cell = (Cell) o;
		//System.out.println(cell.getCol() + cell.getRow() + " called update on " + _col+_row);
	}
	
	//getters and setters
	public String getValue(){return _value;}
	public double getEvaluatedValue(){return _evaluatedValue;}
	
	public String getCol(){return _col;}
	public int getRow(){return _row;}
    
	public Grid getGrid(){return _grid;}
	public ArrayList<Cell> getObservedCells(){return _observedCells;}
	
	public boolean isValidValue(){return _validValue;}
	
	
   
	/* 
	 * setter: to set the value
	 */
	public void setValue(String value){
		_value = value;
		if(validateValue()){
			_validValue = true;
			if(!this.isNumeric(_value)){
				try{
					for(Cell cell : _observedCells){
						cell.deleteObserver(this);
					}
					_observedCells = Formula.listReferencedCells(this);
					for(Cell cell : _observedCells){
						cell.addObserver(this);
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
					_validValue = false;
				}
			}
			this.evaluate();
		}else{
			_validValue = false;
		}
	}
    
    
	
	
	//additional methods 
	
	/* 
	 * check if the user input satisfied the syntax
	 */
	private boolean validateValue(){
		
		String str = _value.trim(); // take off all the spaces for easier calculation

		if( isNumeric(str) ){
			return true;
		}else{
			return isValidChar(str) ;
		}
	}
	
	
	/* 
	 *  do the calculation or simply return the value of number
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
	
	
	/* 
	 * display the evaluatedValue
	 */
	public void display() {
		if(_validValue)
			System.out.println(_evaluatedValue);
		else
			System.out.println("INVALID VALUE: " + _value);
	}
	
	
	public String getDisplay(){
		if(_validValue)
			return String.valueOf(_evaluatedValue);
		else
			return"INVALID VALUE: " + _value;
		
	}
	
	/* 
	 *  check if the string can be converted to a number
	 */
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	} 
	
	
	/* 
	 *  check if the formula string stars with "=" and contains:
	 *   "+"	"-"		"*"		"/"		"("		")"		
	 *   "."	"A-Z"	"0-9"	" "
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
	
	
	private void setChangeAndNotify(){
		this.setChanged();
		this.notifyObservers();
		
	}
	
}
