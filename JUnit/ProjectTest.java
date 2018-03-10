import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the constructors and methods in the Project class.
 *
 * @author Hien Doan
 * @version March 2, 2018
 */
public class ProjectTest {

    /**
     * A Project object used for the tests.
     */
    private Project myProject;

    /**
     * A material object used for the tests.
     */
    private Material m;

    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() { // no need to throw any exceptions!
        myProject = new Project("testing");
        m = new Material("test", "1", "1.11");
    }

    /**
     * Tests add/remove material
     */
    @Test
    public void materialTest() {
        //Material m = new Material("test", "1", "1.11");
        myProject.addMaterial("test", 1, "1.11");
        List<Material> mList = myProject.getProjectMaterials();
//        assertEquals("test", m.get(0).getMaterialName());
//        assertEquals(1, m.get(0).getQuantity());
//        assertEquals("1.11")
        assertEquals(mList.get(0).getMaterialName(), m.getMaterialName());
        assertEquals(mList.get(0).getCostPerOne(), m.getCostPerOne());
        assertEquals(mList.get(0).getQuantity(), m.getQuantity());
        assertEquals(mList.get(0).hashCode(), m.hashCode());
        assertTrue(mList.get(0).equals(m));
        myProject.removeMaterial(m);
        mList = myProject.getProjectMaterials();
        assertEquals(mList.size(), 0);
    }

    /**
     * Test toString features including load
     */
    @Test
    public void toStringTests() {
        myProject.addMaterial(m.getMaterialName(), m.getQuantity(), m.getCostPerOne());
        assertEquals(myProject.toString(), "testing$$$test,1,1.11%%%");
        Project temp = new Project(null);
        temp = temp.loadProject("testing$$$test,1,1.11%%%");
        assertEquals(temp.toString(), "testing$$$test,1,1.11%%%");
    }

    /**
     * testing getTotalCost()
     */
    @Test
    public void totalCostTest() {
        myProject.addMaterial(m.getMaterialName(), m.getQuantity(), m.getCostPerOne());
        myProject.addMaterial(m.getMaterialName(), m.getQuantity(), m.getCostPerOne());
        myProject.addMaterial(m.getMaterialName(), m.getQuantity(), m.getCostPerOne());
        assertEquals(myProject.getTotalCost(), "3.33");
    }

}