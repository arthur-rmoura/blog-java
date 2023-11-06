package com.api.core.appl.comment.controller.spec;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.api.core.appl.comment.CommentDTO;

public interface CommentController  {
	

	ResponseEntity<ArrayList<CommentDTO>> listComment(Integer pageNumber, Integer pageSize, String date, Long userId);

	ResponseEntity<CommentDTO> getComment(Long commentId);
	
	ResponseEntity<CommentDTO> createComment(CommentDTO commentDTO);
	
	ResponseEntity<CommentDTO> deleteComment(Long commentId);
	
	ResponseEntity<CommentDTO> updateComment(Long commentId, CommentDTO commentDTO);

}
