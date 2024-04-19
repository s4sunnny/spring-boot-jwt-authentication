package com.sunny.auth.authentication.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.auth.authentication.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;

}
