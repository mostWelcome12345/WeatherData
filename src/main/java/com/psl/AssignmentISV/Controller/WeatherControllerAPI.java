package com.psl.AssignmentISV.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psl.AssignmentISV.Request.Location;

@Controller
@RequestMapping("Weather")
public class WeatherControllerAPI {
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
