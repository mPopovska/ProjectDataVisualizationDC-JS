package mk.ukim.finki.wp.service;

import java.util.Date;
import java.util.List;

import mk.ukim.finki.wp.model.WorkLog;

public interface WorkLogService {
	public List<WorkLog> listWorkLogs();
	public List<String> listTags();
	public List<String> listProjects();
	public void updateOnRow(String project, Date date, int hours, String Tags, String desc, boolean isHoliday);
	public void deleteAll();
}
