package iteration2;


import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import spreadsheet.Grid;
import spreadsheet.SSGUI;

/**
 * 
 * @author Unknown Iteration 2 Author & Justin Dupuis
 *
 */
public class TestCut {
	
private static Robot _rob;	//The robot used to test the GUI
private static Grid _grid;	//The grid on which we test
private static SSGUI _ui; 	//The UI

	/**
	 * Function that inputs a string with the virtual keyboard
	 * Robot crashes when you try to input from the numpad, thus
	 * for the test to run successfully, 
	 * you need to set your keyboard to "EN CANADA"
	 * 
	 * @param s the string to input with robot
	 */
	public static void inputString(String s){
		char[] charArray = s.toCharArray();
		_rob.waitForIdle();
		for(char c : charArray){
			if (Character.isUpperCase(c) || c == '+' || c == '*' || c == '/') {
	            _rob.keyPress(KeyEvent.VK_SHIFT);
	        }
			
			
			//manages the numpad keys
			switch(c){
				case '+':
					_rob.keyPress(KeyEvent.VK_EQUALS);
					_rob.keyRelease(KeyEvent.VK_EQUALS);
					break;
				case '-':
					_rob.keyPress(KeyEvent.VK_MINUS);
					_rob.keyRelease(KeyEvent.VK_MINUS);
					break;
				case '*':
					_rob.keyPress(KeyEvent.VK_8);
					_rob.keyRelease(KeyEvent.VK_8);
					break;
				case '/':
					_rob.keyPress(KeyEvent.VK_3);
					_rob.keyRelease(KeyEvent.VK_3);
					break;
			
				default:
					char C = Character.toUpperCase(c);
					_rob.keyPress(C);
					_rob.keyRelease(C);
			
			}
			
	
	        if (Character.isUpperCase(c) || c == '+' || c == '*' || c == '/') {
	        	_rob.keyRelease(KeyEvent.VK_SHIFT);
	        }
	        _rob.waitForIdle();
		}
		
	}

	/**
	 * Default function to simulate a click
	 */
	public static void click(){
		_rob.waitForIdle();
		_rob.mousePress(InputEvent.BUTTON1_MASK);
		_rob.delay(50); // Click one second
		_rob.mouseRelease(InputEvent.BUTTON1_MASK);
		
	}
	
	
	/**** Before and After methods ****/
	
	@BeforeClass
	public static void testSetup() {
		//Create the test ui		
		_grid = new Grid();
		_ui = new SSGUI(_grid);
		
		// Create the robot that will be used in the test
		try{
			_rob = new Robot();
			
		}catch(Exception e){
						
		}
	}

	@AfterClass
	public static void testCleanup() {
		// do something after all tests
	}

	@Before
	public void testEachSetup() {
		// do something before each test
		System.out.print("Prepping Test");
		//make sure that the save test starts with an empty file location and an empty clipboard
		_ui.strFileLocation = "";
		_ui.clipBoard = "0";
		
		_ui.displayMessage("");
	}

	
	
	//**********
	// TESTS
	//**********
	
	/**
	 * Edit>cut - Check if the clipboard gets the right value and if the cell is emptied
	 * 
	 * purpose: Test to make sure that the cut menu actually
	 * 			cut the cell
	 * 
	 * dependent on:
	 *  mnuEdit
	 * 	mniCut
	 * 	cut()
	 */
	@Test
	public void testCutMenu() {
		System.out.println("Test Cut from menu");
		
		Rectangle rect = _ui.tblGrid.getCellRect(0, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("12.0");
		_rob.waitForIdle();
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		System.out.println("Input a value in D1 works");
		
		_rob.delay(500);//delay to avoid double click.....
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		mMouse = _ui.mnuEdit.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		_rob.delay(100);
		System.out.println("Clicking on edit menu works");
		
		mMouse = _ui.mniCut.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		System.out.println("Clicking on cut works");
		
		int i = 0;
		while( ( _ui.clipBoard.equals("0") || !_ui.tblGrid.getValueAt(0, 3).toString().equals("") ) && i < 100){
			_rob.delay(10);	
			//System.out.println(i+"-- Clipboard: "+_ui.clipBoard+"\tgridValue: "+_ui.tblGrid.getValueAt(0, 3));
			i++;
		}
		
		assertTrue(_ui.clipBoard.equals("12.0") && _ui.tblGrid.getValueAt(0, 3).toString().equals(""));
	}
	

	/**
	 * Toolbar>cut - Check if the clipboard gets the right value and if the cell is emptied
	 * 
	 * purpose: Test to make sure that the cut toolbar actually
	 * 			cut the cell
	 * 
	 * dependent on:
	 * 	btnCut
	 * 	cut()
	 */
	@Test
	public void testCutTB() {
		System.out.println("Test Cut from toolbar");
		
		Rectangle rect = _ui.tblGrid.getCellRect(1, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("13.0");
		_rob.waitForIdle();
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		System.out.println("Input a value in D2 works");
		
		_rob.delay(500);//delay to avoid double click.....
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		mMouse = _ui.btnCut.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		System.out.println("Clicking on Cut btn works");
		
		int i = 0;
		while( ( _ui.clipBoard.equals("0") || !_ui.tblGrid.getValueAt(1, 3).toString().equals("") ) && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		assertTrue(_ui.clipBoard.equals("13.0") && _ui.tblGrid.getValueAt(1, 3).toString().equals("") );
	}
	
	
	/**
	 * Shortcut: CTRL+X - Check if the clipboard receives the correct value and the cell is emptied
	 * 
	 * purpose: Ensures that the Cut shortcut activates the cut functionality
	 */
	@Test
	public void testCutShortcut() {
		System.out.println("Test Cut from toolbar");
		
		Rectangle rect = _ui.tblGrid.getCellRect(1, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("13.0");
		_rob.waitForIdle();
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		System.out.println("Input a value in D2 works");
		
		_rob.delay(500);//delay to avoid double click.....
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		_rob.waitForIdle();
		_rob.keyPress(KeyEvent.VK_CONTROL);
		_rob.keyPress(KeyEvent.VK_X);
		_rob.keyRelease(KeyEvent.VK_X);
		_rob.keyRelease(KeyEvent.VK_CONTROL);

		System.out.println("CTRL+X Works");
		
		int i = 0;
		while( ( _ui.clipBoard.equals("0") || !_ui.tblGrid.getValueAt(1, 3).toString().equals("") ) && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		assertTrue(_ui.clipBoard.equals("13.0") && _ui.tblGrid.getValueAt(1, 3).toString().equals("") );
	}
	

	/**
	 * cut() - Check if the clipboard gets the formula and not the evaluated value
	 * 
	 * purpose: Test to make sure that the cut takes the right value
	 * 
	 * dependent on:
	 * 	btnCut
	 * 	cut()
	 */
	@Test
	public void testCutFormula() {
		System.out.println("Test Cut from toolbar");
		
		Rectangle rect = _ui.tblGrid.getCellRect(1, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("=A1+3");
		_rob.waitForIdle();
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		System.out.println("Input a value in D2 works");
		
		_rob.delay(500);//delay to avoid double click.....
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		mMouse = _ui.btnCut.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		System.out.println("Clicking on Cut btn works");
		
		int i = 0;
		while( ( _ui.clipBoard.equals("0") || !_ui.tblGrid.getValueAt(1, 3).toString().equals("") ) && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		assertTrue(_ui.clipBoard.equals("=A1+3"));
	}

}
