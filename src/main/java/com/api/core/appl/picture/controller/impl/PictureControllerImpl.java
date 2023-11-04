package com.api.core.appl.picture.controller.impl;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

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

import com.api.core.appl.picture.PictureDTO;
import com.api.core.appl.picture.controller.spec.PictureController;
import com.api.core.appl.picture.service.spec.PictureService;
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
public class PictureControllerImpl implements PictureController {

	@Autowired
	PictureService pictureService;

	@Operation(summary = "Recupera Fotos", description = "Recupera Fotos paginados e filtrados por álbum e/ou nome")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Fotos recuperadas com sucesso.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PictureDTO.class))) }),
			@ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados fotos com os parâmetros de entrada fornecidos.", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
	@GetMapping("/pictures")
	@ResponseBody
	@Override
	public ResponseEntity<ArrayList<PictureDTO>> listPicture(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") @Parameter(name = "pageNumber", description = "Número da página", example = "1", in = ParameterIn.QUERY) Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") @Parameter(name = "pageSize", description = "Tamanho da página", example = "10", in = ParameterIn.QUERY) Integer pageSize,
			@RequestParam(name = "albumId", required = false) @Parameter(name = "albumId", description = "Id do álbum", example = "100", in = ParameterIn.QUERY, required = true) Long albumId,
			@RequestParam(name = "pictureName", required = false) @Parameter(name = "Nome da figura", description = "Nome da figura", example = "100", in = ParameterIn.QUERY) String pictureName) {
		Filter filter = new Filter();
		filter.setPageNumber(pageNumber);
		filter.setPageSize(pageSize);
		filter.setAlbumId(albumId);
		filter.setPictureName(pictureName);

		ArrayList<PictureDTO> pictureDTOList = pictureService.listPicture(filter);

		return new ResponseEntity<ArrayList<PictureDTO>>(pictureDTOList, HttpStatus.OK);
	}

	@Operation(summary = "Insere foto em álbum", description = "Insere novas fotos em um álbum especificado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "foto inserida com sucesso.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PictureDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
	@PostMapping("/pictures")
	@ResponseBody
	@Consumes(value = "application/octet-stream")
	@Produces(value = "application/octet-stream")
	@Override
	public ResponseEntity<PictureDTO> createPicture(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da requisição contendo o conteúdo json e arquivo da nova figura a ser inserida", content = {
					@Content(mediaType = "application/octet-stream", schema = @Schema(implementation = PictureDTO.class)) }) @RequestBody PictureDTO pictureDTO) {

		pictureDTO = pictureService.createPicture(pictureDTO);
		return new ResponseEntity<PictureDTO>(pictureDTO, HttpStatus.CREATED);
	}

	@Operation(	
		summary = "Recupera uma foto em específico", 
		description = "Recupera uma foto em específico passando como parâmetro o id da foto"
	)
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Foto recuperada com sucesso.", content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PictureDTO.class))}), 
	        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados fotos com os parâmetros de entrada fornecidos.", content = @Content),
	        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
	})
	@GetMapping("/pictures/{pictureId}/")
	@ResponseBody
	@Override
	public ResponseEntity<PictureDTO> getPicture(
			@PathVariable(name = "pictureId", required = true) @Parameter(name = "pictureId", description = "Id da foto para ser recuperada", example = "100", in = ParameterIn.PATH, required = true) Long pictureId			
	) {
		Filter filter = new Filter();
		filter.setPictureId(pictureId);
		PictureDTO pictureDTO = pictureService.getPicture(filter);
		
		if(pictureDTO.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PictureDTO>(pictureDTO, HttpStatus.OK);
	}

	@Operation(summary = "Atualiza foto em um álbum", description = "Atualiza foto em um álbum especificado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Foto atualizada com sucesso.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PictureDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
	@PutMapping("/pictures/{pictureId}/")
	@ResponseBody
	@Override
	public ResponseEntity<PictureDTO> updatePicture(
			@PathVariable(name = "pictureId", required = true) @Parameter(name = "pictureId", description = "Id da foto para ser atualizada", example = "100", in = ParameterIn.PATH, required = true) Long pictureId,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da requisição contendo o conteúdo json e arquivo da nova figura a ser atualizada", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PictureDTO.class)) }) @RequestBody PictureDTO pictureDTO
	) {
		pictureDTO.setId(pictureId);
		pictureDTO = pictureService.updatePicture(pictureDTO);
		return new ResponseEntity<PictureDTO>(pictureDTO, HttpStatus.OK);
	}

	@Operation(summary = "Remove foto em um álbum", description = "Remove foto em um álbum especificado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Foto removida com sucesso.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PictureDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
	@DeleteMapping("/pictures/{pictureId}/")
	@ResponseBody
	@Override
	public ResponseEntity<PictureDTO> deletePicture(
			@PathVariable(name = "pictureId", required = true) @Parameter(name = "pictureId", description = "Id da foto para ser excluida", example = "100", in = ParameterIn.PATH, required = true) Long pictureId
	) {
		pictureService.deletePicture(pictureId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
