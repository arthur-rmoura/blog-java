package com.api.core.appl.user.repository.spec;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api.core.appl.user.User;


public interface UserRepositoryData extends PagingAndSortingRepository<User, Long> {
	
	Page<User> findAll(Pageable pageable);

	Page<User> findByFirstName(String firstName, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.firstName like CONCAT('%',:firstName,'%')")
	Page<User> findFirstNameLike(String firstName, Pageable pageable);

	Page<User> findByBirthDateTimestamp(long dateTimestampF1, Pageable pageable);

	Page<User> findByBirthDateTimestampAndFirstName(long dateTimestamp, String firstName, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.firstName like CONCAT('%',:firstName,'%') and u.birthDateTimestamp = :dateTimestamp")
	Page<User> findBirthDateTimestampAndFirstNameLike(long dateTimestamp, String firstName, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.birthDateTimestamp between :dateTimestampStart and :dateTimestampEnd")
	Page<User> findIntervalBirthDateTimestamp(Long dateTimestampStart, Long dateTimestampEnd, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.firstName = :firstName and u.birthDateTimestamp between :dateTimestampStart and :dateTimestampEnd")
	Page<User> findIntervalBirthDateTimestampAndFirstName(Long dateTimestampStart, Long dateTimestampEnd, String firstName, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.firstName like CONCAT('%',:firstName,'%') and u.birthDateTimestamp between :dateTimestampStart and :dateTimestampEnd")
	Page<User> findIntervalBirthDateTimestampAndFirstNameLike(Long dateTimestampStart, Long dateTimestampEnd, String firstName,
			Pageable pageable);

	
}
