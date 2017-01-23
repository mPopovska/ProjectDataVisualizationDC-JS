package mk.ukim.finki.wp.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Matea on 20.09.2016.
 */
public class ProjectTest extends TestCase {

    private Project project;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        project = new Project();
    }

    @Test
    public void testGetNameNull() throws Exception {
        assertEquals(null, project.getName());
    }

    @Test
    public void testGetNameNotNull() throws Exception {
        project = new Project("Matea");
        assertEquals("Matea", project.getName());
    }
}