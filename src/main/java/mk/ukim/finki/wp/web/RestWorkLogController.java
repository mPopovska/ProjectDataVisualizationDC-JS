package mk.ukim.finki.wp.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mk.ukim.finki.wp.model.WorkLog;
import mk.ukim.finki.wp.service.WorkLogService;

// HSSF for .xls   XSSF for .xlsx
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;

@RestController
@RequestMapping(value="/rest")
public class RestWorkLogController {//implements InitializingBean {
	@Autowired
	WorkLogService wls;
	
	
	@RequestMapping(method = RequestMethod.GET,value="/getWorkLogs")
	public List<WorkLog> getWorkLogs(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
		System.out.println("Rest Controller getWorkLogs");
		return wls.listWorkLogs();
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/getTags")
	public List<String> getTags(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
		System.out.println("Rest Controller getTags");
		return wls.listTags();
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/getProjects")
	public List<String> getProjects(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
		System.out.println("Rest Controller getProjects");
		return wls.listProjects();
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/deleteAll")
	public String deleteAll(){
		wls.deleteAll();
		return "done";
	}
	
	/*@RequestMapping(method = RequestMethod.POST,value="/addWorkLog")
	public String addWorkLog(HttpServletRequest request, HttpServletResponse response){
		String project = request.getParameter("project"), tags = request.getParameter("tags"), desc = request.getParameter("desc");
		Date date = new Date(new Integer(request.getParameter("year")),
				new Integer(request.getParameter("month")), new Integer(request.getParameter("day")));
		int hours = new Integer(request.getParameter("hours"));
		boolean isHoliday = new Boolean(request.getParameter("isHoliday"));
		wls.updateOnRow(project, date, hours, tags, desc, isHoliday);
		return "done";
	}*/




	public void afterPropertiesSet() throws Exception {
		addFromFile(new File("WorkLogs.xlsx"));
	}
	public void addFromFile(File file) throws Exception {
		System.out.println("rest worklog init...");
		//File file = new File("WorkLogs.xlsx");
		System.out.println("path: "+file.getAbsolutePath());
		if(!file.exists() || !file.isFile()){
			System.out.println("the file does not exist");
		}
		//wls.deleteAll();
		// HSSF for .xls   XSSF for .xlsx
		//HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		//HSSFSheet worksheet = workbook.getSheetAt(0);
		XSSFSheet worksheet = workbook.getSheetAt(0);
		CreationHelper createHelper = workbook.getCreationHelper();
		
		for(int i=1;i <= worksheet.getLastRowNum();i++){
			//HSSFRow row = worksheet.getRow(i);
			XSSFRow row = worksheet.getRow(i);
			String project, tags, desc;
			Date date;
			int hours;
			boolean isHoliday; 
			try {
				row.getCell(1).getCellStyle().setDataFormat(
				        createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
				project = row.getCell(0).getStringCellValue();
				date = row.getCell(1).getDateCellValue();
				hours =	(int)row.getCell(2).getNumericCellValue();
				tags = row.getCell(3).getStringCellValue();
				desc = row.getCell(4).getStringCellValue();
				isHoliday =	row.getCell(5).getBooleanCellValue(); 
			}
			catch (Exception exception){
				System.out.println("Cannot read row no. "+(i+1));
				continue;
			}
			wls.updateOnRow(project, date, hours, tags, desc, isHoliday);
		}	
	}
}
