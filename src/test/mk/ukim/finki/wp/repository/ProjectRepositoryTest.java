package mk.ukim.finki.wp.repository;

import junit.framework.TestCase;
import mk.ukim.finki.wp.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matea on 20.09.2016.
 */
public class ProjectRepositoryTest extends TestCase {

    @Mock
    @Autowired
    private ProjectRepository pr;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        pr = Mockito.mock(ProjectRepository.class);
    }

    @Test
    public void testFindById() throws Exception {
        final Project project = new Project("Matea");

        Mockito.when(pr.findById(project.getName())).thenReturn(project);

        Project projectTest = pr.findById(project.getName());
        Mockito.verify(pr).findById(project.getName());
        assertEquals(project,projectTest);
    }

    @Test
    public void testFindAll() throws Exception {
        final ArrayList<Project> projects = new ArrayList<Project>();
        projects.add(new Project("Matea"));
        projects.add(new Project("Elena"));
        projects.add(new Project("Timon"));

        Mockito.when(pr.findAll()).thenReturn(projects);

        List<Project> list = new ArrayList<Project>();
        list = pr.findAll();

        assertEquals(list,  projects);
        Mockito.verify(pr).findAll();
    }

    @Test
    public void testCreate() throws Exception {
        final Project project = new Project("Matea");

        pr.create(project);

        Mockito.verify(pr).create(project);
    }

}