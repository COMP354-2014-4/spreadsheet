package iteration3;

import static org.junit.Assert.*;

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

import spreadsheet.Grid;
import spreadsheet.SSGUI;

public class TestExtendedFormulas {

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
				//manages the key inputs
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
					case 'S':
						_rob.keyPress(KeyEvent.VK_S);
						_rob.keyRelease(KeyEvent.VK_S);
						break;
					case 'U':
						_rob.keyPress(KeyEvent.VK_U);
						_rob.keyRelease(KeyEvent.VK_U);
						break;
					case 'M':
						_rob.keyPress(KeyEvent.VK_M);
						_rob.keyRelease(KeyEvent.VK_M);
						break;
					case 'B':
						_rob.keyPress(KeyEvent.VK_B);
						_rob.keyRelease(KeyEvent.VK_B);
						break;
					case '=':
						_rob.keyPress(KeyEvent.VK_EQUALS);
						_rob.keyRelease(KeyEvent.VK_EQUALS);
						break;
					case '(':
						_rob.keyPress(KeyEvent.VK_SHIFT);
						_rob.keyPress(KeyEvent.VK_9);
						_rob.keyRelease(KeyEvent.VK_9);
						_rob.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case ')':
						_rob.keyPress(KeyEvent.VK_SHIFT);
						_rob.keyPress(KeyEvent.VK_0);
						_rob.keyRelease(KeyEvent.VK_0);
						_rob.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case 'C':
						_rob.keyPress(KeyEvent.VK_C);
						_rob.keyRelease(KeyEvent.VK_C);
						break;
					case 'F':
						_rob.keyPress(KeyEvent.VK_F);
						_rob.keyRelease(KeyEvent.VK_F);
						break;
					case '1':
						_rob.keyPress(KeyEvent.VK_1);
						_rob.keyRelease(KeyEvent.VK_1);
						break;
					case '2':
						_rob.keyPress(KeyEvent.VK_2);
						_rob.keyRelease(KeyEvent.VK_2);
						break;
					case '3':
						_rob.keyPress(KeyEvent.VK_3);
						_rob.keyRelease(KeyEvent.VK_3);
						break;
					case '4':
						_rob.keyPress(KeyEvent.VK_4);
						_rob.keyRelease(KeyEvent.VK_4);
						break;
					case '5':
						_rob.keyPress(KeyEvent.VK_5);
						_rob.keyRelease(KeyEvent.VK_5);
						break;
					case '6':
						_rob.keyPress(KeyEvent.VK_6);
						_rob.keyRelease(KeyEvent.VK_6);
						break;
					case '7':
						_rob.keyPress(KeyEvent.VK_7);
						_rob.keyRelease(KeyEvent.VK_7);
						break;
					case '8':
						_rob.keyPress(KeyEvent.VK_8);
						_rob.keyRelease(KeyEvent.VK_8);
						break;
					case '9':
						_rob.keyPress(KeyEvent.VK_9);
						_rob.keyRelease(KeyEvent.VK_9);
						break;
					case 'I':
						_rob.keyPress(KeyEvent.VK_I);
						_rob.keyRelease(KeyEvent.VK_I);
						break;
					case 'N':
						_rob.keyPress(KeyEvent.VK_N);
						_rob.keyRelease(KeyEvent.VK_N);
						break;
					case 'A':
						_rob.keyPress(KeyEvent.VK_A);
						_rob.keyRelease(KeyEvent.VK_A);
						break;
					case 'E':
						_rob.keyPress(KeyEvent.VK_E);
						_rob.keyRelease(KeyEvent.VK_E);
						break;
					case 'V':
						_rob.keyPress(KeyEvent.VK_V);
						_rob.keyRelease(KeyEvent.VK_V);
						break;
					case 'G':
						_rob.keyPress(KeyEvent.VK_G);
						_rob.keyRelease(KeyEvent.VK_G);
						break;
					case 'X':
						_rob.keyPress(KeyEvent.VK_X);
						_rob.keyRelease(KeyEvent.VK_X);
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
		 * These tests check to see whether the extended formulas work as prescribed.
		 * the methods SUM, AVG, MIN, and MAX are tested by inputting numbers into a range of cells
		 * and then inputting the command into another cell.  The value obtained is then compared with
		 * its mathematically correct answer to determine whether or not the behaviour is accurate.
		 * 			
		 * 
		 * 
		 *  
		 * 	
		 * 	
		 */
		@Test
		public void testEvaluateSum() {
			System.out.println("Test the extended formula SUM.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(0, 2, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(0, 3, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(0, 4, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(0, 5, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(0, 6, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=SUM(C1-F1)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(0, 6).toString().equals("70.0"));
		}
		
		@Test
		public void testEvaluateAverage() {
			System.out.println("Test the extended formula AVG.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(1, 2, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(1, 3, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(1, 4, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(1, 5, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(1, 6, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=AVG(C2-F2)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(1, 6).toString().equals("17.5"));
		}
		@Test
		public void testEvaluateMinimum() {
			System.out.println("Test the extended formula MIN.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(2, 2, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(2, 3, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(2, 4, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(2, 5, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(2, 6, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=MIN(C3-F3)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(2, 6).toString().equals("10.0"));
		}
		@Test
		public void testEvaluateMaximum() {
			System.out.println("Test the extended formula MAX.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(3, 2, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(3, 3, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(3, 4, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(3, 5, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(3, 6, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=MAX(C4-F4)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(3, 6).toString().equals("25.0"));
		}
		
		@Test
		public void testEvaluateBoxMaximum() {
			System.out.println("Test the extended formula MAX.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(5, 4, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(5, 5, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(6, 4, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(6, 5, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(6, 6, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F4 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=MAX(E6-F7)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(6, 6).toString().equals("25.0"));
		}
		
		@Test
		public void testEvaluateBoxMinimum() {
			System.out.println("Test the extended formula MIN.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(7, 4, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(7, 5, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(8, 4, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(8, 5, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(8, 6, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F3 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=MIN(E8-F9)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(8, 6).toString().equals("10.0"));
		}
		
		@Test
		public void testEvaluateBoxAverage() {
			System.out.println("Test the extended formula AVG.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(7, 1, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(7, 2, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(8, 1, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(8, 2, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(8, 3, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F2 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=AVG(B8-C9)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(8, 3).toString().equals("17.5"));
		}
		
		@Test
		public void testEvaluateBoxSum() {
			System.out.println("Test the extended formula SUM.");
			
			Rectangle rect1 = _ui.tblGrid.getCellRect(5, 1, true);
			Rectangle rect2 = _ui.tblGrid.getCellRect(5, 2, true);
			Rectangle rect3 = _ui.tblGrid.getCellRect(6, 1, true);
			Rectangle rect4 = _ui.tblGrid.getCellRect(6, 2, true);
			Rectangle rect5 = _ui.tblGrid.getCellRect(6, 3, true);
			
			Point pCell1 = rect1.getLocation();
			Point pCell2 = rect2.getLocation();
			Point pCell3 = rect3.getLocation();
			Point pCell4 = rect4.getLocation();
			Point pCell5 = rect5.getLocation();
			
			Point pTable = _ui.tblGrid.getLocationOnScreen();
			
			Point mMouse1 = new Point(pCell1.x + 5 + pTable.x, pCell1.y + 5 + pTable.y);
			Point mMouse2 = new Point(pCell2.x + 5 + pTable.x, pCell2.y + 5 + pTable.y);
			Point mMouse3 = new Point(pCell3.x + 5 + pTable.x, pCell3.y + 5 + pTable.y);
			Point mMouse4 = new Point(pCell4.x + 5 + pTable.x, pCell4.y + 5 + pTable.y);
			Point mMouse5 = new Point(pCell5.x + 5 + pTable.x, pCell5.y + 5 + pTable.y);
			
			_rob.mouseMove(mMouse1.x, mMouse1.y);
			_rob.waitForIdle();
			click();
			
			inputString("15.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in C1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse2.x, mMouse2.y);
			_rob.waitForIdle();
			click();
			
			inputString("10.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in D1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse3.x, mMouse3.y);
			_rob.waitForIdle();
			click();
			
			inputString("20.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in E1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse4.x, mMouse4.y);
			_rob.waitForIdle();
			click();
			
			inputString("25.0");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println("Input a value in F1 works");
			_rob.delay(500);//delay to avoid double click.....
			
			_rob.mouseMove(mMouse5.x, mMouse5.y);
			_rob.waitForIdle();
			click();
			
			inputString("=SUM(B6-C7)");
			_rob.waitForIdle();
			_rob.keyPress(KeyEvent.VK_ENTER);
			_rob.keyRelease(KeyEvent.VK_ENTER);
			_rob.waitForIdle();
			System.out.println(_ui.txtInputBox.getText());
			_rob.delay(500);//delay to avoid double click.....
			
			assertTrue(_ui.tblGrid.getValueAt(6, 3).toString().equals("70.0"));
		}
}