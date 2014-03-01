package utils;

import java.util.ArrayList;

import utils.TokenStack;
import spreadsheet.Grid;
import spreadsheet.Cell;
import utils.Token.TokenType;
/**
 * Formula class is a static class
 * that contains the method to evaluate
 * arithmetics operation given in a string format
 * with "excel-like" syntax
 * 
 * @author Comp354 Team 3
 */
public class Formula {
	/**
	 * Private constructor. Should never be instantiated
	 */
	private Formula(){}

	/**
	 * Find each cell in the formula while checking if
	 * the syntax is right and return them. Also make sure
	 * that there are no circular references
	 * 
	 * @param originCell	The cell that contains the formula
	 * @return				A list of all cell contained inside of the formula
	 * @throws Exception	If the formula contains a circular reference or is invalid
	 */
	public static ArrayList<Cell> listReferencedCells(Cell originCell) throws Exception{
		String formula = originCell.getValue();
		Grid grid = originCell.getGrid();
		ArrayList<Token> tokens = new ArrayList<Token>();
		ArrayList<Cell> cells = new ArrayList<Cell>();
		ArrayList<Cell> origin = new ArrayList<Cell>(); //Used to check if those cells contains circular ref
		origin.add(originCell);

		if(Formula.tokenize(tokens, formula)){//Convert the string in a list of token
			for(Token tok : tokens){
				if(tok.getType() == TokenType.CEL){//extract the cell from the list of token
					Cell cell = grid.getCell(tok.getCol(), tok.getRow());
					cells.add(cell);
				}
			}
			if( !Formula.isCircular(origin, cells) ){//checks for circularity
				return cells;

			}else{
				throw new Exception("Circular formula. The evaluator will not be able to evaluate this formula");

			}

		}else{
			throw new Exception("Invalid formula. The evaluator will not be able to evaluate this formula");
		}
	}

	/**
	 * Depth first traversal of the implied directed graph of
	 * observer-observed cell. Go down each sub path to check for circularity
	 * 
	 * @param visited	list of visited cell in the current sub path
	 * @param toVisit	list of cells to visit in the current sub path
	 * @return			True if a cycle was found, false otherwise
	 */
	private static boolean isCircular(ArrayList<Cell> visited, ArrayList<Cell> toVisit){		
		for(Cell sub : toVisit){
			if(visited.contains(sub))//If a the cell has already been visited in this sub path, there's a cycle
				return true;
			ArrayList<Cell> visitedCopy = visited;
			ArrayList<Cell> subToVisit = sub.getObservedCells();
			if(subToVisit.size() == 0 || subToVisit == null)//if the sub path is at a dead end
				return false;								//there is no cycle in that sub path
			visitedCopy.add(sub);
			return isCircular(visitedCopy, subToVisit);//Keep on going down the graph

		}
		return false;
	}
	/**
	 * Evaluates the formula in a given cell
	 * 
	 * @param originCell	The cell that contains the formula
	 * @return				The evaluated value
	 * @throws Exception	If the formula cannot be evaluated for one reason or another
	 */
	public static double evaluateFormula(Cell originCell) throws Exception{
		String formula = originCell.getValue();
		Grid grid = originCell.getGrid();
		double res = 0;
		ArrayList<Token> tokens = new ArrayList<Token>();


		//parsing the formula into tokens
		if(Formula.tokenize(tokens, formula)){

			//creating the stacks tha will hold all of the operators
			TokenStack operatorStack = new TokenStack();
			TokenStack operandStack = new TokenStack();

			for(Token tok : tokens){
				//if it is an operator
				if(tok.getType() == TokenType.PLU || tok.getType() == TokenType.MIN || tok.getType() == TokenType.DIV || tok.getType() == TokenType.MUL || tok.getType() == TokenType.MOD ){
					//While the new operator has a smaller precedence, evaluate the top of the operator stack
					while(!operatorStack.isEmpty() && operatorStack.top().getType() != TokenType.LPA  && operatorStack.top().getPrecedence() >= tok.getPrecedence()){
						double val = Formula.calculateFirst(operatorStack, operandStack);
						operandStack.push(new Token(TokenType.NUM, val));
					}
					operatorStack.push(tok);//add the new operator to the stack
				}else if(tok.getType() == TokenType.LPA ){
					operatorStack.push(tok);
				}else if(tok.getType() == TokenType.RPA ){
					//evaluate the top operator until we hit the closing parenthesis
					while(operatorStack.top().getType() != TokenType.LPA ){
						double val = Formula.calculateFirst(operatorStack, operandStack);
						operandStack.push(new Token(TokenType.NUM, val));
					}
					operatorStack.pop();//pop the closing parenthesis
				}else{
					//if tok is an operand
					if(tok.getType() == TokenType.CEL){//if it's a cell and is value is valid
						Cell cell = grid.getCell(tok.getCol(), tok.getRow());
						if(cell != null && cell.isValidValue()){
							tok = new Token(TokenType.NUM, cell.getEvaluatedValue());//convert it to a num token

						}else{
							String message = "Invalid formula. " + tok.getCol() + tok.getRow() + " is out of bounds or has an invalid value";
							originCell.getGrid().getGUI().displayMessage(message);
							throw new Exception(message);
						}
					}
					operandStack.push(tok);//push the operand on the stack
				}

			}

			while(!operatorStack.isEmpty()){//Empty the operator stack
				double val = Formula.calculateFirst(operatorStack, operandStack);
				operandStack.push(new Token(TokenType.NUM, val));
			}
			res = operandStack.pop().getValue();//get the final value on the operand stack

		}else{
			throw new Exception("Invalid formula. The evaluator will not be able to evaluate this formula");
		}
		return res;
	}

