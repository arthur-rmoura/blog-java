package com.api.core.appl.user.controller.spec;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.api.core.appl.user.UserDTO;

public interface UserController  {

	ResponseEntity<ArrayList<UserDTO>> listUser(Integer pageNumber, Integer pageSize, String birthDate, String firstName);

	ResponseEntity<UserDTO> getUser(Long userId);
	
	ResponseEntity<UserDTO> createUser(UserDTO userDTO);
	
	ResponseEntity<UserDTO> deleteUser(Long userId);
	
	ResponseEntity<UserDTO> updateUser(Long userId, UserDTO userDTO);

}
