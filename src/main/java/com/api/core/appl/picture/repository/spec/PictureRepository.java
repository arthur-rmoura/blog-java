package com.api.core.appl.picture.repository.spec;


import org.springframework.data.domain.Page;

import com.api.core.appl.picture.Picture;
import com.api.core.appl.util.Filter;

public interface PictureRepository {

	Page<Picture> listPictureByAlbum(Filter filter);

	Page<Picture> listPicture(Filter filter);

	Picture createPicture(Picture picture);

	Picture findPictureById(Filter filter);

	Page<Picture> listPictureByAlbumAndName(Filter filter);

	Page<Picture> findPictureByName(Filter filter);

	Picture updatePicture(Picture picture);

	void deletePicture(Long pictureId);

}
