package utils;

import java.util.ArrayList;

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
	
	/** Mutator methods **/
	public void push(Token t) {
		tokens.add(t);
	}
	
	public Token pop() {
		return tokens.remove(tokens.size()-1);
	}
}