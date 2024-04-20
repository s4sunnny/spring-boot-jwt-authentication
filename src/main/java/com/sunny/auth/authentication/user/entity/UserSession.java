package com.sunny.auth.authentication.user.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserSession implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;
	private Long userId;
	private String userType;
	private String userRole;

	public UserSession(User user) {
		this.user = user;
		this.userId = user.getUserId();
		this.userType = user.getUserType();
		this.userRole = user.getUserRole();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getUserRole()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getIsLocked() == 1 ? false : true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return user.getIsActive() == 1 ? true : false;
	}

}
