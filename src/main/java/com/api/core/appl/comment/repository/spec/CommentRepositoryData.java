package com.api.core.appl.comment.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.comment.Comment;


public interface CommentRepositoryData extends PagingAndSortingRepository<Comment, Long> {
	
	Page<Comment> findAll(Pageable pageable);

	Page<Comment> findByDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<Comment> findByDateTimestampAndUser(long dateTimestampF1, Long userId, Pageable pageable);

	//@Query("SELECT dp FROM DadosPosicao dp WHERE dp.epochSecondPosicao between :timestampPosicaoInicio and :timestampPosicaoFim and dp.timezonePosicao = :timezonePosicao")
	Page<Comment> findByUser(Long userId, Pageable pageable);

	
}
