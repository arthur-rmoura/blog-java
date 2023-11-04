package com.api.core.appl.picture.controller.spec;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.api.core.appl.picture.PictureDTO;

public interface PictureController  {
	

	ResponseEntity<ArrayList<PictureDTO>> listPicture(Integer pageNumber, Integer pageSize, Long albumId, Long pictureId);
	
	ResponseEntity<PictureDTO> getPicture(Long pictureId);

	ResponseEntity<PictureDTO> createPicture(PictureDTO pictureDTO);
	
	ResponseEntity<PictureDTO> deletePicture(Long pictureId);

	ResponseEntity<PictureDTO> updatePicture(Long pictureId, PictureDTO pictureDTO);


}
