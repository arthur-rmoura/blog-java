package com.api.core.appl.picture;

import java.io.Serializable;

public class PictureDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pictureId;

	private String name;

	private String date;

	private byte[] data;

	protected PictureDTO() {

	}

	public PictureDTO(Long pictureId, String name, String date, byte[] data) {
		super();
		this.pictureId = pictureId;
		this.name = name;
		this.date = date;
		this.data = data;
	}

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
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

}
