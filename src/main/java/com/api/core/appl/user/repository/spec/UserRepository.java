package com.api.core.appl.user.repository.spec;

import org.springframework.data.domain.Page;

import com.api.core.appl.user.User;
import com.api.core.appl.util.Filter;

public interface UserRepository {

	User createUser(User dadosPosicao);

	Page<User> listUsersByBirthDateAndFirstName(Filter filter);

	Page<User> listUsersByBirthDate(Filter filter);

	Page<User> listUsersByFirstName(Filter filter);

	Page<User> listUsers(Filter filter);

	User findUserById(Filter filter);
}
