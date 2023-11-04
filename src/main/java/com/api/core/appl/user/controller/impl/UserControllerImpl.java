package com.api.core.appl.user.controller.impl;

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

import com.api.core.appl.user.UserDTO;
import com.api.core.appl.user.controller.spec.UserController;
import com.api.core.appl.user.service.spec.UserService;
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
public class UserControllerImpl implements UserController {

	@Autowired
	UserService userService;
	
	@Operation(	
		summary = "Recupera usuários", 
		description = "Recupera usuários paginados e filtrados por nome e/ou data de nascimento"
	)
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Usuários recuperados com sucesso.", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))}), 
	        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados usuários com os parâmetros de entrada fornecidos.", content = @Content),
	        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
	})
	@GetMapping("/users")
	@ResponseBody
	@Override
    public ResponseEntity<ArrayList<UserDTO>> listUser(
    		@RequestParam(name = "pageNumber", required = false, defaultValue = "0") @Parameter(name = "pageNumber", description = "Número da página", example = "1", in = ParameterIn.QUERY) Integer pageNumber,
    		@RequestParam(name = "pageSize", required = false, defaultValue = "10") @Parameter(name = "pageSize", description = "Tamanho da página", example = "10", in = ParameterIn.QUERY) Integer pageSize,
    		@RequestParam(name = "birthDate", required = false) @Parameter(name = "birthDate", description = "Data de nascimento Usuário", example = "1980-12-31", in = ParameterIn.QUERY) String birthDate,
    		@RequestParam(name = "firstName", required = false) @Parameter(name = "firstName", description = "Primeiro nome do usuário", example = "2022-12-31 23:59:59", in = ParameterIn.QUERY) String firstName
    ) {
		Filter filter = new Filter();
		filter.setPageNumber(pageNumber);
		filter.setPageSize(pageSize);
		filter.setBirthDate(birthDate);
		filter.setFirstName(firstName);
		
		ArrayList<UserDTO> userDTOList = userService.listUser(filter);
		
        return new ResponseEntity<ArrayList<UserDTO>>(userDTOList, HttpStatus.OK);
    }
	
	
	@Operation(	
			summary = "Insere novos usuários", 
			description = "Insere novos usuários"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "201", description = "Usuário inserido com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = UserDTO.class))}), 
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@PostMapping("/users")
		@ResponseBody
		@Override
	    public ResponseEntity<UserDTO> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
	    		required = true,
	    		description = "Payload da requisição contendo o conteúdo json do novo usuário a ser inserido",
	    		content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = UserDTO.class))}
	    		)  
	    		@RequestBody UserDTO userDTO) {
			
			userDTO = userService.createUser(userDTO);
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
			
	    }
	
	
	@Operation(	
			summary = "Recupera um usuário em específico", 
			description = "Recupera um usuário em específico passando como parâmetro o id do usuário"
		)
		@ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso.", content = {@Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = UserDTO.class))}), 
		        @ApiResponse(responseCode = "404", description = "Não Encontrado - Não foram encontrados usuários com os parâmetros de entrada fornecidos.", content = @Content),
		        @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content)
		})
		@GetMapping("/users/{userId}/")
		@ResponseBody
		@Override
		public ResponseEntity<UserDTO> getUser(
				@PathVariable(name = "userId", required = true) @Parameter(name = "userId", description = "Id do usuário para ser recuperado", example = "100", in = ParameterIn.PATH, required = true) Long userId			
		) {
			Filter filter = new Filter();
			filter.setUserId(userId);
			UserDTO userDTO = userService.getUser(filter);
			if(userDTO.getId() == 0L) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}

		@Operation(summary = "Atualiza usuário", description = "Atualiza usuário de id enviado no 'path param'")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@PutMapping("/users/{userId}/")
		@ResponseBody
		@Override
		public ResponseEntity<UserDTO> updateUser(
				@PathVariable(name = "userId", required = true) @Parameter(name = "userId", description = "Id do usuário para ser atualizado", example = "100", in = ParameterIn.PATH, required = true) Long userId,
				@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Payload da requisição contendo o conteúdo json do usuário a ser atualizado", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }) @RequestBody UserDTO userDTO
		) {
			userDTO.setId(userId);
			userDTO = userService.updateUser(userDTO);
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}

		@Operation(summary = "Remove usuário", description = "Remove usuário de id passado como parâmetro")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "204", description = "Usuário removido com sucesso.", content = {
						@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
				@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = @Content) })
		@DeleteMapping("/users/{userId}/")
		@ResponseBody
		@Override
		public ResponseEntity<UserDTO> deleteUser(
				@PathVariable(name = "userId", required = true) @Parameter(name = "userId", description = "Id do usuário para ser excluido", example = "100", in = ParameterIn.PATH, required = true) Long userId
		) {
			userService.deleteUser(userId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}

