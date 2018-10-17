package com.cm.style.profile.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication manager to provide 
 * the auth details
 * @author DHIVAKART
 *
 */
public class CustomAuthenticationManager implements AuthenticationManager{

	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authenticationProvider.authenticate(authentication);
	}

}
