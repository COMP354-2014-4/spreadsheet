import static org.junit.Assert.*;  //import assert methods

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;

import spreadsheet.*;

import org.junit.*;                //import JUnit Annotations


public class GridTest {
  
		File file;
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
    System.out.println("Prepping Test....");
  }
  
  @After
  public void testEachCleanup() {
    // do something after each test
    System.out.println("Test Completed!");
  }

  
  /**** Testing Methods ****/
  
  /*** Waiting for implementation ***/
  @Test
  public void testGetSelectedCell() {
    fail("Not yet implemented");
  }
  

  @Test
  public void testRemoveSelectedCell() {
    fail("Not yet implemented");
  }
  
  @Test
  public void testDisplay() {
	  
  }
  
  /*** Exception Tests ***/
  /**
   * getCell() - Exceptions
   * 
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCellsException() {
    Grid testGrid = new Grid();
    testGrid.getCell("a",1);
  }

  /**
   * selectCell() - Exceptions
   * 
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSelectCellException() {
    Grid testGrid = new Grid();
    testGrid.selectCell("a",1);
  }
  
  
  /*** Equals Tests ***/
  
  /**
   * Load(String filename) and Save(String filename) - Equals
   * dependent on:
   * 	grid.getCell(String,int)
   * 	grid.save(String)
   * 	grid.load(String)
   * 	cell.getValue()
   * 	cell.setValue(String)
   */
  @Test
  public void testLoadSaveEquals() {
	  
	    Grid testGrid = new Grid();
	    testGrid.getCell("A", 1).setValue("1");
	    testGrid.save("testSave");
	    testGrid = new Grid();
	    testGrid.load("testSave");
	    assertEquals("1", testGrid.getCell("A", 1).getValue());
	    file = new File("testSave.sav");
	    file.delete();
  }
  
  /**
   * Load(String filename) - Equals
   * Dependent on the implementation of Save(String filename) and Load((String filename)
   * Dependent on:
   * 	cell.setValue(String)
   * 	cell.getValue()
   */
  @Test
  public void testLoadEquals() {
	  Grid testGrid = new Grid();
	  
		Hashtable<String, Cell> cells = new Hashtable<>();
		Cell cell = new Cell("A",1,testGrid);
		cell.setValue("1");
		cells.put("A1", cell);
		file = new File("testLoad.sav");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cells);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testGrid.load("testLoad");
		assertEquals("1",testGrid.getCell("A", 1).getValue());
	    file.delete();
  }
  
  /**
   * clear() - Equals
   */
  @Test
  public void testClearEquals() {
	Grid testGrid = new Grid();
	testGrid.getCell("A",1).setValue("1");
	testGrid.clear(); 
	assertEquals("0", testGrid.getCell("A", 1).getValue());
	
  }
  
  
  /*** Equals with tolerance Tests ***/
  
  
  /*** True Tests ***/

  /**
   * Save(String) - True
   * test for file creation
   */
  @Test
  public void testSaveTrue() {
	    Grid testGrid = new Grid();
	    testGrid.getCell("A", 1);//should create a cell
	    testGrid.save("test");
	    file = new File("test.sav");
	    assertTrue(file.exists());
	    file.delete();
	  
  }
  
  /*** False Tests ***/
  
  
  /*** Null Tests ***/
  
  
  /*** Not Null Tests ***/
  /**
   * getCell() - Not Null
   * getCell() should never return null and must pass this test
   */
  @Test
  public void testGetCellNotNull() {
    //fail("Not yet implemented");
    Grid testGrid = new Grid();
    assertNotNull("getCells() is not null", testGrid.getCell("a",1));
  }
  
  /**
   * SelectCell() - Not Null
   * SelectCell() should never return null and must pass this test
   */
  @Test
  public void testSelectCellNotNull() {
    //fail("Not yet implemented");
    Grid testGrid = new Grid();
    assertNotNull("SelectCell() is not null", testGrid.selectCell("a",1));
  }

  /**
   * RemoveCell() - Not Null
   * RemoveCell() should be removing a cell from the HashTable
   */
  @Test
  public void testRemoveCellNotNull() {
    //fail("Not yet implemented");
    Grid testGrid = new Grid();
    testGrid.removeCell("a",1);  //This should remove the cell
    //check hashtable to see if this element exists
  }
  

  /**
   * RemoveSelectedCell() - Not Null
   * RemoveSelectedCell() should be removing the current cell from the HashTable
   */
  @Test
  public void testRemoveSelectedCellNotNull() {
    //fail("Not yet implemented");
    Grid testGrid = new Grid();
    testGrid.removeSelectedCell();  //This should remove the cell
    //check hashtable to see if this element exists
  }
  

  
  /*** Same Tests ***/
  
  /*** Not Same Tests ***/


}
