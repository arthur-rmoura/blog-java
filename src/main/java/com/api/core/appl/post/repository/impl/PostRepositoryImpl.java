package com.api.core.appl.post.repository.impl;


import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.core.appl.post.Post;
import com.api.core.appl.post.repository.spec.PostRepository;
import com.api.core.appl.post.repository.spec.PostRepositoryData;
import com.api.core.appl.user.User;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;

@Repository
public class PostRepositoryImpl implements PostRepository{
	
	private final PostRepositoryData postRepositoryData;
	
	public PostRepositoryImpl(PostRepositoryData postRepositoryData) {
		super();
		this.postRepositoryData = postRepositoryData;
	}


	@Override
	public Page<Post> listPosts(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return postRepositoryData.findAll(pageable);
	}


	@Override
	public Post createPost(Post post) {
		return postRepositoryData.save(post);
	}


	@Override
	public Page<Post> listPostsByDate(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		
		Page<Post> pagePost = null;
		Long dateTimestamp;
		try {
			dateTimestamp = UtilLibrary.getDateTimestampF1(filter.getDate());
			pagePost = postRepositoryData.findByDateTimestamp(dateTimestamp, pageable);
		}
		catch (DateTimeParseException e) {
			dateTimestamp = UtilLibrary.getDateTimestampF2(filter.getDate());
			pagePost =  postRepositoryData.findInterval(dateTimestamp, dateTimestamp + 86400, pageable);
		}
		
		return pagePost;
	}
	
	@Override
	public Page<Post> listPostsByDateAndUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		
		Page<Post> pagePost = null;
		Long dateTimestamp;
		try {
			dateTimestamp = UtilLibrary.getDateTimestampF1(filter.getDate());
			pagePost = postRepositoryData.findByDateTimestampAndUser(dateTimestamp, new User(filter.getUserId()), pageable);
		}
		catch (DateTimeParseException e) {
			dateTimestamp = UtilLibrary.getDateTimestampF2(filter.getDate());
			pagePost =  postRepositoryData.findIntervalAndUser(dateTimestamp, dateTimestamp + 86400, filter.getUserId(), pageable);
		}
		
		return pagePost;
	}


	@Override
	public Page<Post> listPostsByUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return postRepositoryData.findByUser(new User(filter.getUserId()), pageable);

	}


	@Override
	public Post findPostById(Filter filter) {
		try {
			Post post = postRepositoryData.findById(filter.getPostId()).get();
			return post;
		}
		catch (NoSuchElementException  e) {
			return new Post(0L, "", new ArrayList<>(), null);
		}
	}


	@Override
	public Post updatePost(Post post) {
		return postRepositoryData.save(post);
	}


	@Override
	public void deletePost(Long postId) {
		postRepositoryData.deleteById(postId);
	}

}
