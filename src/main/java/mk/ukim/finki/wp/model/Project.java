package mk.ukim.finki.wp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Projects")
public class Project {

	@Id
	private String name;

	public Project(String name) {
		this.name = name;
	}

	public Project() {
	}

	public String getName() {
		return name;
	}
}
