package mk.ukim.finki.wp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mk.ukim.finki.wp.service.impl.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="WorkLogs")
public class WorkLog {
	@ManyToOne
	private Project project;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date datum;
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Tag> tags;
	private float hours;
	private String description;
	private boolean isHoliday;
	
	public WorkLog(Project project, Date datum, List<Tag> tags,
			float hours, String description, boolean isHoliday) {
		this.project = project;
		this.datum = datum;
		this.tags = tags;
		this.hours = hours;
		this.description = description;
		this.isHoliday = isHoliday;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHoliday(boolean holiday) {
		isHoliday = holiday;
	}

	public WorkLog() {
	}

	public Long getId() {
		return id;
	}

	public Project getProject() {
		return project;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDatum() {
		return datum;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public float getHours() {
		return hours;
	}

	public String getDescription() {
		return description;
	}

	public boolean isHoliday() {
		return isHoliday;
	}
}

