package com.api.core.appl.picture.repository.spec;


import org.springframework.data.domain.Page;

import com.api.core.appl.picture.Picture;
import com.api.core.appl.util.Filter;

public interface PictureRepository {

	Page<Picture> listPictureByAlbumAndId(Filter filter);

	Page<Picture> listPictureByAlbum(Filter filter);

	Page<Picture> listPictureById(Filter filter);

	Page<Picture> listPicture(Filter filter);

	Picture createPicture(Picture picture);

	Picture findPictureById(Filter filter);

}
