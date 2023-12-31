package com.api.core.appl.picture;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.api.core.appl.album.Album;

@Entity
public class Picture implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 150)
	private String name;

	@Column(nullable = true)
	private Long dateTimestamp;

	@Column(nullable = false)
	private String idAmazonS3;

	@ManyToOne(optional=false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Album album;

	protected Picture() {
		// No args constructor required by JPA
		// Defined as protected, since it shouldn't be used directly
	}

	public Picture(String name, Long dateTimestamp, String idAmazonS3) {
		super();
		this.name = name;
		this.dateTimestamp = dateTimestamp;
		this.idAmazonS3 = idAmazonS3;
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

	public Long getDateTimestamp() {
		return dateTimestamp;
	}

	public void setDateTimestamp(Long dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}

	public String getIdAmazonS3() {
		return idAmazonS3;
	}

	public void setIdAmazonS3(String idAmazonS3) {
		this.idAmazonS3 = idAmazonS3;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
}