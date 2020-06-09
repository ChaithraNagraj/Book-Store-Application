package com.bridgelabz.bookstoreapp.repository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstoreapp.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "Select * from users where email_Id = :email", nativeQuery = true)
	User findEmail(String email);
	
	@Query(value = "select * from users where uid = :id", nativeQuery = true)
	User findById(long id);
	
	@Modifying
	@Transactional
	@Query(value = "update users set modified_Date = ? where uid = ?", nativeQuery = true)
	void modifiedDate(LocalDateTime date, long id);

	@Modifying
	@Query(value = "update users set is_verified = true where uid = :id", nativeQuery = true)
	void verify(long id);
}
