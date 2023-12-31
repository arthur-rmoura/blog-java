package com.api.core.appl.comment.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.comment.CommentDTO;
import com.api.core.appl.comment.repository.spec.CommentRepository;
import com.api.core.appl.comment.service.spec.CommentService;
import com.api.core.appl.post.Post;
import com.api.core.appl.post.repository.spec.PostRepository;
import com.api.core.appl.user.User;
import com.api.core.appl.user.service.spec.UserService;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserService userService;
	
	
	@Override
	public ArrayList<CommentDTO> listComment(Filter filter) {

		Page<Comment> pageComment;
		
		if(filter.getDate() != null && filter.getUserId() != null) {
			pageComment = commentRepository.listCommentsByDateAndUser(filter);
		}
		else if(filter.getDate() != null) {
			pageComment = commentRepository.listCommentsByDate(filter);
		}
		else if (filter.getUserId() != null) {
			pageComment = commentRepository.listCommentsByUser(filter);
		}
		else {
			pageComment = commentRepository.listComments(filter);
		}
		
		List<Comment> commentList = pageComment.getContent();
		

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		ArrayList<CommentDTO> commentDTOList = new ArrayList<>();
		Instant instant = Instant.now();
		
		for(Comment comment : commentList) {
			LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(comment.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			CommentDTO commentDTO = new CommentDTO(comment.getId(), localDateTime.format(formatter), comment.getTextContent(), comment.getUser().getId(), comment.getPost().getId());
			commentDTOList.add(commentDTO);
		}

		return commentDTOList;
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public CommentDTO createComment(CommentDTO commentDTO) {
		
		Filter filter = new Filter();
		filter.setPostId(commentDTO.getPostId());
		Post post = postRepository.findPostById(filter);
		
		if(post.getId() == null) {
			throw new NotFoundException("The post doesn't exist");
		}
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (commentDTO.getDate() != null) {
			try {
				timestampDate = UtilLibrary.getDateTimestampF1(commentDTO.getDate());
			}
			catch (DateTimeParseException e) {
				timestampDate = UtilLibrary.getDateTimestampF2(commentDTO.getDate());
			}
		}
		
		filter.setUserId(1L); //TODO pegar o id da sessão do usuário
		User user = userService.getUserEntity(filter);
		
		Comment comment = new Comment(timestampDate, commentDTO.getTextContent(), user);
		comment.setPost(post);
		
		comment = commentRepository.createComment(comment);
		commentDTO.setId(comment.getId());

		return commentDTO;
	}
	
	@Override
	public CommentDTO getComment(Filter filter) {
		
		Comment comment = commentRepository.findCommentById(filter);
		if(comment.getId() == null) {
			return new CommentDTO(0L, "", "", 0L, 0L);
		}

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);

		Instant instant = Instant.now();
		
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(comment.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		CommentDTO commentDTO = new CommentDTO(comment.getId(), localDateTime.format(formatter), comment.getTextContent(), comment.getUser().getId(), comment.getPost().getId());		

		return commentDTO;
	}

	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public CommentDTO updateComment(CommentDTO commentDTO) {
		
		Filter filter = new Filter();
		filter.setPostId(commentDTO.getPostId());
		Post post = postRepository.findPostById(filter);
		
		if(post.getId() == null) {
			throw new NotFoundException("The post doesn't exist");
		}
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (commentDTO.getDate() != null) {
			try {
				timestampDate = UtilLibrary.getDateTimestampF1(commentDTO.getDate());
			}
			catch (DateTimeParseException e) {
				timestampDate = UtilLibrary.getDateTimestampF2(commentDTO.getDate());
			}
		}
		
		filter.setUserId(1L); //TODO pegar o id da sessão do usuário
		User user = userService.getUserEntity(filter);
		
		Comment comment = new Comment(timestampDate, commentDTO.getTextContent(), user);
		comment.setPost(post);
		
		comment.setId(commentDTO.getId());
		comment = commentRepository.updateComment(comment);

		return commentDTO;
	}

	@Override
	public void deleteComment(Long commentId) {
		commentRepository.deleteComment(commentId);
	}

	@Override
	public ArrayList<CommentDTO> convertToDTO(List<Comment> commentList) {
		ArrayList<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd")
			    .toFormatter(Locale.ENGLISH);
		Instant instant = Instant.now();

		for(Comment comment : commentList) {
			LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(comment.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			CommentDTO commentDTO = new CommentDTO(comment.getId(), localDateTime.format(formatter), comment.getTextContent(), comment.getUser().getId(), comment.getPost().getId());
			commentDTOList.add(commentDTO);
		}
		
		return commentDTOList;
	}

}

