package mk.ukim.finki.wp.repository;

import junit.framework.TestCase;
import mk.ukim.finki.wp.model.Tag;
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
public class TagRepositoryTest extends TestCase {

    @Mock
    @Autowired
    private TagRepository tr;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        tr = Mockito.mock(TagRepository.class);
    }

    @Test
    public void testFindById() throws Exception {
        final Tag tag = new Tag("Testing");

        Mockito.when(tr.findById(tag.getName())).thenReturn(tag);

        Tag tagTest = tr.findById(tag.getName());
        Mockito.verify(tr).findById(tag.getName());
        assertEquals(tag, tagTest);
    }

    @Test
    public void testFindAll() throws Exception {
        final ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Testing"));

        Mockito.when(tr.findAll()).thenReturn(tags);

        List<Tag> list = new ArrayList<Tag>();
        list = tr.findAll();

        assertEquals(list,  tags);
        Mockito.verify(tr).findAll();
    }

    @Test
    public void testCreate() throws Exception {
        final Tag tag = new Tag("Testing");

        tr.create(tag);

        Mockito.verify(tr).create(tag);
    }

}