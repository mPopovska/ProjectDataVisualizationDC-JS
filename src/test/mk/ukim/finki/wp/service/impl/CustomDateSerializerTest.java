package mk.ukim.finki.wp.service.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

/**
 * Created by Matea on 20.09.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomDateSerializerTest extends TestCase {

    @Mock
    private JsonGenerator gen;

    private CustomDateSerializer cds;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        cds = new CustomDateSerializer();
        gen = Mockito.mock(JsonGenerator.class);
    }

    @Test
    public void testSerialize() throws Exception {
        Date date = new Date(2014 - 1900, 12 - 1, 12);

        cds.serialize(date,gen, null);

        Mockito.verify(gen).writeString("12 12 2014 00:00:00");
    }
}