package com.wellnessapp.boundary;

import static com.wellnessapp.utils.TestUtils.boundaryTestFile;
import static com.wellnessapp.utils.TestUtils.currentTest;
import static com.wellnessapp.utils.TestUtils.testReport;
import static com.wellnessapp.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wellnessapp.dto.WellnessPlanDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class WellnessPlanBoundaryTest {

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
	public void testPlanTypeNotBlank() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType(""); // Blank plan type to trigger the NotBlank validation
		planDTO.setDescription("A valid description");
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDescriptionNotBlank() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType("Diet");
		planDTO.setDescription(""); // Blank description to trigger the NotBlank validation
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testGoalsNotBlank() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType("Diet");
		planDTO.setDescription("A valid description");
		planDTO.setGoals(""); // Blank goals to trigger the NotBlank validation
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStartDateNotNull() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType("Diet");
		planDTO.setDescription("A valid description");
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(null); // Null start date to trigger the NotNull validation
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEndDateNotNull() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType("Diet");
		planDTO.setDescription("A valid description");
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(null); // Null end date to trigger the NotNull validation
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testPlanTypeSize() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		// Create a plan type string longer than the maximum allowed size of 20
		// characters
		String longPlanType = new String(new char[21]).replace('\0', 'a');
		planDTO.setPlanType(longPlanType);
		planDTO.setDescription("A valid description");
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDescriptionSize() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		// Create a description string longer than the maximum allowed size of 500
		// characters
		String longDescription = new String(new char[501]).replace('\0', 'a');
		planDTO.setPlanType("Diet");
		planDTO.setDescription(longDescription);
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testGoalsSize() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		// Create a goals string longer than the maximum allowed size of 200 characters
		String longGoals = new String(new char[201]).replace('\0', 'a');
		planDTO.setPlanType("Diet");
		planDTO.setDescription("A valid description");
		planDTO.setGoals(longGoals);
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStartDateIsFutureOrPresent() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType("Diet");
		planDTO.setDescription("A valid description");
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now().minusDays(1)); // Past date to trigger the FutureOrPresent validation
		planDTO.setEndDate(LocalDate.now().plusDays(30));
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEndDateIsFutureOrPresent() throws IOException {
		WellnessPlanDTO planDTO = new WellnessPlanDTO();
		planDTO.setPlanType("Diet");
		planDTO.setDescription("A valid description");
		planDTO.setGoals("Valid goals");
		planDTO.setStartDate(LocalDate.now());
		planDTO.setEndDate(LocalDate.now().minusDays(1)); // Past date to trigger the FutureOrPresent validation
		Set<ConstraintViolation<WellnessPlanDTO>> violations = validator.validate(planDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}