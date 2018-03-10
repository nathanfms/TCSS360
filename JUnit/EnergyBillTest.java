import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the constructors and methods in the EnergyBill class.
 *
 * @author Hien Doan
 * @version March 2, 2018
 */
public class EnergyBillTest {

    /**
     * A energy bill object used for the test.
     */
    private EnergyBill eb;

    /**
     * A method to initialize the test fixture before the test.
     */
    @Before
    public void setUp() { // no need to throw any exceptions!
        eb = new EnergyBill("test", "3.33");
    }

    /**
     * Testing the toString method
     */
    @Test
    public void stringTest()    {
        assertEquals(eb.toString(), "test%$%3.33");
    }



}
