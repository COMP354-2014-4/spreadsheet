package utils;

import java.util.ArrayList;

/**
 * Simple class that converts an
 * Arraylist<Token> into a stack of Token
 * 
 * @author http://www2.lawrence.edu/fast/GREGGJ/CMSC150/Infix/Expressions.html
 */
public class TokenStack{
	private ArrayList<Token> tokens;


	public TokenStack() {
		tokens = new ArrayList<Token>();
	}

	public boolean isEmpty() {
		return tokens.size() == 0;
	}

	public Token top() {
		return tokens.get(tokens.size()-1);
	}

	public void push(Token t) {
		tokens.add(t);
	}

	public Token pop() {
		return tokens.remove(tokens.size()-1);
	}
}