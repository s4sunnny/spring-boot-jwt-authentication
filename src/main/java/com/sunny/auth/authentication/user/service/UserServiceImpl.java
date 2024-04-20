package com.sunny.auth.authentication.user.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sunny.auth.authentication.user.entity.User;
import com.sunny.auth.authentication.user.entity.UserSession;
import com.sunny.auth.authentication.user.repository.UserRepository;

public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new BadCredentialsException("BAd Credential"));
		
		Set<GrantedAuthority> ga = new HashSet<GrantedAuthority>();
		ga.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
		user.setAuthorities(ga);
		return new UserSession(user);
	}

}
