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


	private static Robot robot;
	
	private static Grid grid;
	private static SSGUI gui;
	
	
	/**
	 *  mouse delay + click + release
	 * 
	 */
	public void click(){
	//	robot.delay(500);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(500);
	    robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	
	/**
	 *  let the robot input string into cells
	 * 
	 */
	public static void inputString(String s){
        char[] charArray = s.toCharArray();
        robot.waitForIdle();
        for(char c : charArray){
                if (Character.isUpperCase(c)) {
                	robot.keyPress(KeyEvent.VK_SHIFT);
        }
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));

        if (Character.isUpperCase(c)) {
        	robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        robot.waitForIdle();
        }
       
	}
	
	public static void waiting(){
		int i = 0;
        while( gui.clipBoard.equals("0") && i < 100){
                robot.delay(10);
                i++;
        }
	}
	
	@Rule
	public ExpectedException exceptionThrown = ExpectedException.none();
	
	/**** Before and After methods ****/
	@BeforeClass
	public static void testSetup() throws Exception{
		// do something before all tests
		robot = new Robot();
		grid = new Grid();
		gui = new SSGUI(grid);
	}

	@AfterClass
	public static void testCleanup() {
	// do something after all tests
	}

	@Before
	public void testEachSetup() {
	// do something before each test
	    System.out.println("Prepping Test....");
	    gui.clipBoard="0";
	  }
	  
	@After
	public void testEachCleanup() {
		  // do something after each test
		  System.out.println("Test Completed!");
		  gui.clipBoard="0";
	}

  
	/**
	 * testing newSpreadsheet1()
	 * robot moves the mouse to click the btnNew from the toolbar
	 */
	
	@Test
	public void testNewSpreadSheet1() {
		
		// enter the value in A1 cell, value = 23
		Rectangle rect = gui.tblGrid.getCellRect(0, 0, true);
        Point pCell = rect.getLocation();
        Point pTable = gui.tblGrid.getLocationOnScreen();
        Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
        robot.mouseMove(mMouse.x, mMouse.y);
        click();
        inputString("23");
		
		  
		// use the robot to click the "New" button
		robot.mouseMove(gui.btnNew.getLocationOnScreen().x , gui.btnNew.getLocationOnScreen().y);
		click();
		
		waiting();
		
		// test if the value of A1 is cleared
		Object expected = null;
	    Object result = gui.tblGrid.getValueAt(0, 0); 
	    assertEquals(expected, result);
	}
	
	
	/**
	 * testing newSpreadsheet2()
	 * robot moves the mouse to click the menu "File" (mnuFile) 
	 * then moves the mouse to click menu item "New" (mniNew)
	 */

	@Test
	public void testNewSpreadSheet2() {
		
		// enter the value in A1 cell, value = 23
		Rectangle rect = gui.tblGrid.getCellRect(0, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = gui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		inputString("23");
		  
		// use the robot to click the "File" menu
		robot.mouseMove(gui.mnuFile.getLocationOnScreen().x , gui.mnuFile.getLocationOnScreen().y);
		click();
	    
		// use the robot to click the "New" menu item
	    robot.mouseMove(gui.mniNew.getLocationOnScreen().x , gui.mniNew.getLocationOnScreen().y);
		click();
		
		waiting();
		
		// test if the value of A1 is cleared
		Object expected = null;
	    Object result = gui.tblGrid.getValueAt(0, 0); 
	    assertEquals(expected, result);
	} 
	
	/** 
	 * testing; can we copy and paste from the toolbar
	 * int value
	 */
	@Test
	public void testNumberCopyToolbar1(){
		
		// enter the value in A1 cell, value = 23
		Rectangle rect = gui.tblGrid.getCellRect(1, 1, true);
		Point pCell = rect.getLocation();
		Point pTable = gui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		
		inputString("23");
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		rect = gui.tblGrid.getCellRect(1, 1, true);
		pCell = rect.getLocation();
		pTable = gui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// use the robot to click the "Copy" button
		robot.mouseMove(gui.btnCopy.getLocationOnScreen().x , gui.btnCopy.getLocationOnScreen().y);
		click();	
			
		waiting();  
				
		// to check if the value of the clipboard = the value we input
		String expected = "23";
	    String result = gui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}
	
	/**
	 * testing; can we copy and paste from the toolbar
	 * double value
	 */
	@Test
	public void testNumberCopyToolbar2(){
			
		// enter the value in A1 cell, value = 23.65
		Rectangle rect = gui.tblGrid.getCellRect(2, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = gui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
			
		inputString("23.65");

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
			
		rect = gui.tblGrid.getCellRect(2, 0, true);
		pCell = rect.getLocation();
		pTable = gui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
			
		// use the robot to click the "Copy" button
		robot.mouseMove(gui.btnCopy.getLocationOnScreen().x , gui.btnCopy.getLocationOnScreen().y);
		click();	

		waiting();  
			
		// to check if the value of the clipboard = the value we input
		String expected = "23.65";
	    String result = gui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}
		 
	/**
	 * testing; can we copy and paste from the menu
	 * int value
	 */
	 
	@Test
	public void testNumberCopyMenu1(){

		// enter the value in A1 cell, value = 123
		Rectangle rect = gui.tblGrid.getCellRect(3, 0, true);
		Point pCell = rect.getLocation();
		Point pTable = gui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		
		inputString("123");

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		rect = gui.tblGrid.getCellRect(3, 0, true);
		pCell = rect.getLocation();
		pTable = gui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// use the robot to click the "Copy" button
		robot.mouseMove(gui.mnuEdit.getLocationOnScreen().x , gui.mnuEdit.getLocationOnScreen().y);
		click();	
		robot.mouseMove(gui.mniCopy.getLocationOnScreen().x , gui.mniCopy.getLocationOnScreen().y);
		click();
			
		waiting();  
				
		// to check if the value of the clipboard = the value we input
		String expected = "123";
	    String result = gui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}
	
	
	/**
	 * testing; can we copy and paste from the menu
	 * double value
	 */
	 
	 
	@Test
	public void testNumberCopyMenu2(){

		// enter the value in A1 cell, value = 12.3
		Rectangle rect = gui.tblGrid.getCellRect(4, 1, true);
		Point pCell = rect.getLocation();
		Point pTable = gui.tblGrid.getLocationOnScreen();
		Point mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		
		inputString("12.3");
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		rect = gui.tblGrid.getCellRect(4, 1, true);
		pCell = rect.getLocation();
		pTable = gui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x, pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();
		
		// use the robot to click the "Copy" button
		robot.mouseMove(gui.mnuEdit.getLocationOnScreen().x , gui.mnuEdit.getLocationOnScreen().y);
		click();	
		robot.mouseMove(gui.mniCopy.getLocationOnScreen().x , gui.mniCopy.getLocationOnScreen().y);
		click();
			
		waiting();  
				
		// to check if the value of the clipboard = the value we input
		String expected = "12.3";
	    String result = gui.clipBoard; 
	    System.out.println(result);
	    assertEquals(expected, result);
	}
	
	
	/**
	 * testing; can we input numbers into the cell
	 * 
	 */
	 /*
	@Test
	public void testNumberInput(){
		
		robot.mouseMove(350 , 120);
		click();
	}  */
	
	
}