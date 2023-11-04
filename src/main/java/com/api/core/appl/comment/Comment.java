package com.api.core.appl.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.api.core.appl.post.Post;
import com.api.core.appl.user.User;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long dateTimestamp;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String textContent;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private User user;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private Post post;

	protected Comment() {
		// No args constructor required by JPA
		// Defined as protected, since it shouldn't be used directly
	}

	public Comment(Long dateTimestamp, String textContent, User user, Post post) {
		super();
		this.dateTimestamp = dateTimestamp;
		this.textContent = textContent;
		this.user = user;
		this.post = post;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}