package com.api.core.appl.picture.repository.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.core.appl.album.Album;
import com.api.core.appl.picture.Picture;
import com.api.core.appl.picture.repository.spec.PictureRepository;
import com.api.core.appl.picture.repository.spec.PictureRepositoryData;
import com.api.core.appl.util.Filter;

@Repository
public class PictureRepositoryImpl implements PictureRepository{
	
	private final PictureRepositoryData pictureRepositoryData;
	
	public PictureRepositoryImpl(PictureRepositoryData pictureRepositoryData) {
		super();
		this.pictureRepositoryData = pictureRepositoryData;
	}


	@Override
	public Page<Picture> listPicture(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return pictureRepositoryData.findAll(pageable);
	}


	@Override
	public Page<Picture> listPictureByAlbum(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return pictureRepositoryData.findByAlbum(new Album(filter.getAlbumId()), pageable);
	}
	
	@Override
	public Picture findPictureById(Filter filter) {
		try {
			Picture picture = pictureRepositoryData.findById(filter.getPictureId()).get();
			return picture;
		}
		catch (NoSuchElementException  e) {
			return new Picture("", 0L, null);
		}
	}

	@Override
	public Picture createPicture(Picture picture) {
		return pictureRepositoryData.save(picture);
	}

	@Override
	public Page<Picture> listPictureByAlbumAndName(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		Page<Picture> picturePage =  pictureRepositoryData.findByAlbumAndName(new Album(filter.getAlbumId()),filter.getPictureName(), pageable);
		
		if(picturePage.getContent().size() == 0) {
			picturePage = pictureRepositoryData.findNameLikeAndAlbumId(filter.getPictureName(), filter.getAlbumId(), pageable);
		}
		return picturePage;
	}

	@Override
	public Page<Picture> findPictureByName(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		Page<Picture> picturePage = pictureRepositoryData.findByName(filter.getPictureName(), pageable);
		if(picturePage.getContent().size() == 0) {
			picturePage = pictureRepositoryData.findNameLike(filter.getPictureName(), pageable);
		}
		return picturePage;
	}


	@Override
	public Picture updatePicture(Picture picture) {
		return pictureRepositoryData.save(picture);
	}


	@Override
	public void deletePicture(Long pictureId) {
		pictureRepositoryData.deleteById(pictureId);
		
	}
}
