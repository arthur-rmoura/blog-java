package com.api.core.appl.comment.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.comment.CommentDTO;
import com.api.core.appl.util.Filter;

public interface CommentService {

	ArrayList<CommentDTO> listComment(Filter filter);

	CommentDTO createComment(CommentDTO commentDTO);

	CommentDTO getComment(Filter filter);
	
	CommentDTO updateComment(CommentDTO commentDTO);

	void deleteComment(Long commentId);

	ArrayList<CommentDTO> convertToDTO(List<Comment> commentList);

}
