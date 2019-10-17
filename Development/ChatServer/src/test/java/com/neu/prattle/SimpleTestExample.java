package com.neu.prattle;


import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.neu.prattle.service.UserService;
import com.neu.prattle.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import com.neu.prattle.model.User;

public class SimpleTestExample {

	private UserService as;
	@Before
	public void setUp() {
		as = UserServiceImpl.getInstance();
	}
	
	
	// This method just tries to add 
	@Test
	public void setUserTest(){
	   as.addUser(new User("Mike"));
	}
	
	// This method just tries to add 
	@Test
	public void getUserTest(){
		Optional<User> user = as.findUserByName("Mike");
		assertTrue(user.isPresent());
	}
}
