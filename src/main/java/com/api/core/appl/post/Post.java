package com.api.core.appl.post;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.user.User;

@Entity
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long dateTimestamp;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String textContent;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Comment> commentList;
	
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	@JoinColumn(name="id", nullable=false)
	private User user;


	public Post(Long dateTimestamp, String textContent, List<Comment> commentList, User user) {
		super();
		this.dateTimestamp = dateTimestamp;
		this.textContent = textContent;
		this.commentList = commentList;
		this.user = user;
	}

	protected Post() {
		// No args constructor required by JPA
		// Defined as protected, since it shouldn't be used directly
	}

	public Post(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDateTimestamp() {
		return dateTimestamp;
	}

	public void setDateTimestamp(Long dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}