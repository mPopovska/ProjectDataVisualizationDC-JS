package mk.ukim.finki.wp.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matea on 20.09.2016.
 */
public class WorkLogTest extends TestCase {

    private WorkLog workLog;
    private WorkLog workLogNull;
    Project project;
    Date date;
    ArrayList<Tag> tags;
    float hours;
    float hoursNull;
    String desc;
    boolean holiday;

    public void setUp() throws Exception {
        super.setUp();
        tags = new ArrayList<Tag>();
        tags.add(new Tag("commercial"));
        project = new Project("FINKI1");
        date = new Date(2014 - 1900, 12 - 1, 12);
        hours = 6;
        desc = "project data visualization";
        holiday = false;
        workLog = new WorkLog(project, date, tags, hours, desc, holiday);
        hoursNull = 0;
        workLogNull = new WorkLog();
    }

    public void testGetId() throws Exception {
        assertEquals(null, workLog.getId());
    }

    public void testGetProjectNull() throws Exception {
        assertEquals(null, workLogNull.getProject());
    }

    public void testGetDatumNull() throws Exception {
        assertEquals(null, workLogNull.getDatum());
    }

    public void testGetTagsNull() throws Exception {
        assertEquals(null, workLogNull.getTags());
    }

    public void testGetHoursNull() throws Exception {
        assertEquals(hoursNull, workLogNull.getHours());
    }

    public void testGetDescriptionNull() throws Exception {
        assertEquals(null, workLogNull.getDescription());
    }

    public void testIsHolidayNull() throws Exception {
        assertEquals(false, workLogNull.isHoliday());
    }

    public void testGetProject() throws Exception {
        assertEquals(project, workLog.getProject());
    }

    public void testGetDatum() throws Exception {
        assertEquals(date, workLog.getDatum());
    }

    public void testGetTags() throws Exception {
        assertEquals(tags, workLog.getTags());
    }

    public void testGetHours() throws Exception {
        assertEquals(hours, workLog.getHours());
    }

    public void testGetDescription() throws Exception {
        assertEquals(desc, workLog.getDescription());
    }

    public void testIsHoliday() throws Exception {
        assertEquals(holiday, workLog.isHoliday());
    }
}