package mk.ukim.finki.wp.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matea on 20.09.2016.
 */
public class WorkLogTest extends TestCase {

    private WorkLog workLog;

    public void setUp() throws Exception {
        super.setUp();
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("commercial"));
        workLog = new WorkLog(new Project("FINKI1"), new Date(), tags, 6, "project data visualization", false);
    }

    public void testGetId() throws Exception {

    }

    public void testGetProject() throws Exception {

    }

    public void testGetDatum() throws Exception {

    }

    public void testGetTags() throws Exception {

    }

    public void testGetHours() throws Exception {

    }

    public void testGetDescription() throws Exception {

    }

    public void testIsHoliday() throws Exception {

    }
}