package com.api.core.appl.comment.repository.spec;

import org.springframework.data.domain.Page;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.util.Filter;

public interface CommentRepository {

	Comment createComment(Comment dadosPosicao);

	Page<Comment> listCommentsByDateAndUser(Filter filter);

	Page<Comment> listCommentsByDate(Filter filter);

	Page<Comment> listCommentsByUser(Filter filter);

	Page<Comment> listComments(Filter filter);

	Comment findCommentById(Filter filter);
}
