package com.api.core.appl.post.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.api.core.appl.comment.Comment;
import com.api.core.appl.comment.service.spec.CommentService;
import com.api.core.appl.post.Post;
import com.api.core.appl.post.PostDTO;
import com.api.core.appl.post.repository.spec.PostRepository;
import com.api.core.appl.post.service.spec.PostService;
import com.api.core.appl.user.User;
import com.api.core.appl.util.Filter;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentService commentService;
	
	@Override
	public ArrayList<PostDTO> listPost(Filter filter) {

		Page<Post> pagePost;
		
		if(filter.getDate() != null && filter.getUserId() != null) {
			pagePost = postRepository.listPostsByDateAndUser(filter);
		}
		else if(filter.getDate() != null) {
			pagePost = postRepository.listPostsByDate(filter);
		}
		else if (filter.getUserId() != null) {
			pagePost = postRepository.listPostsByUser(filter);
		}
		else {
			pagePost = postRepository.listPosts(filter);
		}
		
		List<Post> postList = pagePost.getContent();
		

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		ArrayList<PostDTO> postDTOList = new ArrayList<>();
		Instant instant = Instant.now();
		
		for(Post post : postList) {
			LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(post.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			PostDTO postDTO = new PostDTO(post.getId(), localDateTime.format(formatter), post.getTextContent(), post.getUser().getId(), commentService.convertToDTO(post.getCommentList()));
			postDTOList.add(postDTO);
		}

		return postDTOList;
	}

	@Override
	public PostDTO createPost(PostDTO postDTO) {
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (postDTO.getDate() != null) {
			LocalDateTime localDateTime = LocalDateTime.parse(postDTO.getDate(), formatter);
			Instant instant = Instant.now();
			timestampDate = localDateTime.toEpochSecond(ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		}
		
		User user = new User(1L); //TODO pegar o id da sessão do usuário
		Post post = new Post(timestampDate, postDTO.getTextContent(), new ArrayList<Comment>(), user);

		post = postRepository.createPost(post);
		postDTO.setId(post.getId());
		

		return postDTO;
	}
	
	@Override
	public PostDTO getPost(Filter filter) {
		
		Post post = postRepository.findPostById(filter);
		if(post.getId() == null) {
			return new PostDTO(0L, "", "", 0L, new ArrayList<>());
		}

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);

		Instant instant = Instant.now();
		
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(post.getDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		PostDTO postDTO = new PostDTO(post.getId(), localDateTime.format(formatter), post.getTextContent(), post.getUser().getId(), commentService.convertToDTO(post.getCommentList()));		

		return postDTO;
	}

	
	@Override
	public PostDTO updatePost(PostDTO postDTO) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);
		
		
		long timestampDate = Instant.now().toEpochMilli() / 1000;
		if (postDTO.getDate() != null) {
			LocalDateTime localDateTime = LocalDateTime.parse(postDTO.getDate(), formatter);
			Instant instant = Instant.now();
			timestampDate = localDateTime.toEpochSecond(ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		}
		
		User user = new User(postDTO.getUserId());
		Post post = new Post(timestampDate, postDTO.getTextContent(), new ArrayList<Comment>(), user);

		post.setId(postDTO.getId());
		post = postRepository.updatePost(post);

		return postDTO;
	}

	@Override
	public void deletePost(Long postId) {
		postRepository.deletePost(postId);
	}

}
