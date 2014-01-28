import static org.junit.Assert.*;  //import assert methods
import org.junit.*;                //import JUnit Annotations
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import spreadsheet.*;

public class GridTest {
  
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

  
  
  /*************************/
  /**** Testing Methods ****/
  /*************************/
  
  /*** Waiting for implementation ***/
  /**********************************/
  @Ignore @Test
  public void testRemoveSelectedCell() {
    fail("Not yet implemented");
  }
  
  /**
   * RemoveCell() - Not Null
   * RemoveCell() should be removing a cell from the HashTable
   */
  @Ignore @Test
  public void testRemoveCell() {
    fail("Not yet implemented");
  }

  
  /**
   * clear() - Equals
   * 
   * purpose: test to see if clear resets the grid
   * 
   * dependent on:
   * 	grid.getCell(string,int)
   * 	cell.setValue(string)
   * 	cell.getValue()
   */
  @Test
  public void testClearEquals() {
	Grid testGrid = new Grid();
	testGrid.getCell("A", 1).setValue("1");

	testGrid.getCell("E", 5).setValue("9");
	testGrid.clear();
	assertEquals("0", testGrid.getCell("A", 1).getValue());
	assertEquals("0", testGrid.getCell("E", 5).getValue());
  }
  
  /**
   * Display() - ???
   * 
   * purpose: test the functionality of the display
   * cannot automate yet, can only check for no exceptions
   * 
   * dependent on:
   * 	grid.getCell(string,int)
   * 	cell.setValue(string)
   */
  @Test
  public void testDisplay() {
	Grid testGrid = new Grid();
	testGrid.getCell("B", 2).setValue("2");
	testGrid.Display();
  }
  
  
  /*** Exception Tests ***/
  /***********************/
  
  
  /*** Equals Tests ***/
  /********************/

  /**
   * Load(String filename) and Save(String filename) - Equals
   * 
   * purpose: check basic save/load functionality
   * 
   * dependent on:
   * 	grid.getCell(String,int)
   * 	grid.save(String)
   * 	grid.load(String)
   * 	cell.getValue()
   * 	cell.setValue(String)
   */
  @Test
  public void testLoadSaveEquals1() {
	    Grid testGrid = new Grid();
	    testGrid.getCell("a", 1).setValue("1");
	    testGrid.save("testSave");
	    testGrid = new Grid();
	    testGrid.load("testSave");
	    assertEquals("1", testGrid.getCell("a", 1).getValue());
	    File file = new File("testSave.sav");
	    file.delete();
  }
  
  /**
   * Load(String filename) and Save(String filename) - Equals
   * 
   * purpose: check is evaluated values are kept
   * 
   * dependent on:
   * 	grid.getCell(String,int)
   * 	grid.save(String)
   * 	grid.load(String)
   * 	cell.getValue()
   * 	cell.setValue(String)
   * 	cell.evaluate()
   * 	cell.getEvaluatedValue()
   */
  @Test
  public void testLoadSaveEquals2() {
	  
	    Grid testGrid = new Grid();
	    Cell c = testGrid.getCell("A", 1);
	    c.setValue("=2");
	    c.evaluate();
	    
	    testGrid.save("testSave");
	    testGrid = new Grid();
	    testGrid.load("testSave");
	    assertEquals(2.0, testGrid.getCell("A", 1).getEvaluatedValue(),0.00001);
	    File file = new File("testSave.sav");
	    file.delete();
  }
  /**
   * Load(String filename) and Save(String filename) - Equals
   * 
   * purpose: check is references to other cells are kept
   * 
   * dependent on:
   * 	grid.getCell(String,int)
   * 	grid.save(String)
   * 	grid.load(String)
   * 	cell.getValue()
   * 	cell.setValue(String)
   * 	cell.evaluate()
   * 	cell.getEvaluatedValue()
   */
  @Test
  public void testLoadSaveEquals3() {
	  
	    Grid testGrid = new Grid();
	    testGrid.getCell("A", 2).setValue("3");
	    Cell c = testGrid.getCell("A", 1);
	    c.setValue("=A2");
	    c.evaluate();
	    
	    testGrid.save("testSave");
	    testGrid = new Grid();
	    testGrid.load("testSave");
	    assertEquals(3.0, testGrid.getCell("A", 1).getEvaluatedValue(),0.00001);
	    File file = new File("testSave.sav");
	    file.delete();
  }

