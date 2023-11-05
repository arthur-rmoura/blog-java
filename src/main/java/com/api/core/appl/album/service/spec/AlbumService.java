package com.api.core.appl.album.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.api.core.appl.album.Album;
import com.api.core.appl.album.AlbumDTO;
import com.api.core.appl.util.Filter;

public interface AlbumService {

	ArrayList<AlbumDTO> listAlbum(Filter filter);

	AlbumDTO createAlbum(AlbumDTO albumDTO);

	AlbumDTO getAlbum(Filter filter);
	
	AlbumDTO updateAlbum(AlbumDTO albumDTO);

	void deleteAlbum(Long albumId);

	ArrayList<AlbumDTO> convertToDTO(List<Album> albumList);

	Album getAlbumEntity(Filter filter);

	Album createAlbumEntity(Album album);

}
