package com.wellnessapp.boundary;

import static com.wellnessapp.utils.TestUtils.boundaryTestFile;
import static com.wellnessapp.utils.TestUtils.currentTest;
import static com.wellnessapp.utils.TestUtils.testReport;
import static com.wellnessapp.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wellnessapp.dto.UserDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserBoundaryTest {

	private static Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testUsernameNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(""); // Blank username to trigger the NotBlank validation
		userDTO.setEmail("valid.email@example.com");
		userDTO.setPassword("ValidPassword123");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailValidFormat() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("ValidUsername");
		userDTO.setEmail("invalid-email"); // Invalid email format
		userDTO.setPassword("ValidPassword123");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testPasswordSize() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("ValidUsername");
		userDTO.setEmail("valid.email@example.com");
		userDTO.setPassword("short"); // Password too short to meet the @Size criteria
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testUsernameLength() throws IOException {
		UserDTO userDTO = new UserDTO();
		// Create a username string longer than the maximum allowed size
		String longUsername = new String(new char[51]).replace('\0', 'a');
		userDTO.setUsername(longUsername);
		userDTO.setEmail("valid.email@example.com");
		userDTO.setPassword("ValidPassword123");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailLength() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("ValidUsername");
		// Create an email string longer than the maximum allowed size
		String longEmail = "valid.email@" + new String(new char[80]).replace('\0', 'a') + ".com";
		userDTO.setEmail(longEmail);
		userDTO.setPassword("ValidPassword123");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testPasswordNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
