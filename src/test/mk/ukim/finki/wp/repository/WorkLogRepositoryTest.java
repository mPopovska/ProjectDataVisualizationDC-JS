package mk.ukim.finki.wp.repository;

import junit.framework.TestCase;
import mk.ukim.finki.wp.model.Project;
import mk.ukim.finki.wp.model.Tag;
import mk.ukim.finki.wp.model.WorkLog;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Matea on 20.09.2016.
 */
public class WorkLogRepositoryTest extends TestCase {

    @Mock
    private WorkLogRepository wr;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        wr = Mockito.mock(WorkLogRepository.class);
    }

    @Test
    public  void testFindById() throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Testing"));
        final WorkLog entity = new WorkLog(new Project("FINKI1"), new Date(), tags, 6, "project data visualization", false);

        Mockito.when(wr.findById(entity.getId())).thenReturn(entity);

        WorkLog workLog = wr.findById(entity.getId());
        Mockito.verify(wr).findById(entity.getId());
        assertEquals(entity, workLog);
    }

    @Test
    public void testFindAll() throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Testing"));
        ArrayList<WorkLog> workLogs = new ArrayList<WorkLog>();
        final WorkLog entity1 = new WorkLog(new Project("FINKI1"), new Date(), tags, 6, "project data visualization", false);
        final WorkLog entity2 = new WorkLog(new Project("FINKI2"), new Date(), tags, 6, "project data visualization 2", false);
        workLogs.add(entity1);
        workLogs.add(entity2);

        Mockito.when(wr.findAll()).thenReturn(workLogs);

        List<WorkLog> list = new ArrayList<WorkLog>();
        list = wr.findAll();

        assertEquals(list,  workLogs);
        Mockito.verify(wr).findAll();
    }

    @Test
    public void testCreate() throws Exception {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Testing"));
        final WorkLog entity = new WorkLog(new Project("FINKI1"), new Date(), tags, 6, "project data visualization", false);

        wr.create(entity);

        Mockito.verify(wr).create(entity);
    }

    @Test
    public void testDeleteAll() throws Exception {
        wr.deleteAll();
        Mockito.verify(wr).deleteAll();
    }
}