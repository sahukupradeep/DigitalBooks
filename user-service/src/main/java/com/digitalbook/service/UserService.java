package com.digitalbook.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitalbook.repository.RoleRepository;
import com.digitalbook.repository.UserRepository;

public class UserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

}
