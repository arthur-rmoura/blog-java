package com.api.core.appl.user.service.spec;

import java.util.ArrayList;

import com.api.core.appl.user.UserDTO;
import com.api.core.appl.util.Filter;

public interface UserService {

	ArrayList<UserDTO> listUser(Filter filter);

	UserDTO createUser(UserDTO userDTO);

	UserDTO getUser(Filter filter);
	
	UserDTO updateUser(UserDTO userDTO);

	void deleteUser(Long userId);

}
