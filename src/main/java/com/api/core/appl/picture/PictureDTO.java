package com.api.core.appl.picture;

import java.io.Serializable;

public class PictureDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String date;
	
	private Long albumId;

	private byte[] data;

	protected PictureDTO() {

	}

	public PictureDTO(Long id, String name, String date, byte[] data, Long albumId) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.data = data;
		this.albumId = albumId;
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
}
