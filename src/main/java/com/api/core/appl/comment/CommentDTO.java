package com.api.core.appl.comment;

import java.io.Serializable;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String date;

	private String textContent;

	private Long userId;

	private Long postId;

	protected CommentDTO() {

	}

	public CommentDTO(Long id, String date, String textContent, Long userId, Long postId) {
		super();
		this.id = id;
		this.date = date;
		this.textContent = textContent;
		this.userId = userId;
		this.postId = postId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}
