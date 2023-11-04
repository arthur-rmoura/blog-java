package com.api.core.appl.picture.repository.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findAll(pageable);
	}

	@Override
	public Page<Picture> listPictureByAlbumAndId(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findByAlbumAndId(filter.getAlbumId(), filter.getPictureId(), pageable);
	}


	@Override
	public Page<Picture> listPictureByAlbum(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findByAlbum(filter.getAlbumId(), pageable);
	}
	
	@Override
	public Page<Picture> listPictureById(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findById(filter.getPictureId(), pageable);
	}
	
	@Override
	public Picture findPictureById(Filter filter) {
		return pictureRepositoryData.findById(filter.getPictureId());
	}

	@Override
	public Picture createPicture(Picture picture) {
		return pictureRepositoryData.save(picture);
	}

	
}
