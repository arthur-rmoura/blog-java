package com.api.core.appl.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.post.Post;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String firstName;

	@Column(nullable = false, length = 100)
	private String lastName;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = true)
	private Long birthDateTimestamp;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> postList;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Comment> commentList;

	protected User() {
		// No args constructor required by JPA
		// Defined as protected, since it shouldn't be used directly
	}

	public User(String firstName, String lastName, String email, String password, Long birthDateTimestamp) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDateTimestamp = birthDateTimestamp;
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getBirthDateTimestamp() {
		return birthDateTimestamp;
	}

	public void setBirthDateTimestamp(Long birthDateTimestamp) {
		this.birthDateTimestamp = birthDateTimestamp;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

}