	/**
	 * Pop the first operator and the 2 first operand.
	 * Do the according arithmetic operation
	 * 
	 * @param operatorStack	The list of operator
	 * @param operandStack	The list of operand
	 * @return				The result of the arithmetic operation
	 */
	private static double calculateFirst(TokenStack operatorStack, TokenStack operandStack){
		double val = 0;

		Token operator = operatorStack.pop();
		if(operator.getType() != TokenType.LPA){
			double operand2 = operandStack.pop().getValue();
			double operand1 = operandStack.pop().getValue();

			if(operator.getType() == TokenType.PLU){
				val = operand1 + operand2;
			}else if(operator.getType() == TokenType.MIN){
				val = operand1 - operand2;
			}else if(operator.getType() == TokenType.DIV){
				val = operand1 / operand2;
			}else if(operator.getType() == TokenType.MUL){
				val = operand1 * operand2;
			}else if(operator.getType() == TokenType.MOD){
				val = operand1 % operand2;
			}
		}
		return val;

	}

	/**
	 * Transform a formula string into a Token list
	 * ready to be evaluated
	 * 
	 * @param tokens	Empty ArrayList that will store the token(passed by reference)
	 * @param formula	The formula to tokenize
	 * @return			returns false if the formula is invalid, True otherwise
	 */
	private static boolean tokenize(ArrayList<Token> tokens, String formula){
		boolean expectedOperator = false;
		int openParenthesis = 0;
		char[] formulaChars = formula.toCharArray();
		int i =  1; char character;
		if(formulaChars[0] != '='){//If the formula doesn't starts with an "="
			return false;			//Invalid
		}else{
			while(i < formulaChars.length){//Go through every char
				switch(character = formulaChars[i]){
				case ' ':// ingnores spaces and tabs
				case '	':
					break;
				case '+':
					if(!expectedOperator)
						return false;
					tokens.add( new Token(TokenType.PLU, 1) );
					expectedOperator = false;
					break;
				case '-':
					if(!expectedOperator)
						return false;
					tokens.add( new Token(TokenType.MIN, 1) );
					expectedOperator = false;
					break;
				case '*':
					if(!expectedOperator)
						return false;
					tokens.add( new Token(TokenType.MUL, 2) );
					expectedOperator = false;
					break;
				case '/':
					if(!expectedOperator)
						return false;
					tokens.add( new Token(TokenType.DIV, 2) );
					expectedOperator = false;
					break;
				case '%':
					if(!expectedOperator)
						return false;
					tokens.add( new Token(TokenType.MOD, 2) );
					expectedOperator = false;
					break;
				case '(':
					if(expectedOperator)
						return false;
					tokens.add( new Token(TokenType.LPA, 1) );
					openParenthesis++;
					break;
				case ')':
					if(!expectedOperator || openParenthesis <= 0)
						return false;
					tokens.add( new Token(TokenType.RPA, 1) );
					openParenthesis--;
					break;
				default:// expect and operand(either a cell or a double)
					if(expectedOperator)
						return false;
					String s = String.valueOf(character);
					if( s.matches("[A-Z]") ){
						String tokenColValue = "";
						tokenColValue += s;
						String TokenRowValue = "";
						int c = i+1;
						if(c < formulaChars.length){
							String s2= String.valueOf(formulaChars[c]);
							while( s2.matches("[A-Z]")){//Get the column name
								tokenColValue += s2;
								if(++c < formulaChars.length)
									s2 = String.valueOf(formulaChars[c]);
								else
									break;
							}
							while(  s2.matches("[0-9]") ){//get the row num
								TokenRowValue += s2;
								if(++c < formulaChars.length)
									s2 = String.valueOf(formulaChars[c]);
								else
									break;
							}
							i = c-1;
						}
						tokens.add( new Token(TokenType.CEL, tokenColValue, Integer.parseInt(TokenRowValue)) );

						//Double operand
					}else if( s.matches("[0-9.]") ){
						String tokenValue = "";
						tokenValue += s;
						int c = i+1;
						if(c < formulaChars.length){//get the value of a double typed operand
							String s2= String.valueOf(formulaChars[c]);
							while( s2.matches("[0-9.]")){
								tokenValue += s2;
								if(++c < formulaChars.length)
									s2 = String.valueOf(formulaChars[c]);
								else
									break;
							}
							i = c-1;
						}
						tokens.add( new Token(TokenType.NUM, Double.parseDouble(tokenValue)) );

					}else{//Not a cell nor a double typed operand
						return false;

					}
					expectedOperator = true;

					break;

				}
				i++;
			}
		}
		//if there is at least one unclosed parenthesis
		//or if the formula ends with an operator
		if(openParenthesis > 0 || !expectedOperator)
			return false;

		return true;
	}

}
