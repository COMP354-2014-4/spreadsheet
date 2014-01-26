import static org.junit.Assert.*;  //import assert methods
import org.junit.*;                //import JUnit Annotations


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
  public static void testEachSetup() {
    // do something before each test
    System.out.println("Prepping Test....");
  }
  
  @After
  public static void testEachCleanup() {
    // do something after each test
    System.out.println("Test Completed!");
  }

  
  /**** Testing Methods ****/
  
  /*** Waiting for implementation ***/
  @Test
  public void testGetSelectedCell() {
    fail("Not yet implemented");
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
  
  
  /*** Equals with tolerance Tests ***/
  
  
  /*** True Tests ***/
  
  
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
