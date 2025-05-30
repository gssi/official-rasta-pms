package it.gssi.cs.rastapms.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

	public static final String ADMIN_NAME = "administrator";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "description")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
