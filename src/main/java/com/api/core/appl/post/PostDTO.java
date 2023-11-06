package com.api.core.appl.post;

import java.io.Serializable;
import java.util.ArrayList;

import com.api.core.appl.comment.CommentDTO;


public class PostDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String date;
	
	private String textContent;
	
	private Long userId;
	
	private ArrayList<CommentDTO> commentDTOList;
	
	protected PostDTO() {
	
	}
	
	public PostDTO(Long id, String date, String textContent, Long userId, ArrayList<CommentDTO> commentDTOList) {
		super();
		this.id = id;
		this.date = date;
		this.textContent = textContent;
		this.userId = userId;
		this.commentDTOList = commentDTOList;
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

	public ArrayList<CommentDTO> getCommentDTOList() {
		return commentDTOList;
	}

	public void setCommentDTOList(ArrayList<CommentDTO> commentDTOList) {
		this.commentDTOList = commentDTOList;
	}
	
}
