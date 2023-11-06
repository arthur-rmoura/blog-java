package com.api.core.appl.post.service.spec;

import java.util.ArrayList;

import com.api.core.appl.post.Post;
import com.api.core.appl.post.PostDTO;
import com.api.core.appl.util.Filter;

public interface PostService {

	ArrayList<PostDTO> listPost(Filter filter);

	PostDTO createPost(PostDTO postDTO);

	PostDTO getPost(Filter filter);
	
	PostDTO updatePost(PostDTO postDTO);

	void deletePost(Long postId);

	Post getPostEntity(Filter filter);

}
