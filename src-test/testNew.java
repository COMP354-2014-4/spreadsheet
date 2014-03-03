import static org.junit.Assert.*;
import spreadsheet.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Assert.*;

public class testNew {


	private static Robot _rob;
	
	private static Grid _grid;
	private static SSGUI _ui;
	
	
	/**
	 *  mouse delay + click + release
	 * 
	 */
	public void click(){
		_rob.waitForIdle();
		_rob.mousePress(InputEvent.BUTTON1_MASK);
		_rob.delay(50); // Click one second
		_rob.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	
	/**
	 *  let the robot input string into cells
	 * 
	 */
	public static void inputString(String s){
		char[] charArray = s.toCharArray();
		_rob.waitForIdle();
		for(char c : charArray){
			if (Character.isUpperCase(c) || c == '+' || c == '*' ) {//|| c == '/'
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
			//	case '/':
			//		_rob.keyPress(KeyEvent.VK_3);
			//		_rob.keyRelease(KeyEvent.VK_3);
			//		break;
			
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
	
	public static void waiting(){
		int i = 0;
        while( _ui.clipBoard.equals("0") && i < 100){
        	_rob.delay(10);
                i++;
        }
	}
	
	@Rule
	public ExpectedException exceptionThrown = ExpectedException.none();
	
	/**** Before and After methods ****/
	@BeforeClass
	public static void testSetup() throws Exception{
		// do something before all tests
		_rob = new Robot();
		_grid = new Grid();
		_ui = new SSGUI(_grid);
	}

	@AfterClass
	public static void testCleanup() {
	// do something after all tests
	}

	@Before
	public void testEachSetup() {
	// do something before each test
	    System.out.println("Prepping Test....");
	    _ui.clipBoard="0";
	  }
	  
	@After
	public void testEachCleanup() {
		  // do something after each test
		  System.out.println("Test Completed!");
		  _ui.clipBoard="0";
	}

  
	/**
	 * test: newSpreadsheet1()
	 * robot moves the mouse to click the btnNew from the toolbar
	 */
	
/*	@Test
	public void testNewSpreadSheet1() {
		
		// enter the value in A1 cell, value = 23
		Rectangle rect = _ui.tblGrid.getCellRect(0, 0, true);
        Point pCell = rect.getLocation();
        Point pTable = _ui.tblGrid.getLocationOnScreen();
        Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
        _rob.mouseMove(mMouse.x, mMouse.y);
        click();
        inputString("23");
		
		  
		// use the robot to click the "New" button
        _rob.mouseMove(_ui.btnNew.getLocationOnScreen().x , _ui.btnNew.getLocationOnScreen().y);
		click();
		
		waiting();
		
		// test if the value of A1 is cleared
		Object expected = null;
	    Object result = _ui.tblGrid.getValueAt(0, 0); 
	    assertEquals(expected, result);
	}*/
	
	
	/**
	 * test: newSpreadsheet2()
	 * robot moves the mouse to click the menu "File" (mnuFile) 
	 * then moves the mouse to click menu item "New" (mniNew)
	 */

/*	@Test
	public void testNewSpreadSheet2() {
		
		// enter the value in A1 cell, value = 23
		Rectangle rect = _ui.tblGrid.getCellRect(0, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		inputString("23");
		  
		// use the robot to click the "File" menu
		_rob.mouseMove(_ui.mnuFile.getLocationOnScreen().x , _ui.mnuFile.getLocationOnScreen().y);
		click();
	    
		// use the robot to click the "New" menu item
		_rob.mouseMove(_ui.mniNew.getLocationOnScreen().x , _ui.mniNew.getLocationOnScreen().y);
		click();
		
		waiting();
		
		// test if the value of A1 is cleared
		Object expected = null;
	    Object result = _ui.tblGrid.getValueAt(0, 0); 
	    assertEquals(expected, result);
	} */
	

	
	
	/**
	 * test: can we input numbers from the input box
	 * type a number and then click the "update" button
	 */
	 
/*	@Test
	public void testInputbox1(){
		// select a cell
		Rectangle rect = _ui.tblGrid.getCellRect(2, 2, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// move the robot to the input box and type a number (35)
		_rob.mouseMove(_ui.txtInputBox.getLocationOnScreen().x , _ui.txtInputBox.getLocationOnScreen().y);
		click();	
		inputString("35.0");
		
		// move the robot to click the "update" button
		_rob.mouseMove(_ui.btnUpdate.getLocationOnScreen().x , _ui.btnUpdate.getLocationOnScreen().y);
		click();
		
		waiting();
		// test if the value of the cell equals the number we typed
		String expected = "35.0";
	    String result = _ui.tblGrid.getValueAt(2, 2).toString(); 
	    assertEquals(expected, result);
	}  
	*/
	
	/**
	 * test: can we input numbers from the input box
	 * type a number and then click "enter" key
	 */
	 
/*	@Test
	public void testInputbox2(){
		// select a cell
		Rectangle rect = _ui.tblGrid.getCellRect(3, 2, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		
		// move the robot to the input box and type a number (35)
		_rob.mouseMove(_ui.txtInputBox.getLocationOnScreen().x , _ui.txtInputBox.getLocationOnScreen().y);
		click();	
		inputString("35.0");
		
		// and then hit the "enter" key
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		waiting();
				
		// test if the value of the cell equals the number we typed
		String expected = "35.0";
	    String result = _ui.tblGrid.getValueAt(3, 2).toString(); 
	    assertEquals(expected, result);
		
	}  */
	
	
	
	/** 
	 * test: can we copy number from the toolbar
	 * int value
	 */
/*	@Test
	public void testNumberCopyToolbar1(){
		
		// enter the value in A1 cell, value = 23
		Rectangle rect = _ui.tblGrid.getCellRect(1, 1, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		inputString("23");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		
		rect = _ui.tblGrid.getCellRect(1, 1, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// use the robot to click the "Copy" button
		_rob.mouseMove(_ui.btnCopy.getLocationOnScreen().x , _ui.btnCopy.getLocationOnScreen().y);
		click();	
			
		waiting();  
				
		// to check if the value of the clipboard = the value we input
		String expected = "23";
	    String result = _ui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}*/
	
	/**
	 * test: can we copy number from the toolbar
	 * double value
	 */
/*	@Test
	public void testNumberCopyToolbar2(){
			
		// enter the value in A1 cell, value = 23.65
		Rectangle rect = _ui.tblGrid.getCellRect(2, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
			
		inputString("23.65");

		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
			
		rect = _ui.tblGrid.getCellRect(2, 0, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
			
		// use the robot to click the "Copy" button
		_rob.mouseMove(_ui.btnCopy.getLocationOnScreen().x , _ui.btnCopy.getLocationOnScreen().y);
		click();	

		waiting();  
			
		// to check if the value of the clipboard = the value we input
		String expected = "23.65";
	    String result = _ui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}*/
		 
	/**
	 * test: can we copy number from the menu
	 * int value
	 */
/*	 
	@Test
	public void testNumberCopyMenu1(){

		// enter the value in A1 cell, value = 123
		Rectangle rect = _ui.tblGrid.getCellRect(3, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		inputString("123");

		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		
		rect = _ui.tblGrid.getCellRect(3, 0, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// use the robot to click the "Copy" button
		_rob.mouseMove(_ui.mnuEdit.getLocationOnScreen().x , _ui.mnuEdit.getLocationOnScreen().y);
		click();	
		_rob.mouseMove(_ui.mniCopy.getLocationOnScreen().x , _ui.mniCopy.getLocationOnScreen().y);
		click();
			
		waiting();  
				
		// to check if the value of the clipboard = the value we input
		String expected = "123";
	    String result = _ui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}*/
	
	
	/**
	 * test: can we copy number from the menu
	 * double value
	 */
	 
/*	 
	@Test
	public void testNumberCopyMenu2(){

		// enter the value in A1 cell, value = 12.3
		Rectangle rect = _ui.tblGrid.getCellRect(4, 1, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		inputString("12.3");
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		
		rect = _ui.tblGrid.getCellRect(4, 1, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// use the robot to click the "Copy" button
		_rob.mouseMove(_ui.mnuEdit.getLocationOnScreen().x , _ui.mnuEdit.getLocationOnScreen().y);
		click();	
		_rob.mouseMove(_ui.mniCopy.getLocationOnScreen().x , _ui.mniCopy.getLocationOnScreen().y);
		click();
			
		waiting();  
				
		// to check if the value of the clipboard = the value we input
		String expected = "12.3";
	    String result = _ui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}*/
	
	
	
	/**
	 * test: can we copy formula from the menu
	 * double value
	 */
	 

	@Test
	public void testNumberCopyFormula(){

		// input number 12 into cell A1 
		Rectangle rect = _ui.tblGrid.getCellRect(0, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = _ui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("10");
		_rob.waitForIdle();
		
		//input number 23 into cell B1
		rect = _ui.tblGrid.getCellRect(0, 1, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("20");
		_rob.waitForIdle();
		
		rect = _ui.tblGrid.getCellRect(0, 2, true);
		pCell = rect.getLocation();
		pTable = _ui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		_rob.mouseMove(mMouse.x, mMouse.y);
		_rob.waitForIdle();
		click();
		
		inputString("=A1+B1+30/2"); // = 10+ 20+ 15 = 45
		_rob.waitForIdle();
		
		_rob.keyPress(KeyEvent.VK_ENTER);
		_rob.keyRelease(KeyEvent.VK_ENTER);
		_rob.waitForIdle();
		
		waiting();
		
		String expected = "45.0";
		String result = _ui.tblGrid.getValueAt(0, 2).toString();
		
		assertEquals( expected, result);
	}
}