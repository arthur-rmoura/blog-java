package com.api.core.appl.album.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.api.core.appl.album.Album;
import com.api.core.appl.album.AlbumDTO;
import com.api.core.appl.album.repository.spec.AlbumRepository;
import com.api.core.appl.album.service.spec.AlbumService;
import com.api.core.appl.picture.Picture;
import com.api.core.appl.user.User;
import com.api.core.appl.util.Filter;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	AlbumRepository albumRepository;
	
	@Override
	public ArrayList<AlbumDTO> listAlbum(Filter filter) {

		Page<Album> pageAlbum;
		
		if(filter.getDate() != null && filter.getUserId() != null) {
			pageAlbum = albumRepository.listAlbumsByDateAndUser(filter);
		}
		else if(filter.getDate() != null) {
			pageAlbum = albumRepository.listAlbumsByDate(filter);
		}
		else if (filter.getUserId() != null) {
			pageAlbum = albumRepository.listAlbumsByUser(filter);
		}
		else {
			pageAlbum = albumRepository.listAlbums(filter);
		}
		
		List<Album> albumList = pageAlbum.getContent();
		

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		ArrayList<AlbumDTO> albumDTOList = new ArrayList<>();
		Instant instant = Instant.now();
		
		for(Album album : albumList) {
			LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(album.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			AlbumDTO albumDTO = new AlbumDTO(album.getId(), album.getName(), localDateTime.format(formatter), album.getDescription(), album.getUser().getId());
			albumDTOList.add(albumDTO);
		}

		return albumDTOList;
	}

	@Override
	public AlbumDTO createAlbum(AlbumDTO albumDTO) {
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (albumDTO.getDate() != null) {
			LocalDateTime localDateTime = LocalDateTime.parse(albumDTO.getDate(), formatter);
			Instant instant = Instant.now();
			timestampDate = localDateTime.toEpochSecond(ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		}
		
		User user = new User(albumDTO.getUserId());
		Album album = new Album(albumDTO.getName(), albumDTO.getDescription(), timestampDate, new ArrayList<Picture>(), user);

		albumRepository.createAlbum(album);
		

		return albumDTO;
	}
	
	@Override
	public AlbumDTO getAlbum(Filter filter) {
		
		Album album = albumRepository.findAlbumById(filter);
		if(album.getId() == null) {
			return new AlbumDTO(0L, "", "", "", 0L);
		}

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);

		Instant instant = Instant.now();
		
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(album.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		AlbumDTO albumDTO = new AlbumDTO(album.getId(), album.getName(), localDateTime.format(formatter), album.getDescription(), album.getUser().getId());		

		return albumDTO;
	}

	
	@Override
	public AlbumDTO updateAlbum(AlbumDTO albumDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAlbum(Long albumId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<AlbumDTO> convertToDTO(List<Album> albumList) {
		ArrayList<AlbumDTO> albumDTOList = new ArrayList<AlbumDTO>();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd")
			    .toFormatter(Locale.ENGLISH);
		Instant instant = Instant.now();

		for(Album album : albumList) {
			LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(album.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			AlbumDTO albumDTO = new AlbumDTO(album.getId(), album.getName(), localDateTime.format(formatter), album.getDescription(), album.getUser().getId());
			albumDTOList.add(albumDTO);
		}
		
		return albumDTOList;
	}

}

