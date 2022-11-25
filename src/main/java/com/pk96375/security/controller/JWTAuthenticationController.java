package com.pk96375.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pk96375.security.VO.JWTRequest;
import com.pk96375.security.VO.JWTResponse;
import com.pk96375.security.service.CustomUserDetailsService;
import com.pk96375.security.utils.JWTTokenUtils;

@RestController
public class JWTAuthenticationController {

	@Autowired
	JWTTokenUtils jwtUtils;
	@Autowired
	CustomUserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	CustomUserDetailsService service;

	@PostMapping("/authenticate")
	public JWTResponse authenticate(@RequestBody JWTRequest request) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		} catch (BadCredentialsException ex) {
			throw new Exception("INVALID_CREDS", ex);
		}
		final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
		String token = jwtUtils.generateToken(userDetails);

		return new JWTResponse(token);
			}
}
