package mk.ukim.finki.wp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tags")
public class Tag {
	
	@Id
	private String name;

	public Tag(String name) {
		this.name = name;
	}

	public Tag() {
	}

	public String getName() {
		return name;
	}
}
