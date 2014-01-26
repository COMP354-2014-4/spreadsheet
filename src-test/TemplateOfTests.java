

import static org.junit.Assert.*;

import org.junit.*;
/*
 import org.junit.Test;
 import org.junit.Before;
 import org.junit.After;
 import org.junit.BeforeClass;
 import org.junit.AfterClass;
 */


public class TemplateOfTests {

  /**** Before and After methods ****/
  @BeforeClass
  public static void testSetup() {
    // do something before all tests
  }

  @AfterClass
  public static void testCleanup() {
    // Teardown for data used by the unit tests
    // do after all tests
  }

  @Before
  public static void testEachSetup() {
    // do something before each test
  }
  
  @After
  public static void testEachCleanup() {
    // do something after each test
  }

  
  /**** Test Methods ****/
  /**
   * Test 1
   * Exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testExceptionIsThrown() {
    Class tester = new Class();
    tester.multiply(1000, 5);
  }

  /**
   * Test 2
   * assert equals
   */
  @Test
  public void multiplyEquals() {
    Class tester = new Class();
    assertEquals("10 x 5 equals 50", 50, tester.multiply(10, 5));
    //fail("Not yet implemented");
  }

  /**
   * Test 3
   * assert equals with tolerance (for floats and doubles)
   */
  @Test
  public void multiplyEqualsTolerance() {
    Class tester = new Class();
    assertEquals("10 x 5 equals 49, 50, or 51", 50, tester.multiply(10, 5), 1);
    //fail("Not yet implemented");
  }
  
  /**
   * Test 4
   * assert true
   */
  @Test
  public void multiplyTrue() {
    Class tester = new Class();
    assertTrue("10 x 5 is 50", 50 == tester.multiply(10, 5));
    //fail("Not yet implemented");
  }
  
  /**
   * Test 5
   * assert false
   */
  @Test
  public void multiplyFalse() {
    Class tester = new Class();
    assertFalse("10 x 5 is not 50", 50 != tester.multiply(10, 5));
    //fail("Not yet implemented");
  }

  /**
   * Test 6
   * assert null
   */
  @Test
  public void multiplyNull() {
    Class tester = new Class();
    assertNull("tester is null", tester);
    //fail("Not yet implemented");
  }
  
  /**
   * Test 7
   * assert not null
   */
  @Test
  public void multiplyNotNull() {
    Class tester = new Class();
    assertNotNull("tester is not null", tester);
    //fail("Not yet implemented");
  }
  
  /**
   * Test 8
   * assert same
   */
  @Test
  public void multiplySame() {
    int result = 50;
    Class tester = new Class();
    assertSame("5 x 10 is same as result", result ,tester.multiply(5, 10));
    //fail("Not yet implemented");
  }
  
  /**
   * Test 9
   * assert not same
   */
  @Test
  public void multiplyNotSame() {
    int result = 50;
    Class tester = new Class();
    assertNotSame("5 x 10 is not same as result", result ,tester.multiply(5, 10));
    //fail("Not yet implemented");
  }

}
