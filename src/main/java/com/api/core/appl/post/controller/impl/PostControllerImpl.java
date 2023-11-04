package com.api.core.appl.post.controller.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.core.appl.post.PostDTO;
import com.api.core.appl.post.controller.spec.PostController;
import com.api.core.appl.post.service.spec.PostService;
import com.api.core.appl.util.Filter;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;


@RestController
public class PostControllerImpl implements PostController {

	@Autowired
	PostService postService;
	
	
	@Operation(	
		summary = "Recupera posts", 
		description = "Recupera posts paginados e filtrados por data e/ou usuário"
	)
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Posts recuperados com sucesso.", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PostDTO.class)))}), 
	        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados posts com os parâmetros de entrada fornecidos.", content = @Content),
	        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
	})
	@GetMapping("/posts")
	@ResponseBody
	@Override
    public ResponseEntity<ArrayList<PostDTO>> listPost(
    		@RequestParam(name = "pageNumber", required = false, defaultValue = "0") @Parameter(name = "pageNumber", description = "Número da página", example = "1", in = ParameterIn.QUERY) Integer pageNumber,
    		@RequestParam(name = "pageSize", required = false, defaultValue = "10") @Parameter(name = "pageSize", description = "Tamanho da página", example = "10", in = ParameterIn.QUERY) Integer pageSize,
    		@RequestParam(name = "date", required = false) @Parameter(name = "date", description = "Data do Post", example = "2022-12-31 23:59:59", in = ParameterIn.QUERY) String date,
    		@RequestParam(name = "userId", required = false) @Parameter(name = "userId", description = "Id do usuário", example = "2022-12-31 23:59:59", in = ParameterIn.QUERY) Long userId
    ) {
		Filter filter = new Filter();
		filter.setPageNumber(pageNumber);
		filter.setPageSize(pageSize);
		filter.setDate(date);
		filter.setUserId(userId);
		
		ArrayList<PostDTO> postDTOList = postService.listPost(filter);
		
        return new ResponseEntity<ArrayList<PostDTO>>(postDTOList, HttpStatus.OK);
    }
	
	
	@Operation(	
			summary = "Insere novos Posts", 
			description = "Insere novos Posts"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "201", description = "Post inserido com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = PostDTO.class))}), 
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@PostMapping("/posts")
		@ResponseBody
		@Override
	    public ResponseEntity<PostDTO> createPost(@io.swagger.v3.oas.annotations.parameters.RequestBody(
	    		required = true,
	    		description = "Payload da requisição contendo o conteúdo json do novo post a ser inserido",
	    		content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = PostDTO.class))}
	    		)  
	    		@RequestBody PostDTO postDTO) {
			
			postDTO = postService.createPost(postDTO);
			return new ResponseEntity<PostDTO>(postDTO, HttpStatus.CREATED);
			
	    }
	
	
	@Operation(	
			summary = "Recupera um post em específico", 
			description = "Recupera um post em específico passando como parâmetro o id do post"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Post recuperado com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = PostDTO.class))}), 
		        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados posts com os parâmetros de entrada fornecidos.", content = @Content),
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@GetMapping("/posts/{postId}/")
		@ResponseBody
		@Override
		public ResponseEntity<PostDTO> getPost(
				@PathVariable(name = "postId", required = true) @Parameter(name = "postId", description = "Id do post para ser recuperado", example = "100", in = ParameterIn.PATH, required = true) Long postId			
		) {
			Filter filter = new Filter();
			filter.setPostId(postId);
			PostDTO postDTO = postService.getPost(filter);
			if(postDTO.getId() == 0L) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
		}

		@Operation(summary = "Atualiza post", description = "Atualiza post de id passado como path param")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Post atualizado com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@PutMapping("/posts/{postId}/")
		@ResponseBody
		@Override
		public ResponseEntity<PostDTO> updatePost(
				@PathVariable(name = "postId", required = true) @Parameter(name = "postId", description = "Id do post para ser atualizado", example = "100", in = ParameterIn.PATH, required = true) Long postId,
				@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da requisição contendo o conteúdo json do post a ser atualizado", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class)) }) @RequestBody PostDTO postDTO
		) {
			postDTO.setId(postId);
			postDTO = postService.updatePost(postDTO);
			return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
		}

		@Operation(summary = "Remove post", description = "Remove post de id passado como parâmetro")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "204", description = "Post removido com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@DeleteMapping("/posts/{postId}/")
		@ResponseBody
		@Override
		public ResponseEntity<PostDTO> deletePost(
				@PathVariable(name = "postId", required = true) @Parameter(name = "postId", description = "Id do post para ser excluido", example = "100", in = ParameterIn.PATH, required = true) Long postId
		) {
			postService.deletePost(postId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}