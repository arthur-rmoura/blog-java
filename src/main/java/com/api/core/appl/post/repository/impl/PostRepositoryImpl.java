package com.api.core.appl.post.repository.impl;


import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.core.appl.post.Post;
import com.api.core.appl.post.repository.spec.PostRepository;
import com.api.core.appl.post.repository.spec.PostRepositoryData;
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
		return postRepositoryData.findByDateTimestamp(UtilLibrary.getDateTimestampF1(filter.getDate()), pageable);
	}
	
	@Override
	public Page<Post> listPostsByDateAndUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return postRepositoryData.findByDateTimestampAndUser(UtilLibrary.getDateTimestampF1(filter.getDate()), filter.getUserId(), pageable);
	}


	@Override
	public Page<Post> listPostsByUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return postRepositoryData.findByUser(filter.getUserId(), pageable);

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

}
