package com.api.core.appl.picture.service.spec;

import java.util.ArrayList;

import com.api.core.appl.picture.PictureDTO;
import com.api.core.appl.util.Filter;

public interface PictureService {

	ArrayList<PictureDTO> listPicture(Filter filter);

	PictureDTO createPicture(PictureDTO pictureDTO);

	PictureDTO getPicture(Filter filter);

	PictureDTO updatePicture(PictureDTO pictureDTO);

	void deletePicture(Long pictureId);

}
