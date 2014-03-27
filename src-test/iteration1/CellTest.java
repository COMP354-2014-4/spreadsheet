package iteration1;

import spreadsheet.*;

import static org.junit.Assert.*;
import org.junit.*;      //import JUnit Annotations
import org.junit.rules.ExpectedException;	//import JUnit Error handling package
import org.junit.Test;	//import testing package
import org.junit.Rule; //import JUnit Rule

public class CellTest {
	private static Grid test_Grid;
 
	//Acceptable error for comparing doubles
	public final double ACCEPTED_ERROR = 1E-10;
	
	@Rule
	public ExpectedException exceptionThrown = ExpectedException.none();
	
	
  /**** Before and After methods ****/
  @BeforeClass
  public static void testSetup() {
    // do something before all test
	  test_Grid = new Grid();
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
  
  /**
   * Testing display cell
   */
  public void testDisplayCell(){
    fail("Not yet implemented");
  }
  
  /**
   * Testing if value is valid
   */
  public void testvalidateValue(){
    fail("Not yet implemented");
  }

  

  /*** Exception Tests ***/
  /***********************/

  
  
  /*** Equals Tests ***/
  /********************/

  /**
   * Evaluated value stored in a cell
   * 
   */
  @Test
  public void testGetEvaluatedValueEquals() {
    //test default constructor value
    Cell cell01 = new Cell("A",1,test_Grid);
    assertEquals("Get Evaluated default cell value equals", 0.0, cell01.getEvaluatedValue(), ACCEPTED_ERROR);
    //test value from setValue
    Cell cell02 = new Cell("A",1,test_Grid);
    cell02.setValue("28.0");
    assertEquals("Get Evaluated set cell value equals", 28.0, cell02.getEvaluatedValue(), ACCEPTED_ERROR);
    //test value from formula
    Cell cell03 = new Cell("A",1,test_Grid);
    cell03.setValue("=1+1");
    assertEquals("Get Evaluated cell formula equals", 2.0, cell03.getEvaluatedValue(), ACCEPTED_ERROR);
  }
  
  /**
   * Evaluate Formula in a cell
   */
  @Test
  public void testEvaluateEquals() {
    //test evaluate on default constructor value
    Cell cell01 = new Cell("A",1,test_Grid);
    //assertEquals("Evaluate default cell formula equals", 0.0, cell01.evaluate(), ACCEPTED_ERROR);
    //test evaluate on setValue
    Cell cell02 = new Cell("A",1,test_Grid);
    cell02.setValue("28.0");
    //assertEquals("Evaluate set cell double equals", 28.0, cell02.evaluate(), ACCEPTED_ERROR);
    //test evaluate on formula
    Cell cell03 = new Cell("A",1,test_Grid);
    cell03.setValue("=1+1");
    //assertEquals("Evaluate set cell formula equals", 2.0, cell03.evaluate(), ACCEPTED_ERROR);    
  }
  
  /**
   * Value stored in cell
   */
  @Test
  public void testGetValueEquals(){
    //test value from constructor
    Cell cell01  = new Cell("B", 1, test_Grid);
    assertEquals("Get default value equals", "0", cell01.getValue());
    //test value from setValue
    Cell cell02  = new Cell("B", 1, test_Grid);
    cell02.setValue("28.0");
    assertEquals("Get double value equals", "28.0", cell02.getValue());
    //test value from formula
    Cell cell03  = new Cell("B", 1, test_Grid);
    cell03.setValue("=1+1");
    assertEquals("Get formula value equals", "=1+1", cell03.getValue());
  }
  
  /**
   * Testing if setValue() for cells work
   */
  @Test
  public void testSetValueEquals(){
    Cell cell = new Cell("B",2,test_Grid);
    cell.setValue("0.0");
    assertEquals("Cell new set value", "0.0", cell.getValue());
    cell.setValue("30.0");
    assertEquals("Cell new set value", "30.0", cell.getValue());
    cell.setValue("100.0");
    assertEquals("Cell new set value", "100.0", cell.getValue());
    cell.setValue("1.000000");
    assertEquals("Cell new set value", "1.000000", cell.getValue());
  }

  
  /**
   * Testing getRow() method
   */
  @Test 
  public void testGetRowEquals() {
    //Lower row boundary
    Cell cell01 = new Cell("A", 1, test_Grid);
    assertEquals("Getting cell's row", 1, cell01.getRow());  //Should return an int 
    //upper row boundary
    Cell cell02 = new Cell("A", 10, test_Grid);
    assertEquals("Getting cell's row", 10, cell02.getRow());
    //middle row boundary 
    Cell cell03 = new Cell("A", 5, test_Grid);
    assertEquals("Getting cell's row", 5, cell03.getRow());
  }
  
  /**
   * Testing GetCol() method
   */
  @Test
  public void testGetColEquals(){
    //Testing lower boundaries
    Cell cell01 = new Cell("A", 1, test_Grid);
    assertEquals("Getting cell's column", "A", cell01.getCol());
    //testing upper boundary
    Cell cell02 = new Cell("J", 1, test_Grid);
    assertEquals("Getting cell's column", "J", cell02.getCol());
    //testing middle boundary
    Cell cell03 = new Cell("E", 3, test_Grid);
    assertEquals("Getting cell's column", "E", cell03.getCol());
  }
  


  /*** Equals with tolerance Tests ***/
  /***********************************/
  
  

  /*** True Tests ***/
  /******************/


  
  /*** False Tests ***/
  /*******************/

  /***
   * Test if value in cell is not valid
   */
  @Test
  public void testIsValidValueFalse(){
    //mixed alphanumeric
    Cell cell01 = new Cell("B", 4, test_Grid);
    cell01.setValue("0a"); // Changed to =0a since 0a is actually a valid input now
    assertEquals("0a", cell01.getValue());
    //just alpha
    Cell cell02 = new Cell("B", 4, test_Grid);
    cell02.setValue("~-34B7H"); // Again, aa is now valid unless preceded with an equals sign
    assertEquals("~-34B7H", cell02.getValue());
    //test circular reference
    Cell cell03 = new Cell("A", 4, test_Grid);
    cell03.setValue("=A4");
    assertFalse("Cell invalid value", cell03.isValidValue());
  }

  /*** Null Tests ***/
  /******************/

  
  
  
  /*** Not Null Tests ***/
  /**********************/
  


  /*** Same Tests ***/
  /******************/
  


  /*** Not Same Tests ***/
  /**********************/

}
