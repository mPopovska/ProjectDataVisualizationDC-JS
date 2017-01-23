package mk.ukim.finki.wp.service.impl;

import junit.framework.TestCase;
import mk.ukim.finki.wp.model.Project;
import mk.ukim.finki.wp.model.Tag;
import mk.ukim.finki.wp.model.WorkLog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Matea on 20.09.2016.
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/mvc-core-config.xml",
"file:src/main/resources/spring/persistence-config.xml",
"file:src/main/resources/spring/mvc-view-config.xml",
"file:src/main/resources/spring/business-config.xml"})
public class WorkLogServiceTest extends TestCase {

    @Mock
    private WorkLogService mockWorkLogService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        mockWorkLogService = Mockito.mock(WorkLogService.class);
    }

    @Test
    public void testListWorkLogs() throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Testing"));
        ArrayList<WorkLog> workLogs = new ArrayList<WorkLog>();
        final WorkLog entity1 = new WorkLog(new Project("FINKI1"), new Date(), tags, 6, "project data visualization", false);
        final WorkLog entity2 = new WorkLog(new Project("FINKI2"), new Date(), tags, 6, "project data visualization 2", false);
        workLogs.add(entity1);
        workLogs.add(entity2);

        Mockito.when(mockWorkLogService.listWorkLogs()).thenReturn(workLogs);;

        List<WorkLog> list = mockWorkLogService.listWorkLogs();

        assertEquals(list,  workLogs);
        Mockito.verify(mockWorkLogService).listWorkLogs();
        System.out.println(list.size());
    }

    @Test
    public void testListTags() throws Exception {
        final ArrayList<String> tags = new ArrayList<String>();
        tags.add(new Tag("Testing").getName());

        Mockito.when(mockWorkLogService.listTags()).thenReturn(tags);

        List<String> list = new ArrayList<String>();
        list = mockWorkLogService.listTags();

        assertEquals(list,  tags);
        Mockito.verify(mockWorkLogService).listTags();
        System.out.println(list.size());
    }

    @Test
    public void testListProjects() throws Exception {
        final ArrayList<String> projects = new ArrayList<String>();
        projects.add(new Project("Matea").getName());
        projects.add(new Project("Elena").getName());
        projects.add(new Project("Timon").getName());

        Mockito.when(mockWorkLogService.listProjects()).thenReturn(projects);

        List<String> list = new ArrayList<String>();
        list = mockWorkLogService.listProjects();

        assertEquals(list,  projects);
        Mockito.verify(mockWorkLogService).listProjects();
        System.out.println(list.size());
    }

    @Test
    public void testUpdateOnRow() throws Exception {
        mockWorkLogService.updateOnRow("FINKI1", new Date(2017 - 1900, 12 - 1, 12), 15, "Testing", "project data visualization", true);

        Mockito.verify(mockWorkLogService).updateOnRow("FINKI1", new Date(2017 - 1900, 12 - 1, 12), 15, "Testing", "project data visualization", true);
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockWorkLogService.deleteAll();
        Mockito.verify(mockWorkLogService).deleteAll();
    }
}