package com.api.core.appl.comment.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.user.User;


public interface CommentRepositoryData extends PagingAndSortingRepository<Comment, Long> {
	
	Page<Comment> findAll(Pageable pageable);

	Page<Comment> findByDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<Comment> findByDateTimestampAndUser(long dateTimestampF1, User user, Pageable pageable);

	Page<Comment> findByUser(User user, Pageable pageable);

	
}
