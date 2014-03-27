package iteration3;
import spreadsheet.*;
import spreadsheet.Cell.Formatting;
import static org.junit.Assert.*;

import org.junit.*;      //import JUnit Annotations
import org.junit.rules.ExpectedException;	//import JUnit Error handling package
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;	//import testing package
import org.junit.Rule; //import JUnit Rule
public class UndoRedoTest {

	private static Grid testGrid;
	private static UndoRedo undoRedo_TestStack;


	@Rule
	public ExpectedException exceptionThrown = ExpectedException.none();


	/**** Before and After methods ****/
	@BeforeClass
	public static void testSetup() {
		// do something before all tests
	}

	@AfterClass
	public static void testCleanup() {
		// do something after all tests
	}

	@Before
	public void testEachSetup() {
		// do something before each test
		testGrid = new Grid(50,50);
		undoRedo_TestStack = new UndoRedo();
		System.out.println("Setting up test...");
	}

	@After
	public void testEachCleanup() {
		// do something after each test
		System.out.println("Test Completed!");
	}

	/**
	 * 
	 * Test to see if undo cannot be performed with a new empty grid
	 * 
	 */
	@Test
	public void testUndoActionEmpty() {
		Cell topStackCell = undoRedo_TestStack.undoAction(testGrid);

		// Verify that the undo stack is empty
		assertEquals(-1, topStackCell.getRow());
		assertEquals("-1", topStackCell.getCol());

	}

	/**
	 * 
	 * Test basic undo after editing cells
	 * 
	 */
	@Test
	public void testUndoAction() {

		//Add the new cell to the undo stack to simulate what
		//occurs during cell editing. Every change must be recorded so it can be undone
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("A",5));
		//Update cell values, simulating user manual input
		testGrid.selectCell("A",5).setValue("88");

