package com.api.core.appl.user.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.api.core.appl.user.User;
import com.api.core.appl.user.UserDTO;
import com.api.core.appl.user.repository.spec.UserRepository;
import com.api.core.appl.user.service.spec.UserService;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public ArrayList<UserDTO> listUser(Filter filter) {

		Page<User> pageUser;
		
		if(filter.getBirthDate() != null && filter.getFirstName() != null) {
			pageUser = userRepository.listUsersByBirthDateAndFirstName(filter);
		}
		else if(filter.getBirthDate() != null) {
			pageUser = userRepository.listUsersByBirthDate(filter);
		}
		else if (filter.getFirstName() != null) {
			pageUser = userRepository.listUsersByFirstName(filter);
		}
		else {
			pageUser = userRepository.listUsers(filter);
		}
		
		List<User> userList = pageUser.getContent();
		

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd")
			    .toFormatter(Locale.ENGLISH);
		Instant instant = Instant.now();
		
		ArrayList<UserDTO> userDTOList = new ArrayList<>();
		
		for(User user : userList) {
			LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(user.getBirthDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
			UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), localDateTime.format(formatter));
			userDTOList.add(userDTO);
		}

		return userDTOList;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd")
			    .toFormatter(Locale.ENGLISH);
		
		
		long timestampDate = 0;
		if (userDTO.getBirthDate() != null) {
			LocalDateTime localDateTime = LocalDateTime.parse(userDTO.getBirthDate(), formatter);
			Instant instant = Instant.now();
			timestampDate = localDateTime.toEpochSecond(ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		}
		
		User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), UtilLibrary.encryptMD5(userDTO.getPassword()), timestampDate);

		userRepository.createUser(user);
		

		return userDTO;
	}
	
	@Override
	public UserDTO getUser(Filter filter) {
		
		User user = userRepository.findUserById(filter);
		if(user.getId() == null) {
			return new UserDTO(0L, "", "", "", "");
		}

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			    .parseCaseInsensitive()
			    .appendPattern("uuuu-MM-dd HH:mm:ss")
			    .toFormatter(Locale.ENGLISH);

		Instant instant = Instant.now();
		
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(user.getBirthDateTimestamp().longValue(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(instant));
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), localDateTime.format(formatter));		

		return userDTO;
	}

	
	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		
	}

}

