import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * A class to test the constructors and methods in the material class.
 *
 * @author Hien Doan
 * @version March 2, 2018
 */
public class MaterialTest {

    /**
     * A material object used for the tests.
     */
    private Material m;

    /**
     * A method to initialize the test fixture before the test.
     */
    @Before
    public void setUp()    {
        m = new Material("Test", "23", "1.00");
    }

    /**
     * testing both constructors and getters
     */
    @Test
    public void constructorTest()  {
        assertEquals(m.getMaterialName(), "Test");
        assertEquals(m.getQuantity(), 23);
        assertEquals(m.getCostPerOne(), "1.00");
        assertEquals(m.getTotalCost(), "23.00");
        m = new Material("test");
        assertEquals(m.getMaterialName(), "test");
        assertEquals(m.getQuantity(), 0);
        assertEquals(m.getCostPerOne(), "0");
        assertEquals(m.getTotalCost(), "0");
    }

    /**
     * testing tostring method
     */
    @Test
    public void stringTests()   {
        assertEquals(m.toString(), "Test\t23\t1.00\t23.00");
    }

    /**
     * testing .equals method
     */
    @Test
    public void equalTest() {
        Material m2 = new Material("Test", "23", "1.00");
        assertTrue(m.equals(m2));
    }





}
