package com.api.core.appl.user.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.user.User;


public interface UserRepositoryData extends PagingAndSortingRepository<User, Long> {
	
	Page<User> findAll(Pageable pageable);

	Page<User> findByFirstName(String firstName, Pageable pageable);

	Page<User> findByBirthDate(long dateTimestampF1, Pageable pageable);

	Page<User> findByBirthDateAndFirstName(long dateTimestamp, String firstName, Pageable pageable);

	
}
