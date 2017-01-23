package mk.ukim.finki.wp.web;

import junit.framework.TestCase;
import mk.ukim.finki.wp.service.impl.WorkLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Matea on 20.09.2016.
 */
public class FileUploadControllerTest extends TestCase {

    private MockMvc mockMvc;

    @Autowired
    WorkLogService workLogServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testProvideUploadInfo() throws Exception {

    }

    public void testAddWorkLog() throws Exception {
        
    }

    public void testAddNewRow() throws Exception {

    }

    public void testGetFile() throws Exception {

    }

    @Test
    public void testHandleFileUpload() throws Exception {
        /*RedirectAttributes atr = redirectAttributes.addFlashAttribute("message", "Successfully added..");


        addAttribute in interface Model
                Parameters:
        attributeName - the name of the model attribute (never null)
        attributeValue - the model attribute value (can be null)*/
    }

    public void testMultipartToFile() throws Exception {

    }

    public void testConvert() throws Exception {

    }
}