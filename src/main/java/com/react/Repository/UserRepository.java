package com.react.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.react.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.full_name like %:query%")
	public List<User> searchUsers(String query);
}
