/*
 * This is the class of Cell
 * wrote by Chang Wei
 */

import java.util.Observer;
import java.util.Observable;
import java.util.regex.*;

public class Cell 
{

	//variables
	private String value = "0";
	private double evaluatedValue = 0.0;
	private String col;
	private int row;
	private Grid grid;
    
	// constructor 
	public Cell( String col, int row, Object grid)
	{
		col = col;
		row = row;
		
		//test if the object is type of Grid
		if(grid instanceof Grid)
		{
			grid = (Grid)grid;
		}
		else
		System.out.println("Wrong object parameter!");
		
	}
    
  
	
	//getters and setters
	
	/* 
	 * getter: get the value string
	 */
	public String getValue()
	{
		return this.value;
	}
	
	/* 
	 * getter: get the column string
	 */
	public String getCol()
	{
		return this.col;
	}
	
	
	/* 
	 * getter: get the row number
	 */
	public int getRow()
	{
		return this.row;
	}
    
	
	/* 
	 * getter: get the Grid
	 */
	public Grid getGrid()
	{
		return this.grid;
	}
	
	
   
	/* 
	 * setter: to set the value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
    
    
	
	
	//additional methods 
	
	/* 
	 * check if the user input satisfied the syntax
	 */
	public boolean validateValue()
	{
		
		String str = value.trim(); // take off all the spaces for easier calculation

		if(isNumeric(str))
		{
			//if the user input is a number, then return true
			return true;
		}
		else
		{
			return isValidChar(str) ;
			
		}//the end of else
	}
	
	
	/* 
	 *  do the calculation or simply return the value of number
	 */
	public double evaluate()
	{
		//if the string is an integer
		if (this.isNumeric(value)) 
		{
			return Integer.parseInt( value );
		}
		
		//else call the formula function
		else
		{
			// TODO call the formula function
			
			return 0;
		}
		
		
		
	}
	
	
	/* 
	 * display the evaluatedValue
	 */
	public void display() 
	{
		System.out.println(evaluatedValue);
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
	public boolean isValidChar(String temp) 
	{
		int counter = 0; // "(", " )" counter
		
	    int len = temp.length();
	    char c = temp.charAt(0);
	    if( c != '=')
	    {
	    	//if the first character is not "="
	    	return false;
	    }
	    for(int i=1;i<len;i++) 
	    {
	        c = temp.charAt(i);
	        // Test for all positive cases
	        
	        if( ('0'<=c && c<='9') ||( 'A'<=c && c<='Z' ) || 
	        		c=='-' || c=='+'|| c=='*'||  c=='/'|| 
	        		c=='(' || c==')' || c=='.' || c==' ')
	        {
	        	 if(c=='(') 
	 	        {
	 	        	counter ++;
	 	        	continue;
	 	        }
	 	        if(c==')')
	 	        {
	 	        	counter --;
	 	        	continue;
	 	        }
	        }
	        else
	        	return false;
	
	    }//the end of for
	    // if the "(" and ")" are not in pairs
	 	if(counter != 0 )
	 	{
	 		return false;
	 	}
	 	else
	 	{
	 		
	 		return true;
	 	}
	}
	
	
	
	
}
