package com.api.core.appl.post.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.post.Post;
import com.api.core.appl.user.User;

public interface PostRepositoryData extends PagingAndSortingRepository<Post, Long> {
	
	Page<Post> findAll(Pageable pageable);

	Page<Post> findByDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<Post> findByDateTimestampAndUser(long dateTimestampF1, User user, Pageable pageable);

	Page<Post> findByUser(User user, Pageable pageable);

	@Query("SELECT post FROM Post post WHERE post.dateTimestamp between :dateTimestampInicio and :dateTimestampFim and post.user.id = :userId")
	Page<Post> findIntervalAndUser(Long dateTimestampInicio, Long dateTimestampFim, Long userId, Pageable pageable);

	@Query("SELECT post FROM Post post WHERE post.dateTimestamp between :dateTimestampInicio and :dateTimestampFim")
	Page<Post> findInterval(Long dateTimestamp, Long dateTimestampFim, Pageable pageable);
	
}
