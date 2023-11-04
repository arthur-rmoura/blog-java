package com.api.core.appl.album.controller.impl;

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

import com.api.core.appl.album.AlbumDTO;
import com.api.core.appl.album.controller.spec.AlbumController;
import com.api.core.appl.album.service.spec.AlbumService;
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
public class AlbumControllerImpl implements AlbumController {

	@Autowired
	AlbumService albumService;
	
	
	@Operation(	
		summary = "Recupera álbums", 
		description = "Recupera álbums paginados e filtrados por data e/ou usuário"
	)
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Dados recuperados com sucesso.", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AlbumDTO.class)))}), 
	        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados álbums com os parâmetros de entrada fornecidos.", content = @Content),
	        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
	})
	@GetMapping("/albums")
	@ResponseBody
	@Override
    public ResponseEntity<ArrayList<AlbumDTO>> listAlbum(
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
		
		ArrayList<AlbumDTO> albumDTOList = albumService.listAlbum(filter);
		
        return new ResponseEntity<ArrayList<AlbumDTO>>(albumDTOList, HttpStatus.OK);
    }
	
	
	@Operation(	
			summary = "Insere novos Comentários", 
			description = "Insere novos Comentários"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "201", description = "Comentário inserido com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = AlbumDTO.class))}), 
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@PostMapping("/albums")
		@ResponseBody
		@Override
	    public ResponseEntity<AlbumDTO> createAlbum(@io.swagger.v3.oas.annotations.parameters.RequestBody(
	    		required = true,
	    		description = "Payload da requisição contendo o conteúdo json do novo álbum a ser inserido",
	    		content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = AlbumDTO.class))}
	    		)  
	    		@RequestBody AlbumDTO albumDTO) {
			
			albumDTO = albumService.createAlbum(albumDTO);
			return new ResponseEntity<AlbumDTO>(albumDTO, HttpStatus.CREATED);
			
	    }
	
	
	@Operation(	
			summary = "Recupera um álbum em específico", 
			description = "Recupera um álbum em específico passando como parâmetro o id do álbum"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Comentário recuperado com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = AlbumDTO.class))}), 
		        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados álbums com os parâmetros de entrada fornecidos.", content = @Content),
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@GetMapping("/albums/{albumId}/")
		@ResponseBody
		@Override
		public ResponseEntity<AlbumDTO> getAlbum(
				@PathVariable(name = "albumId", required = true) @Parameter(name = "albumId", description = "Id do álbum para ser recuperado", example = "100", in = ParameterIn.PATH, required = true) Long albumId			
		) {
			Filter filter = new Filter();
			filter.setAlbumId(albumId);
			AlbumDTO albumDTO = albumService.getAlbum(filter);
			if(albumDTO.getId() == 0L) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<AlbumDTO>(albumDTO, HttpStatus.OK);
		}

		@Operation(summary = "Atualiza álbum", description = "Atualiza álbum de id enviado no 'path param'")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@PutMapping("/albums/{albumId}/")
		@ResponseBody
		@Override
		public ResponseEntity<AlbumDTO> updateAlbum(
				@PathVariable(name = "albumId", required = true) @Parameter(name = "albumId", description = "Id do álbum para ser atualizado", example = "100", in = ParameterIn.PATH, required = true) Long albumId,
				@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da requisição contendo o conteúdo json do álbum a ser atualizado", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class)) }) @RequestBody AlbumDTO albumDTO
		) {
			albumDTO.setId(albumId);
			albumDTO = albumService.updateAlbum(albumDTO);
			return new ResponseEntity<AlbumDTO>(albumDTO, HttpStatus.OK);
		}

		@Operation(summary = "Remove álbum", description = "Remove álbum de id passado como parâmetro")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "204", description = "Comentário removido com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@DeleteMapping("/albums/{albumId}/")
		@ResponseBody
		@Override
		public ResponseEntity<AlbumDTO> deleteAlbum(
				@PathVariable(name = "albumId", required = true) @Parameter(name = "albumId", description = "Id do álbum para ser excluido", example = "100", in = ParameterIn.PATH, required = true) Long albumId
		) {
			albumService.deleteAlbum(albumId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}

