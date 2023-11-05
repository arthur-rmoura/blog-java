package com.api.core.appl.picture.repository.spec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.album.Album;
import com.api.core.appl.picture.Picture;

public interface PictureRepositoryData extends PagingAndSortingRepository<Picture, Long> {

	Page<Picture> findAll(Pageable pageable);

	Page<Picture> findByAlbum(Album album, Pageable pageable);

	Page<Picture> findByAlbumAndName(Album album, String pictureName, Pageable pageable);

	Page<Picture> findByName(String pictureName, Pageable pageable);

	@Query("SELECT pic FROM Picture pic WHERE pic.name like CONCAT('%',:pictureName,'%')")
	Page<Picture> findNameLike(String pictureName, Pageable pageable);

	@Query("SELECT pic FROM Picture pic WHERE pic.name like CONCAT('%',:pictureName,'%') and pic.album.id = :albumId")
	Page<Picture> findNameLikeAndAlbumId(String pictureName, Long albumId, Pageable pageable);

}
