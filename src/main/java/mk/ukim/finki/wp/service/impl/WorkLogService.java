package mk.ukim.finki.wp.service.impl;

import mk.ukim.finki.wp.model.Project;
import mk.ukim.finki.wp.model.Tag;
import mk.ukim.finki.wp.model.WorkLog;
import mk.ukim.finki.wp.repository.ProjectRepository;
import mk.ukim.finki.wp.repository.TagRepository;
import mk.ukim.finki.wp.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class WorkLogService implements mk.ukim.finki.wp.service.WorkLogService {
	
	@Autowired
	WorkLogRepository wlr;	
	@Autowired
	ProjectRepository pr;	
	@Autowired
	TagRepository tr;
	
	public List<WorkLog> listWorkLogs() {
		return wlr.findAll();
	}
	
	public List<String> listTags() {
		ArrayList<String> tags =new ArrayList<String>();
		for(Tag tag : tr.findAll()){
			tags.add(tag.getName());
		}
		return tags;
	}
	
	public List<String> listProjects() {
		ArrayList<String> projects =new ArrayList<String>();
		for(Project project : pr.findAll()){
			projects.add(project.getName());
		}
		return projects;
	}

	public void updateOnRow(String project, Date date, int hours, String tags, String desc, boolean isHoliday) {
		System.out.println("updateOnRow...");
		StringTokenizer tokenizer = new StringTokenizer(tags, ", ");
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		while (tokenizer.hasMoreTokens()){
			String next = tokenizer.nextToken();
			System.out.println("."+next+".");
			if(tr.findById(next)==null) tr.create(new Tag(next));
			tagList.add(tr.findById(next));
		}
		if(pr.findById(project)==null) pr.create(new Project(project));
		WorkLog workLog = new WorkLog(pr.findById(project), date, tagList, hours, desc, isHoliday);
		wlr.create(workLog);
	}
	
	public void deleteAll() {
		wlr.deleteAll();
	}
}
