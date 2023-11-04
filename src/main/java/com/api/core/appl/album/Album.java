package com.api.core.appl.album;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.api.core.appl.picture.Picture;
import com.api.core.appl.user.User;

@Entity
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = true)
	private Long dateTimestamp;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
	private List<Picture> pictureList;
	
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private User user;

	public Album(String name, String description, Long dateTimestamp, List<Picture> pictureList, User user) {
		super();
		this.name = name;
		this.description = description;
		this.dateTimestamp = dateTimestamp;
		this.pictureList = pictureList;
		this.user = user;
	}

	public Album(Long id) {
		super();
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

	public Long getDateTimestamp() {
		return dateTimestamp;
	}

	public void setDateTimestamp(Long dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
