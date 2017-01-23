package mk.ukim.finki.wp.model;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Matea on 20.09.2016.
 */
public class TagTest extends TestCase {

    private Tag tag;

    public void setUp() throws Exception {
        super.setUp();
        tag = new Tag();
    }

    @Test
    public void testGetNameNull() throws Exception {
        assertEquals(null, tag.getName());
    }

    @Test
    public void testGetNameNotNull() throws Exception {
        tag = new Tag("commercial");
        assertEquals("commercial", tag.getName());
    }
}