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
public class testPaste {

	// variables
		private static Robot robot;
		private static SSGUI gui;
		private static Grid grid;
		private static Rectangle rect;
		private Point pCell, pTable, mMouse;
		
		/**
		 * method for simulating mouse click
		 */
		public static void click () {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.delay(1000); // Click 2 seconds
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
		/**** Before and After methods ****/
		
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		robot = new Robot();
		grid = new Grid ();
		gui = new SSGUI(grid);
	}

	
	@Before
	public void setUp(){
		gui.clipBoard = "0";
		  System.out.println("Prepping Test....");
	}

	
	@After
	public void tearDown() {
		gui.clipBoard = "0";
		System.out.println("Test Completed!");
	}
	

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
	
	 /*************************/
	  /**** Testing Methods ****/
	  /*************************/
	
	/**
	 *  test to check if Paste is working from the Toolbar
	 */
	@Test
	public void testPaste1() {
		SelectCellAndWrite(0,3);
		gui.clipBoard = "123";
		robot.mouseMove(gui.btnPaste.getLocationOnScreen().x , gui.btnPaste.getLocationOnScreen().y);
		 click(); 
		 robot.delay(100);
		 double input = 123.0;
		 int i = 0;
		 while( gui.tblGrid.getValueAt(0, 3) == null && i < 100){
			robot.delay(10);	
			i++;
		 }
		 
		 assertEquals(input, gui.tblGrid.getValueAt(0, 3));
	}
	
	/**
	 *  test to check if Paste is working from the Edit menu
	 */

	@Test
	public void testPaste2() {
		SelectCellAndWrite(1,3);
		gui.clipBoard = "99";
		
		robot.mouseMove(gui.mnuEdit.getLocationOnScreen().x , gui.mnuEdit.getLocationOnScreen().y);
		click();
		robot.mouseMove(gui.mniPaste.getLocationOnScreen().x , gui.mniPaste.getLocationOnScreen().y);
		 click(); 
		 robot.delay(100);
		 double input = 99.0;
		 int i = 0;
		 while( gui.tblGrid.getValueAt(1, 3) == null && i < 100){
			robot.delay(10);	
			i++;
		 }
		 
		 assertEquals(input, gui.tblGrid.getValueAt(1, 3));
	}
	
	/**
	 *  test to check whether Paste works with an empty clipboard 
	 *  Paste from toolbar
	 */
	@Test
	public void testPaste3() {
		SelectCellAndWrite(2,3);
		robot.mouseMove(gui.btnPaste.getLocationOnScreen().x , gui.btnPaste.getLocationOnScreen().y);
		click(); 
		robot.delay(100);
	}
	
	/**
	 *  test to check whether Paste works with an empty clipboard 
	 *  Paste from Edit menu
	 */
	@Test
	public void testPaste4() {
		SelectCellAndWrite(5,3);
		robot.mouseMove(gui.mnuEdit.getLocationOnScreen().x , gui.mnuEdit.getLocationOnScreen().y);
		click();
		robot.mouseMove(gui.mniPaste.getLocationOnScreen().x , gui.mniPaste.getLocationOnScreen().y);
		 click(); 
		 robot.delay(100);
		 boolean input = true;
		 assertEquals(input, gui.clipBoard.equals("0"));
	}
	
	/**
	 *  test to check if Paste is working for formula
	 */
	@Test
	public void testPaste5() {
		SelectCellAndWrite(3,3);
		gui.clipBoard = "=123*2";
		robot.mouseMove(gui.btnPaste.getLocationOnScreen().x , gui.btnPaste.getLocationOnScreen().y);
		 click(); 
		 robot.delay(100);
		 double input = 246.0;
		 int i = 0;
		 while( gui.tblGrid.getValueAt(3, 3) == null && i < 100){
			robot.delay(10);	
			i++;
		 }
		 
		 assertEquals(input, gui.tblGrid.getValueAt(3, 3));
	}
	
	 /*************************/
	  /**** Testing Methods ****/
	  /*************************/
	
	/**
	 *  test to check if Paste is working using CTRL+V Shortcut
	 */
	@Test
	public void testPaste6() {
		SelectCellAndWrite(0,3);
		gui.clipBoard = "123";
		
		// use the robot to enter CTRL+V
		robot.waitForIdle();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		 robot.delay(100);
		 double input = 123.0;
		 int i = 0;
		 while( gui.tblGrid.getValueAt(0, 3) == null && i < 100){
			robot.delay(10);	
			i++;
		 }
		 
		 assertEquals(input, gui.tblGrid.getValueAt(0, 3));
	}
	
	
	
}
