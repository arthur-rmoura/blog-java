package com.api.core.appl.post.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.post.Post;

public interface PostRepositoryData extends PagingAndSortingRepository<Post, Long> {
	
	Page<Post> findAll(Pageable pageable);

	Page<Post> findByDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<Post> findByDateTimestampAndUser(long dateTimestampF1, Long userId, Pageable pageable);

	//@Query("SELECT dp FROM DadosPosicao dp WHERE dp.epochSecondPosicao between :timestampPosicaoInicio and :timestampPosicaoFim and dp.timezonePosicao = :timezonePosicao")
	Page<Post> findByUser(Long userId, Pageable pageable);
	
}
