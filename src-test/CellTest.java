
import spreadsheet.*;

import static org.junit.Assert.*;
import org.junit.*;      //import JUnit Annotations
import org.junit.rules.ExpectedException;	//import JUnit Error handling package
import org.junit.Test;	//import testing package
import org.junit.Rule; //import JUnit Rule

public class CellTest {
	
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
  
  //Testing default constructor - value in a cell
  @Test
  public void testCellValue1() {
	  Cell cell = new Cell();
	  assertEquals("Initial cell value", 0.0, cell.getValue());
  }
  
  //Testing default constructor - formula in a cell
  @Test
  public void testCellFormula() {
	  Cell cell = new Cell();
	  assertNull("Initial cell formula", null , cell.getFormula());
  }
 
  //Getters And Setters
  //Testing getRow() method
  //Testing boundaries
  @Test 
  public void testGetRow() {
	  Cell cell = new Cell(A, 1, 3.0);	//Creating a new cell
	  assertEquals("Getting cell's row", A,cell.getRow());	//Should return an int 
  }
  @Test
  public void testGetRow2() {
	  Cell cell = new Cell(A, 10, 3.0);	//Creating a new cell
	  assertEquals("Getting cell's row", A,cell.getRow());
  }
  
  //Testing valid rows
  @Test
  public void testGetRow3() {
	  Cell cell = new Cell(A, 5, 3.0);	//Creating a new cell
	  assertEquals("Getting cell's row", A,cell.getRow());
  }
  @Test
  public void testGetRow4() {
	  Cell cell = new Cell(A, 7, 3.0);	//Creating a new cell
	  assertEquals("Getting cell's row", A,cell.getRow());
  }
  
  //Testing invalid rows
  @Test
  public void testGetRow5() {
	  Cell cell = new Cell(A, -1, 3.0);	//Creating a new cell
	  assertNull("Getting cell's row", A,cell.getRow());
  }
  @Test
  public void testGetRow6() {
	  Cell cell = new Cell(A, 50, 3.0);	//Creating a new cell
	  assertNull("Getting cell's row", A,cell.getRow());
  }
  
  //Testing GetCol() method
  @Test
  //Testing boundaries
  public void testGetCol1(){
	  Cell cell = new Cell(A,3,4.0);	//Creating a new cell
	  assertEquals("Getting cell's column", 3, cell.getCol());
  }
  @Test
  public void testGetCol2(){
	  Cell cell = new Cell(J,3,4.0);	//Creating a new cell
	  assertEquals("Getting cell's column", 3, cell.getCol());
  }
  
  //Testing valid columns
  @Test
  public void testGetCol3(){
	  Cell cell = new Cell(C,3,4.0);	//Creating a new cell
	  assertEquals("Getting cell's column", 3, cell.getCol());
  }
  @Test
  public void testGetCol4(){
	  Cell cell = new Cell(D,3,4.0);	//Creating a new cell
	  assertEquals("Getting cell's column", 3, cell.getCol());
  }
  
  //Testing invalid columns
  @Test
  public void testGetCol5(){
	  Cell cell = new Cell(Z,3,4.0);	//Creating a new cell
	  assertNull("Getting cell's column", cell.getCol());
  }
  @Test
  public void testGetCol6(){
	  Cell cell = new Cell(M,3,4.0);	//Creating a new cell
	  assertNull("Getting cell's column", cell.getCol());
  }
  //Test if value is stored in cell
  @Test
  public void testGetValue(){
	  Cell cell  = new Cell(B, 1, 28.0);
	  assertEquals("Cell set value", 28.0, cell.getValue());
  }
  
  //Testing if the new value set in cell works
  @Test
  public void testSetValue(){
	  Cell cell = new Cell(B,2,29.0);
	  cell.setValue(30.0);
	  assertEquals("Cell new set value", 30.0, cell.getValue());
  }
  
  @Test(expected = IllegalArgumentException.class)
  //Test if value in cell is not a number
  public void testCellValue(){
	  Cell cell = new Cell(B, 4, bb);
	  assertEquals("Cell invalid value", bb, cell.getValue());
  }
  
  //Testing display cell
  public void testDiaplyCell(){
	  fail("Not yet implemented");
  }
  
  //Testing if value is valid
  public void testvalidateValue(){
	  fail("Not yet implemented");
  }
}
