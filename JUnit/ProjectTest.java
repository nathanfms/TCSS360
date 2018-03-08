import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import model.Project;

/**
 * A class to test the constructors and methods in the Project class.
 *
 * @author Hien Doan
 * @version March 2, 2018
 */
public class ProjectTest {

    /** A tolerance used when comparing double values for equality. */
    private static final double TOLERANCE = .000001;

    /** A Project object used for the tests. */
    private Project myProject;

    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() { // no need to throw any exceptions!
        myProject = new Project();
    }