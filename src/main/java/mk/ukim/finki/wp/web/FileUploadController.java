package mk.ukim.finki.wp.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Collectors;

import mk.ukim.finki.wp.service.WorkLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FileUploadController {

	@Autowired
	RestWorkLogController restWorkLogController;
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	public static final String ROOT = "upload-dir";

	private final ResourceLoader resourceLoader;

	@Autowired
	WorkLogService wls;

	@Autowired
	public FileUploadController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String provideUploadInfo(Model model) throws IOException {

		/*model.addAttribute("files", Files.walk(Paths.get(ROOT))
				.filter(path -> !path.equals(Paths.get(ROOT)))
				.map(path -> Paths.get(ROOT).relativize(path))
				.map(path -> linkTo(methodOn(FileUploadController.class).getFile(path.toString())).withRel(path.toString()))
				.collect(Collectors.toList()));*/

		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST,value="/addWorkLog")
	public String addWorkLog(HttpServletRequest request, HttpServletResponse response){
		String project = request.getParameter("project");
		String tags = request.getParameter("tags"), desc = request.getParameter("desc");
		//Date date = new Date(new Integer(request.getParameter("year")),
		//		new Integer(request.getParameter("month")), new Integer(request.getParameter("day")));
		String d = request.getParameter("date");
		String[] dt = d.split("-");
		Date date = new Date(Integer.parseInt(dt[0]) - 1900, Integer.parseInt(dt[1]) - 1, Integer.parseInt(dt[2]));
		System.out.println(Integer.parseInt(dt[0]));
		int hours = new Integer(request.getParameter("hours"));
		boolean isHoliday = false;
		if (request.getParameter("isHoliday") != null){
			isHoliday = true;
		};
		System.out.println(project + " " + date + " " + tags + " " + hours + " " + isHoliday);
		wls.updateOnRow(project, date, hours, tags, desc, isHoliday);
		return "addRow";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addWorkLog")
	public String addNewRow(Model model) throws IOException {
		return "addRow";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
	@ResponseBody
	public ResponseEntity<?> getFile(@PathVariable String filename) {

		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) throws Exception {

		if (!file.isEmpty()) {
			try {
				Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + file.getOriginalFilename() + "!");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
		}

		System.out.println("fileHandling...");
		restWorkLogController.addFromFile(convert(file));
		return "redirect:/";
	}
	
	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
		System.out.println("multipatToFile start "+ multipart.getOriginalFilename());
		File convFile = new File( multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}
	public File convert(MultipartFile file) throws Exception
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
}
