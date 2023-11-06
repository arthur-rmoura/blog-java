package com.api.core.appl.album.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.album.Album;
import com.api.core.appl.user.User;


public interface AlbumRepositoryData extends PagingAndSortingRepository<Album, Long> {
	
	Page<Album> findAll(Pageable pageable);

	Page<Album> findByDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<Album> findByDateTimestampAndUser(long dateTimestampF1, User user, Pageable pageable);

	Page<Album> findByUser(User user, Pageable pageable);

	@Query("SELECT alb FROM Album alb WHERE alb.dateTimestamp between :dateTimestampStart and :dateTimestampEnd and alb.user.id = :userId")
	Page<Album> findIntervalAndUser(Long dateTimestampStart, Long dateTimestampEnd, Long userId, Pageable pageable);

	@Query("SELECT alb FROM Album alb WHERE alb.dateTimestamp between :dateTimestampStart and :dateTimestampEnd")
	Page<Album> findInterval(Long dateTimestampStart, Long dateTimestampEnd, Pageable pageable);

	
}
