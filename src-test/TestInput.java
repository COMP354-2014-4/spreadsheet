

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

public class TestInput {
	
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

	@After
	public void testEachCleanup() {
		// do something after each test
		System.out.println("Test Completed!");
		System.out.println("_________________________________________________________");
	}
	
	//**********
	// TESTS
	//**********
	
	/**
	 * Input directly in a cell - Check if the value is entered in the cell
	 * 
	 * purpose: Test to make sure that it is possible to input
	 * 			some values inside of a cell by simply clicking on it
	 * 			and that the value gets registered in the backend
	 * 
	 * dependent on:
	 * 	tableChanged()
	 */
	@Test
	public void testInputFromGrid(){
		System.out.println("Test Input from grid");
		
		Rectangle rect = _ui.tblGrid.getCellRect(0, 4, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("12.0");
		_rob.waitForIdle();
		System.out.println("Input a value in E1 works");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while( ( _ui.tblGrid.grid.getCell("E", 1).getValue().isEmpty() || _ui.tblGrid.getValueAt(0, 4) == null ) && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		System.out.println("Backend value: " + _ui.tblGrid.grid.getCell("E", 1).getValue() + "  ||  Frontend value: " + _ui.tblGrid.getValueAt(0, 4));
			
		assertTrue(_ui.tblGrid.grid.getCell("E", 1).getValue().equals(_ui.tblGrid.getValueAt(0, 4).toString()));
		
		
	}
	
	/**
	 * Input a circular formula - Check if a circular formula display the right error message
	 * 
	 * purpose: Test to make sure that the user gets a feedback on the error
	 * 
	 * dependent on:
	 * 	tableChanged()
	 *  displayMessage()
	 */
	@Test
	public void testInputCircularFormula(){
		System.out.println("Test Input Circular formula");
		
		Rectangle rect = _ui.tblGrid.getCellRect(2, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("=D4");
		_rob.waitForIdle();
		System.out.println("Input a value in D3 works");
		
		
		rect = _ui.tblGrid.getCellRect(3, 3, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("=D3");
		_rob.waitForIdle();
		System.out.println("Input a value in D4 works");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while( _ui.txtMessageBox.getText().isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		assertTrue(_ui.txtMessageBox.getText().equals("Circular formula. The evaluator will not be able to evaluate this formula"));
		
	
	}
	
	/**
	 * Input a string - Check if a string display the right error message
	 * 
	 * purpose: Test to make sure that the user gets a feedback on the error
	 * 
	 * dependent on:
	 * 	tableChanged()
	 *  displayMessage()
	 */
	@Test
	public void testInputStrings(){
		System.out.println("Test Input String in the grid");
		
		Rectangle rect = _ui.tblGrid.getCellRect(4, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("asd");
		_rob.waitForIdle();
		System.out.println("Input a value in D5 works");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while( _ui.txtMessageBox.getText().isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		assertTrue(_ui.txtMessageBox.getText().equals("That is not valid input, please type either a formula or a number."));
		
	
	}
	
	/**
	 * Input an invalid formula - Check if the right error message is being displayed
	 * 
	 * purpose: Test to make sure that the user gets a meaningfull error message when an error
	 * 			occurs
	 * 
	 * dependent on:
	 * 	tableChanged()
	 */
	@Test
	public void testInvalidFormula() {
		System.out.println("Test Input Invalid Formula in the grid");
		
		Rectangle rect = _ui.tblGrid.getCellRect(0, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("==12A+");
		_rob.delay(1000);
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
	
		int i = 0;
		while( _ui.txtMessageBox.getText().isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		assertTrue(_ui.txtMessageBox.getText().equals("That is not valid input, please type either a formula or a number."));
		
	}
	
	/**
	 * Input a complex formula - Check if a complex formula display the right evaluated value
	 * 
	 * purpose: Test to make sure that the user gets the evaluated value from a complex formula
	 * 			is displayed to the user. Thus testing the link between the frontend/backend
	 * 
	 * dependent on:
	 * 	tableChanged()
	 */
	@Test
	public void testInputComplexFormula(){
		System.out.println("Test Input Formula in the grid");
		
		Rectangle rect = _ui.tblGrid.getCellRect(5, 3, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("36");
		_rob.waitForIdle();
		System.out.println("Input a value in D6 works");
		
		rect = _ui.tblGrid.getCellRect(6, 3, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("42");
		_rob.waitForIdle();
		System.out.println("Input a value in D7 works");
		
		rect = _ui.tblGrid.getCellRect(7, 3, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("=D6+D7+44/2");
		_rob.waitForIdle();
		System.out.println("Input a value in D8 works");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while( ( _ui.tblGrid.grid.getCell("D", 8).getValue().isEmpty() || _ui.tblGrid.getValueAt(7, 3) == null ) && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		double backendVal = _ui.tblGrid.grid.getCell("D", 8).getEvaluatedValue();
		
		System.out.println("Backend value: " + backendVal + "  ||  Frontend value: " + _ui.tblGrid.getValueAt(7, 3));
		
		System.out.println("Backend value: " + String.valueOf(backendVal) + "  ||  Frontend value: " + _ui.tblGrid.getValueAt(7, 3));
		
		assertTrue( backendVal == 100 && String.valueOf(backendVal).equals( _ui.tblGrid.getValueAt(7, 3).toString()) );
		
	
	}
	
	
	/**
	 * Input and update a formula - Check if a complex formula display the right evaluated value after one of it's component has been edited
	 * 
	 * purpose: Test to make sure that the user gets the evaluated value from a complex formula
	 * 			is displayed to the user even after editing one of its component cell.
	 * 			Thus testing the link between the frontend/backend
	 * 
	 * dependent on:
	 * 	tableChanged()
	 */
	@Test
	public void testEditFormula(){
		System.out.println("Test Input Formula in the grid");
		
		Rectangle rect = _ui.tblGrid.getCellRect(1, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("20");
		_rob.waitForIdle();
		System.out.println("Input a value in A2 works");
		
		rect = _ui.tblGrid.getCellRect(2, 0, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("42");
		_rob.waitForIdle();
		System.out.println("Input a value in A3 works");
		
		rect = _ui.tblGrid.getCellRect(3, 0, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("=A2+A3+44/2");
		_rob.waitForIdle();
		System.out.println("Input a value in A4 works");
		
		rect = _ui.tblGrid.getCellRect(1, 0, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		_rob.keyPress(KeyEvent.VK_DELETE);
		_rob.keyRelease(KeyEvent.VK_DELETE);
		_rob.waitForIdle();
		inputString("36");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while( ( _ui.tblGrid.grid.getCell("A", 4).getValue().isEmpty() || !_ui.tblGrid.getValueAt(3, 0).toString().equals("100.0") ) && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		double backendVal = _ui.tblGrid.grid.getCell("A", 4).getEvaluatedValue();
		
		System.out.println("Backend value: " + backendVal + "  ||  Frontend value: " + _ui.tblGrid.getValueAt(3, 0));
		
		System.out.println("Backend value: " + String.valueOf(backendVal) + "  ||  Frontend value: " + _ui.tblGrid.getValueAt(3, 0));
		String backendValS = String.valueOf(backendVal);
		boolean assertVal = ( backendVal == 100 && backendValS.equals( _ui.tblGrid.getValueAt(3, 0).toString()) );
		
		assertTrue( assertVal );
		
	
	}

}
