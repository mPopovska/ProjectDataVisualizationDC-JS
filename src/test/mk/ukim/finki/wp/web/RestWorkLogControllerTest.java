package mk.ukim.finki.wp.web;

import junit.framework.TestCase;
import mk.ukim.finki.wp.model.Project;
import mk.ukim.finki.wp.model.Tag;
import mk.ukim.finki.wp.model.WorkLog;
import mk.ukim.finki.wp.service.impl.WorkLogService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Matea on 20.09.2016.
 */
public class RestWorkLogControllerTest extends TestCase {

    @InjectMocks RestWorkLogController rwlc = new RestWorkLogController();

    WorkLog first;
    WorkLog second;

    Project project1;
    Project project2;

    Tag tag1;
    Tag tag2;
    Tag tag3;
    Tag tag4;

    File nofile;
    File emptyFile;
    File file;

    @Rule
    public final ExpectedException exc = ExpectedException.none();

    @Mock
    @Autowired
    private WorkLogService wlsMock = Mockito.mock(WorkLogService.class);

    public void setUp() throws Exception {
        super.setUp();

        project1 = new Project("FINKI1");
        project2 = new Project("FINKI2");

        tag1 = new Tag("TAG1");
        tag2 = new Tag("TAG2");
        tag3 = new Tag("TAG3");
        tag4 = new Tag("TAG4");

        ArrayList<Tag> list1 = new ArrayList<Tag>();
        list1.add(tag1);
        list1.add(tag2);

        ArrayList<Tag> list2 = new ArrayList<Tag>();
        list2.add(tag3);
        list2.add(tag4);

        first = new WorkLog(project1, new Date(), list1, 5, "opis1", false);
        second = new WorkLog(project2, new Date(), list2, 5, "opis2", false);

        nofile = new File("");
        file = new File("WorkLogs.xlsx");
    }

    @Test
    public void testGetWorkLogs() throws Exception {
    

        when(wlsMock.listWorkLogs()).thenReturn(Arrays.asList(first,second));


        List<WorkLog> list = wlsMock.listWorkLogs();
        verify(wlsMock, times(1)).listWorkLogs();


    }

    @Test
    public void testGetTags() throws Exception {
        when(wlsMock.listTags()).thenReturn(Arrays.asList(tag1.getName(),tag2.getName(),tag3.getName(),tag4.getName()));


        List<String> list = wlsMock.listTags();
        verify(wlsMock, times(1)).listTags();
    }

    @Test
    public void testGetProjects() throws Exception {
        when(wlsMock.listProjects()).thenReturn(Arrays.asList(project1.getName(),project2.getName()));


        List<String> list = wlsMock.listProjects();
        verify(wlsMock, times(1)).listProjects();
    }

    @Test
    public void testDeleteAll() throws Exception {
        wlsMock.deleteAll();
        verify(wlsMock, times(1)).deleteAll();
    }

    public void testAfterPropertiesSet() throws Exception {

    }

    @Test
    public void testAddFromFileFileDoesNotExist() throws Exception {
        FileNotFoundException e = null;

        try{
            rwlc.addFromFile(nofile);
        } catch (FileNotFoundException fnfe) {
            e = fnfe;
        }

        assertTrue(e instanceof FileNotFoundException);

    }

    @Test
    public void testAddFromFileTrue() throws Exception {
        NullPointerException e = null;

        try{
            rwlc.addFromFile(file);
        } catch (NullPointerException fnfe) {
            e = fnfe;
        }

        assertTrue(e instanceof NullPointerException);

    }
}