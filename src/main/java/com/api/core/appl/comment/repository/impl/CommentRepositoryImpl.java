package com.api.core.appl.comment.repository.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.comment.repository.spec.CommentRepository;
import com.api.core.appl.comment.repository.spec.CommentRepositoryData;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;

@Repository
public class CommentRepositoryImpl implements CommentRepository{
	
	private final CommentRepositoryData commentRepositoryData;
	
	public CommentRepositoryImpl(CommentRepositoryData commentRepositoryData) {
		super();
		this.commentRepositoryData = commentRepositoryData;
	}


	@Override
	public Page<Comment> listComments(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return commentRepositoryData.findAll(pageable);
	}


	@Override
	public Comment createComment(Comment comment) {
		return commentRepositoryData.save(comment);
	}


	@Override
	public Page<Comment> listCommentsByDate(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return commentRepositoryData.findByDateTimestamp(UtilLibrary.getDateTimestampF1(filter.getDate()), pageable);
	}
	
	@Override
	public Page<Comment> listCommentsByDateAndUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return commentRepositoryData.findByDateTimestampAndUser(UtilLibrary.getDateTimestampF1(filter.getDate()), filter.getUserId(), pageable);
	}


	@Override
	public Page<Comment> listCommentsByUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return commentRepositoryData.findByUser(filter.getUserId(), pageable);

	}


	@Override
	public Comment findCommentById(Filter filter) {
		try {
			Comment comment = commentRepositoryData.findById(filter.getCommentId()).get();
			return comment;
		}
		catch (NoSuchElementException  e) {
			return new Comment(0L, "", null, null);
		}
	}

}
