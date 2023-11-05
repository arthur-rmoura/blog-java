package com.api.core.appl.user.repository.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.core.appl.user.User;
import com.api.core.appl.user.repository.spec.UserRepository;
import com.api.core.appl.user.repository.spec.UserRepositoryData;
import com.api.core.appl.util.Filter;
import com.api.core.appl.util.UtilLibrary;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	private final UserRepositoryData userRepositoryData;
	
	public UserRepositoryImpl(UserRepositoryData userRepositoryData) {
		super();
		this.userRepositoryData = userRepositoryData;
	}


	@Override
	public Page<User> listUsers(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return userRepositoryData.findAll(pageable);
	}


	@Override
	public User createUser(User user) {
		return userRepositoryData.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepositoryData.save(user);
	}
	
	@Override
	public void deleteUser(Long userId) {
		userRepositoryData.deleteById(userId);
	}

	@Override
	public Page<User> listUsersByBirthDateAndFirstName(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		Page<User> userPage = userRepositoryData.findByBirthDateTimestampAndFirstName(UtilLibrary.getDateTimestampF1(filter.getBirthDate()), filter.getFirstName(), pageable);
		if(userPage.getContent().size() == 0) {
			userPage = userRepositoryData.findBirthDateTimestampAndFirstNameLike(UtilLibrary.getDateTimestampF1(filter.getBirthDate()), filter.getFirstName(), pageable);
		}
		
		return userPage;
	}
	
	@Override
	public Page<User> listUsersByBirthDate(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		return userRepositoryData.findByBirthDateTimestamp(UtilLibrary.getDateTimestampF1(filter.getBirthDate()), pageable);
	}


	@Override
	public Page<User> listUsersByFirstName(Filter filter) {
		Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
		Page<User> userPage =  userRepositoryData.findByFirstName(filter.getFirstName(), pageable);
		if(userPage.getContent().size() == 0) {
			userPage = userRepositoryData.findFirstNameLike(filter.getFirstName(), pageable);
		}
		
		return userPage;
	}


	@Override
	public User findUserById(Filter filter) {
		try {
			User user = userRepositoryData.findById(filter.getUserId()).get();
			return user;
		}
		catch (NoSuchElementException  e) {
			return new User("", "", "", "", 0L);
		}
	}
}
