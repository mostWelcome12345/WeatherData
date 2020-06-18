package com.psl.AssignmentISV.ServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.psl.AssignmentISV.Entity.UserEntity;
import com.psl.AssignmentISV.Shared.Utils;
import com.psl.AssignmentISV.Shared.dto.UserDTO;
import com.psl.AssignmentISV.UserRepository.UserRepository;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceimpl;

	@Mock
	UserRepository userRepository;
	
	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUser() {

		UserEntity userEntiry = new UserEntity();

		userEntiry.setId(1L);
		userEntiry.setFirstName("Swagata");
		userEntiry.setUserId("cfgvhbjk6789");
		userEntiry.setEncryptedPassword("fghjkl5678fghjk4e478");

		when(userRepository.findByemail(anyString())).thenReturn(userEntiry);

		UserDTO userDTO = userServiceimpl.getUser("test@test.com");

		assertNotNull(userDTO);

		assertEquals("Swagata", userDTO.getFirstName());

	}

	@Test
	final void testGetUser_UsernameNotFoundException() {
		when(userRepository.findByemail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class,

				() -> {

					userServiceimpl.getUser("test@test.com");
				}

		);

	}

}
