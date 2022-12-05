package com.digitalbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.repository.RoleRepository;
import com.digitalbook.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

}
