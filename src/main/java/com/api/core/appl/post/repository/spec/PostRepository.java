package com.api.core.appl.post.repository.spec;

import org.springframework.data.domain.Page;

import com.api.core.appl.post.Post;
import com.api.core.appl.util.Filter;

public interface PostRepository {

	Post createPost(Post dadosPosicao);

	Page<Post> listPostsByDateAndUser(Filter filter);

	Page<Post> listPostsByDate(Filter filter);

	Page<Post> listPostsByUser(Filter filter);

	Page<Post> listPosts(Filter filter);

	Post findPostById(Filter filter);

}
