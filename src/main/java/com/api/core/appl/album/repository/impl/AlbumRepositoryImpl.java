package com.api.core.appl.album.repository.impl;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.core.appl.album.Album;
import com.api.core.appl.album.repository.spec.AlbumRepository;
import com.api.core.appl.album.repository.spec.AlbumRepositoryData;
import com.api.core.appl.user.User;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;

@Repository
public class AlbumRepositoryImpl implements AlbumRepository{
	
	private final AlbumRepositoryData albumRepositoryData;
	
	public AlbumRepositoryImpl(AlbumRepositoryData albumRepositoryData) {
		super();
		this.albumRepositoryData = albumRepositoryData;
	}


	@Override
	public Page<Album> listAlbums(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return albumRepositoryData.findAll(pageable);
	}


	@Override
	public Album createAlbum(Album album) {
		return albumRepositoryData.save(album);
	}

	@Override
	public Album updateAlbum(Album album) {
		return albumRepositoryData.save(album);
	}
	
	@Override
	public void deleteAlbum(Long albumId) {
		albumRepositoryData.deleteById(albumId);
	}

	@Override
	public Page<Album> listAlbumsByDate(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		Long dateTimestamp;
		Page<Album> pageAlbum = null;
		try {
			dateTimestamp = UtilLibrary.getDateTimestampF1(filter.getDate());
			pageAlbum = albumRepositoryData.findByDateTimestamp(dateTimestamp, pageable);
		}
		catch (DateTimeParseException e) {
			dateTimestamp = UtilLibrary.getDateTimestampF2(filter.getDate());
			pageAlbum =  albumRepositoryData.findInterval(dateTimestamp, dateTimestamp + 86400, pageable);
		}
		
		return pageAlbum;
	}
	
	@Override
	public Page<Album> listAlbumsByDateAndUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		Long dateTimestamp;
		Page<Album> pageAlbum = null;
		try {
			dateTimestamp = UtilLibrary.getDateTimestampF1(filter.getDate());
			pageAlbum = albumRepositoryData.findByDateTimestampAndUser(dateTimestamp, new User(filter.getUserId()), pageable);
		}
		catch (DateTimeParseException e) {
			dateTimestamp = UtilLibrary.getDateTimestampF2(filter.getDate());
			pageAlbum =  albumRepositoryData.findIntervalAndUser(dateTimestamp, dateTimestamp + 86400, filter.getUserId(), pageable);
		}
		
		return pageAlbum;
		
	}


	@Override
	public Page<Album> listAlbumsByUser(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return albumRepositoryData.findByUser(new User(filter.getUserId()), pageable);

	}


	@Override
	public Album findAlbumById(Filter filter) {
		try {
			Album album = albumRepositoryData.findById(filter.getAlbumId()).get();
			return album;
		}
		catch (NoSuchElementException  e) {
			return new Album("", "", 0L, null, null);
		}
	}

}
