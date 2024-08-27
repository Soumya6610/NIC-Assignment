package com.roleapi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roleapi.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}
