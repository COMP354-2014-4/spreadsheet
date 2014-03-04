

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

public class TestSave {
	
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
	 * file>save - Check if the save file is created
	 * 
	 * purpose: test to make sure that the save menu
	 * 			call the backend save
	 * 
	 * dependent on:
	 * 	mnuFile
	 * 	mniSave
	 * 	saveSpreadsheet()
	 */
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
	
	/**
	 * Toolbar>save - Check if the save file is created
	 * 
	 * purpose: test to make sure that the save btn
	 * 			call the backend save
	 * 
	 * dependent on:
	 * 	btnSave
	 * 	saveSpreadsheet()
	 */
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
	
	/**
	 * File>save As - Check if the save file is created
	 * 
	 * purpose: test to make sure that the save as menu
	 * 			call the backend save
	 * 
	 * dependent on:
	 *  mnuFile
	 * 	mniSaveAs
	 * 	saveAsSpreadsheet()
	 */
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
	
	/**
	 * Toolbar>save As - Check if the save file is created
	 * 
	 * purpose: test to make sure that the save as btn
	 * 			call the backend save
	 * 
	 * dependent on:
	 * 	btnSaveAs
	 * 	saveAsSpreadsheet()
	 */
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

}
