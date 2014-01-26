import static org.junit.Assert.*;  //import assert methods
import org.junit.*;                //import JUnit Annotations


public class CellTest {
  
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

  
  /*** Waiting for implementation ***/
  
  /**** Testing Methods ****/
  
  /*** Exception Tests ***/
  
  /*** Equals Tests ***/
  
  /*** Equals with tolerance Tests ***/
  
  /*** True Tests ***/
  
  /*** False Tests ***/
  
  /*** Null Tests ***/
  
  /*** Not Null Tests ***/
  
  /*** Same Tests ***/
  
  /*** Not Same Tests ***/
  
}
