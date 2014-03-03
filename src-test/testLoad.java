import static org.junit.Assert.*;


import spreadsheet.*;

import java.io.File;
import java.io.IOException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.junit.rules.TemporaryFolder; // create temp files and be removed after the test
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jayanti Rani
 *
 */
public class testLoad {

	// variables
	private static Robot robot;
	private static SSGUI gui;
	private static Grid grid;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		robot = new Robot();
		grid = new Grid ();
		gui = new SSGUI(grid);
		}
	
@Rule
 public TemporaryFolder folder = new TemporaryFolder();
	
	/**
	 * method for simulating mouse click
	 */
	public static void click () {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(100);
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * create temp file to check the load function
	 * @throws IOException
	 */
	 @Test
	  public void testUsingTempFolder() throws IOException {
	    File createdFile = folder.newFile("myfilefile.sav");
	    assertTrue(createdFile.exists());
	  }
	

	 /**
	  * method to input a file name using robot
	  * @param s   String name of the file
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


	/**
	 * load test for testing from direct location for Load
	*/
	@Test
	public void testLoad1() {
		  robot.mouseMove(gui.btnLoad.getLocationOnScreen().x , gui.btnLoad.getLocationOnScreen().y);
		 click();
		 inputString("myfilefile.sav");
		 robot.delay(2000); 
		 robot.keyPress(KeyEvent.VK_ENTER);
		 robot.keyRelease(KeyEvent.VK_ENTER);
		 robot.delay(2000); 
	}
	 
	/**
	 * load test for testing from File --> Load
	 */
	
	@Test
	public void testLoad2() {
		robot.mouseMove(gui.mnuFile.getLocationOnScreen().x , gui.mnuFile.getLocationOnScreen().y);
		click();
		robot.mouseMove(gui.mniLoad.getLocationOnScreen().x , gui.mniLoad.getLocationOnScreen().y);
		click();
		 inputString("myfilefile.sav");
		 robot.delay(100); 
		 robot.keyPress(KeyEvent.VK_ENTER);
		 robot.keyRelease(KeyEvent.VK_ENTER);
		 robot.delay(2000); 
		 
	}

}
