import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * A class to test the constructors and methods in the Project class.
 *
 * @author Hien Doan
 * @version March 2, 2018
 */
public class ProjectTest {

    /** A Project object used for the tests. */
    private Project myProject;

    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() { // no need to throw any exceptions!
        myProject = new Project("test");
    }

    @Test
    public void setProjectName() {
        myProject.setProjectName("002");
        assertEquals("002", myProject.getProjectName());
    }

    @Test
    public void addMaterial() {
        myProject.addMaterial("mat1", 1, new BigDecimal("3.33"));
        assertEquals(myProject.toString(), "test$$$mat1,1,3.33%%%");
        myProject.addMaterial("mat2", 3, "9.99");
        assertEquals(myProject.toString(), "test$$$mat1,1,3.33%%%mat2,3,9.99%%%");
    }

    @Test
    public void removeMaterial() {
        myProject.addMaterial("mat1", 1, new BigDecimal("3.33"));
        myProject.addMaterial("mat2", 3, "9.99");
        myProject.removeMaterial(new Material("mat2", "3", "9.99"));
        assertEquals(myProject.toString(), "test$$$mat1,1,3.33%%%");
    }

    public void getTotalCost() {
        assertEquals(myProject.getTotalCost(), "3.33");
    }

    public void getProjectName() {
        assertEquals("test", myProject.getProjectName());
    }

}