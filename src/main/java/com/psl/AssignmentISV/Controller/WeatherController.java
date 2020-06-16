package com.psl.AssignmentISV.Controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.AssignmentISV.Request.UserDetailsRequest;
import com.psl.AssignmentISV.Response.UserDetailsResponse;
import com.psl.AssignmentISV.Service.UserService;
import com.psl.AssignmentISV.Shared.dto.UserDTO;

@RestController
@RequestMapping("users")
public class WeatherController {

	@Autowired
	UserService userService;

	@GetMapping("/getuser")
	public String getUser() {
		return "its working";
	}

	// user details to be stored in the database
	@PostMapping
	public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userDetails) {

		UserDetailsResponse returnValue = new UserDetailsResponse();

		UserDTO userDto = new UserDTO();

		BeanUtils.copyProperties(userDetails, userDto);

		UserDTO createdUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;

	}
}