		//Add the new cell to the undo stack to simulate what
		//occurs during cell editing. Every change must be recorded so it can be undone
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("E",8));
		//Update cell value, simulating user manual input
		testGrid.selectCell("E",8).setValue("99");


		//Simulate the undo action and keep that info
		Cell result2_undo = undoRedo_TestStack.undoAction(testGrid);
		testGrid.selectCell(result2_undo.getCol(), result2_undo.getRow()).setValue(result2_undo.getValue());
		testGrid.selectCell(result2_undo.getCol(), result2_undo.getRow()).setCellFormat(result2_undo.getCellFormat());

		Cell result1_undo = undoRedo_TestStack.undoAction(testGrid);
		testGrid.selectCell(result1_undo.getCol(), result1_undo.getRow()).setValue(result1_undo.getValue());
		testGrid.selectCell(result1_undo.getCol(), result1_undo.getRow()).setCellFormat(result1_undo.getCellFormat());

		//Case when the undo stack is empty
		Cell emptyStackCell = undoRedo_TestStack.undoAction(testGrid);

		//Compare the cells modified by the undo action and the expected value
		assertEquals("E", result2_undo.getCol());
		assertEquals(8, result2_undo.getRow());
		assertEquals("0", result2_undo.getValue());
		assertEquals(Formatting.REAL, result2_undo.getCellFormat());

		assertEquals("A", result1_undo.getCol());
		assertEquals(5, result1_undo.getRow());
		assertEquals("0", result1_undo.getValue());
		assertEquals(Formatting.REAL, result1_undo.getCellFormat());

		//Check that the undo stack is indeed empty
		assertEquals(-1, emptyStackCell.getRow());
		assertEquals("-1", emptyStackCell.getCol());

	}

	/**
	 * 
	 * Test to see if redo cannot be performed with a new empty grid
	 * 
	 */
	@Test
	public void testRedoActionEmpty() {
		Cell topStackCell = undoRedo_TestStack.redoAction(testGrid);

		// Verify that the undo stack is empty
		assertEquals(-1, topStackCell.getRow());
		assertEquals("-1", topStackCell.getCol());

	}

	/**
	 * 
	 * Test basic redo after editing cells
	 * 
	 */
	@Test
	public void testRedoAction() {

		//Add the new cell to the undo stack to simulate what
		//occurs during cell editing. Every change must be recorded so it can be undone and redone
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("A",5));
		//Update cell values, simulating user manual input
		testGrid.selectCell("A",5).setValue("88");

		//Add the new cell to the undo stack to simulate what
		//occurs during cell editing. Every change must be recorded so it can be undone and redone
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("E",8));
		//Update cell value, simulating user manual input
		testGrid.selectCell("E",8).setValue("99");


		//Simulate the undo action and keep that info
		Cell result2_undo = undoRedo_TestStack.undoAction(testGrid);
		testGrid.selectCell(result2_undo.getCol(), result2_undo.getRow()).setValue(result2_undo.getValue());
		testGrid.selectCell(result2_undo.getCol(), result2_undo.getRow()).setCellFormat(result2_undo.getCellFormat());

		Cell result1_undo = undoRedo_TestStack.undoAction(testGrid);
		testGrid.selectCell(result1_undo.getCol(), result1_undo.getRow()).setValue(result1_undo.getValue());
		testGrid.selectCell(result1_undo.getCol(), result1_undo.getRow()).setCellFormat(result1_undo.getCellFormat());


		//Now that undo action was performed, simulate the redo action and keep that info
		Cell result1_redo = undoRedo_TestStack.redoAction(testGrid);
		testGrid.selectCell(result1_redo.getCol(), result1_redo.getRow()).setValue(result1_redo.getValue());
		testGrid.selectCell(result1_redo.getCol(), result1_redo.getRow()).setCellFormat(result1_redo.getCellFormat());

		Cell result2_redo = undoRedo_TestStack.redoAction(testGrid);
		testGrid.selectCell(result2_redo.getCol(), result2_redo.getRow()).setValue(result2_redo.getValue());
		testGrid.selectCell(result2_redo.getCol(), result2_redo.getRow()).setCellFormat(result2_redo.getCellFormat());

		//Case when the redo stack is empty
		Cell emptyRedoStackCell = undoRedo_TestStack.redoAction(testGrid);

		//Compare the cells modified by the undo action with the results produced by the redo action
		assertEquals("E", result2_redo.getCol());
		assertEquals(8, result2_redo.getRow());
		assertEquals("99", result2_redo.getValue());
		assertEquals(Formatting.REAL, result2_redo.getCellFormat());

		assertEquals("A", result1_redo.getCol());
		assertEquals(5, result1_redo.getRow());
		assertEquals("88", result1_redo.getValue());
		assertEquals(Formatting.REAL, result1_redo.getCellFormat());

		//Check that the undo stack is indeed empty
		assertEquals(-1, emptyRedoStackCell.getRow());
		assertEquals("-1", emptyRedoStackCell.getCol());
		assertEquals("0", emptyRedoStackCell.getValue());

	}

	/**
	 * 
	 * Test that the redo action cannot be performed when
	 * an undo sequence is interrupted by a new cell editing
	 * 
	 */
	@Test
	public void testRedoAction_ClearStack() {


		//Add the new cell to the undo stack to simulate what
		//occurs during cell editing. Every change must be recorded so it can be undone and redone
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("A",5));
		//Update cell values, simulating user manual input
		testGrid.selectCell("A",5).setValue("88");

		//Add the new cell to the undo stack to simulate what
		//occurs during cell editing. Every change must be recorded so it can be undone and redone
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("E",8));
		//Update cell value, simulating user manual input
		testGrid.selectCell("E",8).setValue("99");


		//Simulate the undo action and keep that info
		Cell result2_undo = undoRedo_TestStack.undoAction(testGrid);
		testGrid.selectCell(result2_undo.getCol(), result2_undo.getRow()).setValue(result2_undo.getValue());
		testGrid.selectCell(result2_undo.getCol(), result2_undo.getRow()).setCellFormat(result2_undo.getCellFormat());

		Cell result1_undo = undoRedo_TestStack.undoAction(testGrid);
		testGrid.selectCell(result1_undo.getCol(), result1_undo.getRow()).setValue(result1_undo.getValue());
		testGrid.selectCell(result1_undo.getCol(), result1_undo.getRow()).setCellFormat(result1_undo.getCellFormat());

		//Interrupt the undo actions by simulating cell editing
		undoRedo_TestStack.noUndoRedoAction(testGrid.selectCell("C",13));
		//Update cell value, simulating user manual input
		testGrid.selectCell("C",13).setValue("77");

		//The interrupted undo actions should cause the redo stack to be empty. Keep that info to test it.
		Cell emptyRedoStackCell = undoRedo_TestStack.redoAction(testGrid);

		//Check that the undo stack is indeed empty
		assertEquals(-1, emptyRedoStackCell.getRow());
		assertEquals("-1", emptyRedoStackCell.getCol());
		assertEquals("0", emptyRedoStackCell.getValue());

	}

}
