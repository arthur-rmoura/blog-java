package com.api.core.appl.album.controller.spec;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.api.core.appl.album.AlbumDTO;

public interface AlbumController  {
	

	ResponseEntity<ArrayList<AlbumDTO>> listAlbum(Integer pageNumber, Integer pageSize, String date, Long userId);

	ResponseEntity<AlbumDTO> getAlbum(Long albumId);
	
	ResponseEntity<AlbumDTO> createAlbum(AlbumDTO albumDTO);
	
	ResponseEntity<AlbumDTO> deleteAlbum(Long albumId);
	
	ResponseEntity<AlbumDTO> updateAlbum(Long albumId, AlbumDTO albumDTO);

}
