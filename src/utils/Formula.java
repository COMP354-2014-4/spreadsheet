package utils;

import java.util.ArrayList;

import utils.TokenStack;
import spreadsheet.Grid;
import spreadsheet.Cell;
import utils.Token.TokenType;

public class Formula {
	//This class should never be instantiated
	private Formula(){}
	
	public static ArrayList<Cell> listReferencedCells(String formula, Grid grid) throws Exception{
		ArrayList<Token> tokens = new ArrayList<Token>();
		ArrayList<Cell> cells = new ArrayList<Cell>(); 
		if(Formula.tokenize(tokens, formula)){
			for(Token tok : tokens){
				if(tok.getType() == TokenType.CEL){
					Cell cell = grid.getCell(tok.getCol(), tok.getRow());
					cells.add(cell);					
				}
			}
			return cells;
		}else{
			throw new Exception("Invalid formula. The evaluator will not be able to evaluate this formula");
		}
	}
	
	public static double evaluateFormula(String formula, Grid grid) throws Exception{
		double res = 0;
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		//parsing the formula into tokens
		if(Formula.tokenize(tokens, formula)){
			//System.out.println("VALID FORMULA");
			TokenStack operatorStack = new TokenStack();
			TokenStack operandStack = new TokenStack();
			
			for(Token tok : tokens){
				if(tok.getType() == TokenType.PLU || tok.getType() == TokenType.MIN || tok.getType() == TokenType.DIV || tok.getType() == TokenType.MUL || tok.getType() == TokenType.MOD ){
					while(!operatorStack.isEmpty() && operatorStack.top().getType() != TokenType.LPA  && operatorStack.top().getPrecedence() >= tok.getPrecedence()){
						double val = Formula.calculateFirst(operatorStack, operandStack);
						operandStack.push(new Token(TokenType.NUM, val));
					}
					operatorStack.push(tok);
				}else if(tok.getType() == TokenType.LPA ){
					operatorStack.push(tok);
				}else if(tok.getType() == TokenType.RPA ){
					while(operatorStack.top().getType() != TokenType.LPA ){
						double val = Formula.calculateFirst(operatorStack, operandStack);
						operandStack.push(new Token(TokenType.NUM, val));
					}
					operatorStack.pop();
				}else{
					if(tok.getType() == TokenType.CEL){
						Cell cell = grid.getCell(tok.getCol(), tok.getRow());
						if(cell != null && cell.isValidValue()){
							tok = new Token(TokenType.NUM, cell.getEvaluatedValue());
						}else{
							throw new Exception("Invalid formula. " + tok.getCol() + tok.getRow() + " is out of bound or has an invalid value");
						}
					}
					operandStack.push(tok);
				}
				
			}
			
			while(!operatorStack.isEmpty()){
				double val = Formula.calculateFirst(operatorStack, operandStack);
				operandStack.push(new Token(TokenType.NUM, val));
			}
			res = operandStack.pop().getValue();
			
		}else{
			throw new Exception("Invalid formula. The evaluator will not be able to evaluate this formula");
		}
		return res;
	}
	
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
				val = operand1 / operand2;												//add div by zero exception
			}else if(operator.getType() == TokenType.MUL){
				val = operand1 * operand2;
			}else if(operator.getType() == TokenType.MOD){
				val = operand1 % operand2;
			}
		}
		return val;
		
	}
	
	private static boolean tokenize(ArrayList<Token> tokens, String formula){
		boolean expectedOperator = false;
		int openParenthesis = 0;
		char[] formulaChars = formula.toCharArray();
		int i =  1; char character;
		if(formulaChars[0] != '='){
			return false;
		}else{
			while(i < formulaChars.length){
				switch(character = formulaChars[i]){
					case ' ':
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
					default:
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
								while( s2.matches("[A-Z]")){
									tokenColValue += s2;
									if(++c < formulaChars.length)
										s2 = String.valueOf(formulaChars[c]);
									else
										break;
								}
								while(  s2.matches("[0-9]") ){
									TokenRowValue += s2;
									if(++c < formulaChars.length)
										s2 = String.valueOf(formulaChars[c]);
									else
										break;
								}
								i = c-1;
							}
							tokens.add( new Token(TokenType.CEL, tokenColValue, Integer.parseInt(TokenRowValue)) );
							//System.out.println(tokenColValue + TokenRowValue + "\tThis is a Cell");
							
						}else if( s.matches("[0-9.]") ){
							String tokenValue = "";
							tokenValue += s;
							int c = i+1;
							if(c < formulaChars.length){
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
							//System.out.println(tokenValue + "\tthis is a 0 to 9 char");
							
						}else{
							return false;
								
						}
						expectedOperator = true;
						
						break;
				
				}
				i++;
			}
		}
		if(openParenthesis > 0 || !expectedOperator)
			return false;
		
		return true;
	}
	
}
