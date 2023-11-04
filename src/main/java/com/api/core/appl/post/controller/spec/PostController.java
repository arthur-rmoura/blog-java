package com.api.core.appl.post.controller.spec;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.api.core.appl.post.PostDTO;

public interface PostController  {
	

	ResponseEntity<ArrayList<PostDTO>> listPost(Integer pageNumber, Integer pageSize, String date, Long userId);

	ResponseEntity<PostDTO> getPost(Long postId);
	
	ResponseEntity<PostDTO> createPost(PostDTO postDTO);
	
	ResponseEntity<PostDTO> deletePost(Long postId);
	
	ResponseEntity<PostDTO> updatePost(Long postId, PostDTO postDTO);
	

}
