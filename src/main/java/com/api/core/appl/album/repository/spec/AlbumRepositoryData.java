package com.api.core.appl.album.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.album.Album;


public interface AlbumRepositoryData extends PagingAndSortingRepository<Album, Long> {
	
	Page<Album> findAll(Pageable pageable);

	Page<Album> findByDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<Album> findByDateTimestampAndUser(long dateTimestampF1, Long userId, Pageable pageable);

	//@Query("SELECT dp FROM DadosPosicao dp WHERE dp.epochSecondPosicao between :timestampPosicaoInicio and :timestampPosicaoFim and dp.timezonePosicao = :timezonePosicao")
	Page<Album> findByUser(Long userId, Pageable pageable);

	
}
