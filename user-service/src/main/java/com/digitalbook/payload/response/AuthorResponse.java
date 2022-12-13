package com.digitalbook.payload.response;

import org.springframework.data.rest.core.config.Projection;

import com.digitalbook.entity.ERole;
import com.digitalbook.entity.Role;
import com.digitalbook.entity.User;

@Projection(types = { Role.class, User.class })
public interface AuthorResponse {

	Integer getId();

	ERole getName();
	
	String getUsername();
	Integer getUserId();

}
