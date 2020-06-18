package com.psl.AssignmentISV.Controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.psl.AssignmentISV.Exceptions.UserServiceException;
import com.psl.AssignmentISV.Request.Location;
import com.psl.AssignmentISV.Request.UserDetailsRequest;
import com.psl.AssignmentISV.Response.UserDetailsResponse;
import com.psl.AssignmentISV.Service.UserService;
import com.psl.AssignmentISV.Shared.dto.UserDTO;

@RestController
@RequestMapping("users")
public class WeatherController {

	@Autowired
	UserService userService;

	
	@PostMapping("/registration")
	public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userDetails) throws UserServiceException {

		UserDetailsResponse returnValue = new UserDetailsResponse();

		UserDTO userDto = new UserDTO();

		BeanUtils.copyProperties(userDetails, userDto);

		UserDTO createdUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;

	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@GetMapping("/{cityName}")
	public ResponseEntity<String> getWeather(@PathVariable String cityName)
			throws JsonMappingException, JsonProcessingException {
		String api_key="pZrqi9yXpiQVuG99rtCxHDEeJQfyVuzY";

		final String uri = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey="+api_key+"&q="
				+ cityName + "&language=en-us";
		RestTemplate restTemplate = new RestTemplate();
		Location[] loc = restTemplate.getForObject(uri, Location[].class);

		try {
			final String uri1 = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + loc[0].getKey()
					+ "?apikey="+api_key+"&language=en-us";
			RestTemplate restTemplate1 = new RestTemplate();

			String loc1 = restTemplate1.getForObject(uri1, String.class);
			return new ResponseEntity<String>(loc1, HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println("Please enter valid city name");
			return new ResponseEntity<String>("Please enter valid city name", HttpStatus.BAD_REQUEST);

		}

	}
	
}
