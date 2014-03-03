import static org.junit.Assert.*;
import spreadsheet.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Jayanti Rani
 *
 */
public class testInvalidFormula {

	// variables
	private static Robot robot;
	private static SSGUI gui;
	private static Grid grid;
	private static Rectangle rect;
	private Point pCell, pTable, mMouse;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		robot = new Robot();
		grid = new Grid ();
		gui = new SSGUI(grid);
	}
	
	/**
	 * method to simulate mouse click
	 */
	public static void click () {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(2000); // Click 2 seconds
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gui.displayMessage("");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	 /**
	  * method to input a file name using robot
	  * @param s   String name of the file
	  */
	public static void inputString(String s){
		char[] charArray = s.toCharArray();
		robot.waitForIdle();
		for(char c : charArray){
			if (Character.isUpperCase(c) || c == '+' || c == '*' || c == '/') {
	            robot.keyPress(KeyEvent.VK_SHIFT);
	        }
			
			
			
			switch(c){
				case '+':
					robot.keyPress(KeyEvent.VK_EQUALS);
					robot.keyRelease(KeyEvent.VK_EQUALS);
					break;
				case '-':
					robot.keyPress(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_MINUS);
					break;
				case '*':
					robot.keyPress(KeyEvent.VK_8);
					robot.keyRelease(KeyEvent.VK_8);
					break;
				case '/':
					robot.keyPress(KeyEvent.VK_3);
					robot.keyRelease(KeyEvent.VK_3);
					break;
			
				default:
					char C = Character.toUpperCase(c);
					robot.keyPress(C);
					robot.keyRelease(C);
			
			}
			
	
	        if (Character.isUpperCase(c) || c == '+' || c == '*' || c == '/') {
	        	robot.keyRelease(KeyEvent.VK_SHIFT);
	        }
	        robot.waitForIdle();
		}
      
	}
	
	/**
	 * method to select a given cell
	 */
	
	public void SelectCellAndWrite (int a, int b){
		
		rect= gui.tblGrid.getCellRect(a,b,true);
		pCell = rect.getLocation();
		pTable = gui.tblGrid.getLocationOnScreen();
		mMouse = new Point(pCell.x + 5 + pTable.x , pCell.y + 5 + pTable.y);
		robot.mouseMove(mMouse.x, mMouse.y);
		click();

	}
	
	/**
	 *  test for invalid input in formula, should display an error message
	 */

	@Test
	public void testFormula() {
		SelectCellAndWrite(2,4);
		inputString("==12A+");
		robot.delay(1000);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(1000);
	
		int i = 0;
		while( gui.txtMessageBox.getText().isEmpty() && i < 100){
			robot.delay(10);	
			i++;
		}
		
		assertTrue(gui.txtMessageBox.getText().equals("That is not valid input, please type either a formula or a number."));
		
	}
	
	/**
	 * test evaluation of formula, no error should be displayed
	 */
	@Test
	public void testFormula1() {
		SelectCellAndWrite(3,4);
		inputString("=12*3");
		robot.delay(1000);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(1000);
	}

}
