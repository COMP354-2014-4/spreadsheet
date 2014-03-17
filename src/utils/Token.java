package utils;

/**
 * Simple class that hold the information
 * of the various operator and operands that can
 * be contained inside of a Formula 
 * 
 * @author Comp354 Team 3
 */
public class Token {
	public enum TokenType{PLU, MIN, MUL, DIV, MOD, LPA, RPA, NUM, CEL,SUM}

	private TokenType _type;
	private double _value;
	private String _col;
	private int _row;
	private int _precedence;
	private String _params;

	public Token(TokenType type, int precedence,String params){
		_type = type;
		_precedence = precedence;
		_params = params;
	}
	
	public Token(TokenType type, int precedence){
		_type = type;
		_precedence = precedence;
	}

	public Token(TokenType type, double value){
		_type = type;
		_value = value;
	}

	public Token(TokenType type, String col, int row){
		_type = type;
		_col = col;
		_row = row;
	}

	public TokenType getType(){return _type;}
	public double getValue(){return _value;}
	public int getRow(){return _row;}
	public String getCol(){return _col;}
	public int getPrecedence(){return _precedence;}
	public String getParams(){return _params;}

}