  /**
   * Load(String filename) and Save(String filename) - Equals
   * 
   * purpose: see if save overwriting works
   * 
   * dependent on:
   * 	grid.getCell(String,int)
   * 	grid.save(String)
   * 	grid.load(String)
   * 	cell.getValue()
   * 	cell.setValue(String)
   */
  @Test
  public void testLoadSaveEquals4() {
	    Grid testGrid = new Grid();
	    testGrid.getCell("A", 1);//should create a cell
	    testGrid.save("test");
	    testGrid.getCell("A", 1).setValue("5");//set expected value
	    testGrid.save("test");
	    testGrid = new Grid();
	    testGrid.load("test");//load back previous value
	    
	    File file = new File("test.sav");
	    assertEquals("5",testGrid.getCell("A", 1).getValue());
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
		File file = new File("testLoad.sav");
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


  
  /*** Equals with tolerance Tests ***/
  /***********************************/
  
  

  /*** True Tests ***/
  /******************/
  
  /**
   * Save() - True
   * purpose: test the creation of a file through save
   */
  @Test
  public void testSaveTrue() {
	    Grid testGrid = new Grid();
	    testGrid.getCell("A", 1);//should create a cell
	    testGrid.save("test");
	    File file = new File("test.sav");
	    assertTrue(file.exists());
	    file.delete();
  }
  
  
  /*** False Tests ***/
  /*******************/
  
  /**
   * Load(String) - False
   * 
   * purpose: check if the load function behaves correctly when the file is not found
   */
  @Test
  public void testLoadFalse(){
	Grid testGrid = new Grid();
	assertFalse(testGrid.load("dosNotExist"));
  }
  
  
  /*** Null Tests ***/
  /******************/
  
  /**
   * getCell() - Null
   * getCell() should return null when column or row is out of bounds 
   */
  @Test
  public void testGetCellNull() {
    //fail("Not yet implemented");
    /// test beyond upper bound column
    Grid testGrid01 = new Grid();
    assertNull("getCell() is null", testGrid01.getCell("m",1));
    /// test beyond lower bound row
    Grid testGrid02 = new Grid();
    assertNull("getCell() is null", testGrid02.getCell("a",0));
    /// test beyond upper bound row
    Grid testGrid03 = new Grid();
    assertNull("getCell() is null", testGrid03.getCell("a",testGrid03.getMaxHeight()+1));
  }
  
  /**
   * SelectCell() - Null
   * SelectCell() should return null when provided column and row are outside range
   */
  @Test
  public void testSelectCellNull() {
    //fail("Not yet implemented");
    /// test beyond upper bound column
    Grid testGrid01 = new Grid();
    assertNull("SelectCell() is null", testGrid01.selectCell("m",1));
    /// test beyond lower bound row
    Grid testGrid02 = new Grid();
    assertNull("SelectCell() is null", testGrid02.selectCell("a",0));
    /// test beyond upper bound row
    Grid testGrid03 = new Grid();
    assertNull("SelectCell() is null", testGrid03.selectCell("a",testGrid03.getMaxHeight()+1));
  }
  
  

  /*** Not Null Tests ***/
  /**********************/
  
  /**
   * getCell() - Not Null
   * getCell() should never return null 
   */
  @Test
  public void testGetCellNotNull() {
    //fail("Not yet implemented");
    /// test lower bound column and row
    Grid testGrid01 = new Grid();
    assertNotNull("getCell() is not null", testGrid01.getCell("a",1));
    /// test middle bound column
    Grid testGrid02 = new Grid();
    assertNotNull("getCell() is not null", testGrid02.getCell("f",1));
    /// test upper bound column
    Grid testGrid03 = new Grid();
    assertNotNull("getCell() is not null", testGrid03.getCell("j",1));
    /// test middle bound row
    Grid testGrid04 = new Grid();
    assertNotNull("getCell() is not null", testGrid04.getCell("a",testGrid04.getMaxHeight()/2));
    /// test upper bound row
    Grid testGrid05 = new Grid();
    assertNotNull("getCell() is not null", testGrid05.getCell("a",testGrid05.getMaxHeight()));
  }
  
  /**
   * SelectCell() - Not Null
   * SelectCell() should return not null when provided col and row are within range
   *   must pass this test
   */
  @Test
  public void testSelectCellNotNull() {
    /// test lower bound column and row
    Grid testGrid01 = new Grid();
    assertNotNull("SelectCell() is not null", testGrid01.selectCell("a",1));
    /// test middle bound column
    Grid testGrid02 = new Grid();
    assertNotNull("SelectCell() is not null", testGrid02.selectCell("f",1));
    /// test upper bound column
    Grid testGrid03 = new Grid();
    assertNotNull("SelectCell() is not null", testGrid03.selectCell("j",1));
    /// test middle bound row
    Grid testGrid04 = new Grid();
    assertNotNull("SelectCell() is not null", testGrid04.selectCell("a",testGrid04.getMaxHeight()/2));
    /// test upper bound row
    Grid testGrid05 = new Grid();
    assertNotNull("SelectCell() is not null", testGrid05.selectCell("a",testGrid05.getMaxHeight()));
  }

  /**
   * getSelectedCell() - Not Null
   * getSelectedCell() should always return a cell
   */
  @Test
  public void testGetSelectedCellNotNull() {
    //fail("Not yet implemented");
    Grid testGrid = new Grid();
    assertNotNull("getSelectedCell() is not null", testGrid.getSelectedCell());
  }



  /*** Same Tests ***/
  /******************/
  


  /*** Not Same Tests ***/
  /**********************/


}
