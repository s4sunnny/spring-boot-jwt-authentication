package com.sunny.auth.authentication.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunny.auth.authentication.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
}
