

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

public class SSGUITestMAXIME {
	
private static Robot _rob;
private static Grid _grid;
private static SSGUI _ui; 

	//string input
	public static void inputString(String s){
		char[] charArray = s.toCharArray();
		_rob.waitForIdle();
		for(char c : charArray){
			if (Character.isUpperCase(c) || c == '+' || c == '*' || c == '/') {
	            _rob.keyPress(KeyEvent.VK_SHIFT);
	        }
			
			
			
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

	//Robot click
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

	@Test
	public void testSaveSpreadsheetMenu() {
		System.out.println(" SaveSpreadsheet from Menu");
		Point mMouse = _ui.mnuFile.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		System.out.println("\tClick on file works");
		
		_rob.delay(100);
		mMouse = _ui.mniSave.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		System.out.println("\tClick on save works");
		inputString("TestSaveMenu");
		_rob.delay(100);
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while(_ui.strFileLocation.isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		System.out.println("\tEntering the file name works: " + _ui.strFileLocation + ".sav");
		File saveFile = new File(_ui.strFileLocation+".sav");
		
		boolean assertValue = saveFile.exists();
		if(assertValue) saveFile.delete();
		assertTrue(assertValue);
	}
	
	@Test
	public void testSaveSpreadsheetTB() {
		System.out.println(" SaveSpreadsheet from ToolBar"+_ui.strFileLocation);
		Point mMouse = _ui.btnSave.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		System.out.println("\tClick on save btn works");
		inputString("TestSaveTB");
		_rob.delay(100);
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while(_ui.strFileLocation.isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		System.out.println("\tEntering the file name works: " + _ui.strFileLocation + ".sav");
		File saveFile = new File(_ui.strFileLocation+".sav");
		
		boolean assertValue = saveFile.exists();
		if(assertValue) saveFile.delete();
		assertTrue(assertValue);
	}

	@Test
	public void testSaveAsSpreadsheetMenu() {
		System.out.println(" SaveAsSpreadsheet from Menu");
		Point mMouse = _ui.mnuFile.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		System.out.println("\tClick on file works");
		
		_rob.delay(100);
		mMouse = _ui.mniSaveAs.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		System.out.println("\tClick on saveAs works");
		inputString("TestSaveAsMenu");
		_rob.delay(100);
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while(_ui.strFileLocation.isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		System.out.println("\tEntering the file name works: " + _ui.strFileLocation + ".sav");
		File saveFile = new File(_ui.strFileLocation+".sav");
		
		boolean assertValue = saveFile.exists();
		if(assertValue) saveFile.delete();
		assertTrue(assertValue);
	}
	
	@Test
	public void testSaveAsSpreadsheetTB() {
		System.out.println(" SaveAsSpreadsheet from Toolbar");
		Point mMouse = _ui.btnSaveAs.getLocationOnScreen();
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		System.out.println("\tClick on btn SaveAs works");
		
		inputString("TestSaveAsTB");
		_rob.delay(100);
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		int i = 0;
		while(_ui.strFileLocation.isEmpty() && i < 100){
			_rob.delay(10);	
			i++;
		}
		
		System.out.println("\tEntering the file name works: " + _ui.strFileLocation + ".sav");
		File saveFile = new File(_ui.strFileLocation+".sav");
		
		boolean assertValue = saveFile.exists();
		if(assertValue) saveFile.delete();
		assertTrue(assertValue);
	}

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

}
