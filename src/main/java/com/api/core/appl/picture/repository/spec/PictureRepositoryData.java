package com.api.core.appl.picture.repository.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.picture.Picture;

public interface PictureRepositoryData extends PagingAndSortingRepository<Picture, Long> {

	Page<Picture> findAll(Pageable pageable);

	Page<Picture> findByAlbum(Long albumId, Pageable pageable);

	Page<Picture> findByAlbumAndName(Long albumId, String pictureName, Pageable pageable);

	Page<Picture> findByName(String pictureName, Pageable pageable);

}