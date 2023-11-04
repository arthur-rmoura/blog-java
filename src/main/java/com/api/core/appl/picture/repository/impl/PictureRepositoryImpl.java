package com.api.core.appl.picture.repository.impl;

import java.util.NoSuchElementException;

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
	public Page<Picture> listPictureByAlbum(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findByAlbum(filter.getAlbumId(), pageable);
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
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findByAlbumAndName(filter.getAlbumId(),filter.getPictureName(), pageable);
	}

	@Override
	public Page<Picture> findPictureByName(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageNumber());
		return pictureRepositoryData.findByName(filter.getPictureName(), pageable);
	}
}
