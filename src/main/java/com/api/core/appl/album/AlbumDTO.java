package com.api.core.appl.album;

import java.io.Serializable;

public class AlbumDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;
	
	private String date;

	private String description;
	
	private Long userId;

	
	public AlbumDTO(Long id, String name, String date, String description, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.description = description;
		this.userId = userId;
	}

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	

}
