package com.pk96375.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pk96375.security.service.CustomUserDetailsService;
import com.pk96375.security.utils.JWTTokenUtils;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenUtils jwtUtility;

	@Autowired
	private CustomUserDetailsService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authorisation = request.getHeader("Authorization");
		String username = null;
		String token = null;
		if (null != authorisation && authorisation.startsWith("Bearer ")) {
			token = authorisation.substring(7);
			username = jwtUtility.getUsernameFromToken(token);
			if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userdetails = userService.loadUserByUsername(username);
				if (jwtUtility.validateToken(token, userdetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userdetails, null, userdetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}

		}
		filterChain.doFilter(request, response);
	}

}
