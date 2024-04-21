package com.sunny.auth.authentication.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunny.auth.authentication.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);

}
