package com.api.core.appl.album.repository.spec;

import org.springframework.data.domain.Page;

import com.api.core.appl.album.Album;
import com.api.core.appl.util.Filter;

public interface AlbumRepository {

	Album createAlbum(Album dadosPosicao);

	Page<Album> listAlbumsByDateAndUser(Filter filter);

	Page<Album> listAlbumsByDate(Filter filter);

	Page<Album> listAlbumsByUser(Filter filter);

	Page<Album> listAlbums(Filter filter);

	Album findAlbumById(Filter filter);
}
