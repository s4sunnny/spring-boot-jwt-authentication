package com.sunny.auth.authentication.user.controller;

import javax.security.auth.login.CredentialExpiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.auth.authentication.config.ApplicationException;
import com.sunny.auth.authentication.config.jwt.JwtTokenUtil;
import com.sunny.auth.authentication.dto.JwtRequest;
import com.sunny.auth.authentication.dto.JwtResponse;
import com.sunny.auth.authentication.user.service.UserService;
import com.sunny.auth.authentication.user.service.UserServiceImpl;

@RestController
public class UserController {

//	@Autowired
//	private UserService service;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServiceImpl serviceImpl;

	@PostMapping("/login")
	public JwtResponse userLogin(@RequestBody JwtRequest jwtRequest) throws Exception {
		UserDetails userdetail = null;
		try {
			userdetail = serviceImpl.loadUserByUsername(jwtRequest.getUsername());
		} catch (BadCredentialsException e) {
			throw new Exception("Badcredential");
		} catch (DisabledException e) {
			throw new Exception("User is disabled");
		}
		String token = "";
		if (userdetail != null) {
			authenticate(jwtRequest);
			token = jwtTokenUtil.generateJWT(userdetail);
		} else {
			throw new Exception("Badcredential");
		}

		return new JwtResponse(token, "Success", "200");
	}

	private boolean authenticate(JwtRequest jwtRequest) throws Exception {
		try {
			System.out.println(jwtRequest.getPassword());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			return true;
		} catch (BadCredentialsException e) {
			throw new ApplicationException("Badcredential");
		} catch (DisabledException e) {
			throw new ApplicationException("User is disabled");
		}
	}

}
