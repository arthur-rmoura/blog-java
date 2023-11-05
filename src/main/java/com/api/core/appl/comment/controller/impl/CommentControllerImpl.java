package com.api.core.appl.comment.controller.impl;

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

import com.api.core.appl.comment.CommentDTO;
import com.api.core.appl.comment.controller.spec.CommentController;
import com.api.core.appl.comment.service.spec.CommentService;
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
public class CommentControllerImpl implements CommentController {

	@Autowired
	CommentService commentService;
	
	
	@Operation(	
		summary = "Recupera comentários", 
		description = "Recupera comentários paginados e filtrados por data e/ou usuário"
	)
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Comentários recuperados com sucesso.", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))}), 
	        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados comentários com os parâmetros de entrada fornecidos.", content = @Content),
	        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
	})
	@GetMapping("/comments")
	@ResponseBody
	@Override
    public ResponseEntity<ArrayList<CommentDTO>> listComment(
    		@RequestParam(name = "pageNumber", required = false, defaultValue = "0") @Parameter(name = "pageNumber", description = "Número da página", example = "1", in = ParameterIn.QUERY) Integer pageNumber,
    		@RequestParam(name = "pageSize", required = false, defaultValue = "10") @Parameter(name = "pageSize", description = "Tamanho da página", example = "10", in = ParameterIn.QUERY) Integer pageSize,
    		@RequestParam(name = "date", required = false) @Parameter(name = "date", description = "Data do Comentário", example = "2022-12-31 23:59:59", in = ParameterIn.QUERY) String date,
    		@RequestParam(name = "userId", required = false) @Parameter(name = "userId", description = "Id do usuário", example = "2022-12-31 23:59:59", in = ParameterIn.QUERY) Long userId
    ) {
		Filter filter = new Filter();
		filter.setPageNumber(pageNumber);
		filter.setPageSize(pageSize);
		filter.setDate(date);
		filter.setUserId(userId);
		
		ArrayList<CommentDTO> commentDTOList = commentService.listComment(filter);
		
        return new ResponseEntity<ArrayList<CommentDTO>>(commentDTOList, HttpStatus.OK);
    }
	
	
	@Operation(	
			summary = "Insere novos Comentários", 
			description = "Insere novos Comentários"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "201", description = "Comentário inserido com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = CommentDTO.class))}), 
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@PostMapping("/comments")
		@ResponseBody
		@Override
	    public ResponseEntity<CommentDTO> createComment(@io.swagger.v3.oas.annotations.parameters.RequestBody(
	    		required = true,
	    		description = "Payload da requisição contendo o conteúdo json do novo comentário a ser inserido",
	    		content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = CommentDTO.class))}
	    		)  
	    		@RequestBody CommentDTO commentDTO) {
			
			commentDTO = commentService.createComment(commentDTO);
			return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.CREATED);
			
	    }
	
	
	@Operation(	
			summary = "Recupera um comentário em específico", 
			description = "Recupera um comentário em específico passando como parâmetro o id do comentário"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Comentário recuperado com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = CommentDTO.class))}), 
		        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados comentários com os parâmetros de entrada fornecidos.", content = @Content),
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@GetMapping("/comments/{commentId}")
		@ResponseBody
		@Override
		public ResponseEntity<CommentDTO> getComment(
				@PathVariable(name = "commentId", required = true) @Parameter(name = "commentId", description = "Id do comentário para ser recuperado", example = "100", in = ParameterIn.PATH, required = true) Long commentId			
		) {
			Filter filter = new Filter();
			filter.setCommentId(commentId);
			CommentDTO commentDTO = commentService.getComment(filter);
			if(commentDTO.getId() == 0L) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
		}

		@Operation(summary = "Atualiza comentário", description = "Atualiza comentário de id enviado no 'path param'")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@PutMapping("/comments/{commentId}")
		@ResponseBody
		@Override
		public ResponseEntity<CommentDTO> updateComment(
				@PathVariable(name = "commentId", required = true) @Parameter(name = "commentId", description = "Id do comentário para ser atualizado", example = "100", in = ParameterIn.PATH, required = true) Long commentId,
				@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da requisição contendo o conteúdo json do comentário a ser atualizado", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class)) }) @RequestBody CommentDTO commentDTO
		) {
			commentDTO.setId(commentId);
			commentDTO = commentService.updateComment(commentDTO);
			return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
		}

		@Operation(summary = "Remove comentário", description = "Remove comentário de id passado como parâmetro")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "204", description = "Comentário removido com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@DeleteMapping("/comments/{commentId}")
		@ResponseBody
		@Override
		public ResponseEntity<CommentDTO> deleteComment(
				@PathVariable(name = "commentId", required = true) @Parameter(name = "commentId", description = "Id do comentário para ser excluido", example = "100", in = ParameterIn.PATH, required = true) Long commentId
		) {
			commentService.deleteComment(commentId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}

