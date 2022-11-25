package com.pk96375.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pk96375.security.VO.UserVO;
import com.pk96375.security.model.User;
import com.pk96375.security.repo.UserRepository;

@RestController
public class SecurityController {

	@Autowired
	UserRepository repo;

	@GetMapping("/home")
	public String getUser() {
		return "This is User home";
	}

	@GetMapping("/admin")
	public String getAdmin() {
		return "This is Admin home";
	}

	@PostMapping("/save")
	public Long saveUserDetails(@RequestBody UserVO uservo) {
		User user = new User();
		user.setPassword(new BCryptPasswordEncoder().encode(uservo.getPassword()));
		user.setUsername(uservo.getUsername());
		user.setRole(uservo.getRole());

		return repo.save(user).getId();
	}
}
