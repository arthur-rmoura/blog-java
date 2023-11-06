package com.api.core.appl.user.repository.spec;

import org.springframework.data.domain.Page;

import com.api.core.appl.user.User;
import com.api.core.appl.util.Filter;

public interface UserRepository {

	User createUser(User user);

	Page<User> listUsersByBirthDateAndFirstName(Filter filter);

	Page<User> listUsersByBirthDate(Filter filter);

	Page<User> listUsersByFirstName(Filter filter);

	Page<User> listUsers(Filter filter);

	User findUserById(Filter filter);

	User updateUser(User user);

	void deleteUser(Long userId);

	User findUserByEmail(String email);
}
