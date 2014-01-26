package spreadsheet;
/*
 * This is the Cell Class
 * wrote by Chang Wei
 */

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import utils.Formula;


public class Cell extends Observable implements Observer{

	//variables
	private String _value = "0";
	private double _evaluatedValue = 0.0;
	private String _col;
	private int _row;
	private Grid _grid;
	private boolean _validValue = true;
    
	// constructor 
	public Cell( String col, int row, Grid grid){
		_col = col;
		_row = row;
		_grid = grid;
		
	}
    
	@Override public void update(Observable o, Object arg){
		this.evaluate();
	}
	
	//getters and setters
	public String getValue(){return _value;}
	public double getEvaluatedValue(){return _evaluatedValue;}
	
	public String getCol(){return _col;}
	public int getRow(){return _row;}
    
	public Grid getGrid(){return this._grid;}
	
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
					ArrayList<Cell> cells = Formula.listReferencedCells(_value, _grid);
					for(Cell cell : cells){
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
		//if the string is an integer
		if (this.isNumeric(_value)){
			_evaluatedValue = Double.parseDouble(_value);
			_validValue = true;
			this.setChangeAndNotify();
			return _evaluatedValue;
		}else{//else call the formula function
			// call the Formula.evaluateFormula function
			try{
				_evaluatedValue = Formula.evaluateFormula(this._value, this._grid);
				_validValue = true;
				this.setChangeAndNotify();
				return _evaluatedValue;
			}catch(Exception e){
				System.out.println(e.getMessage());
				_validValue = false;
				_evaluatedValue = 0;
				this.setChangeAndNotify();
				return _evaluatedValue;
			}
		}
		
		
		
	}
	
	
	/* 
	 * display the evaluatedValue
	 */
	public void display() {
		System.out.println(_evaluatedValue);
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
