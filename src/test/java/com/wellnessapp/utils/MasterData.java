package com.wellnessapp.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wellnessapp.dto.UserDTO;
import com.wellnessapp.dto.WellnessPlanDTO;

public class MasterData {

	public static UserDTO getUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("JohnDoe");
		userDTO.setEmail("john.doe@example.com");
		userDTO.setPassword("password"); // Note: In real scenarios, passwords should be encrypted
		return userDTO;
	}

	public static List<UserDTO> getUserDTOList() {
		return Arrays.asList(getUserDTO());
	}

	public static Page<UserDTO> getUserDTOPage(int page, int size) {
		List<UserDTO> userDTOList = getUserDTOList();
		PageRequest pageRequest = PageRequest.of(page, size);
		return new PageImpl<>(userDTOList, pageRequest, userDTOList.size());
	}

	public static WellnessPlanDTO getWellnessPlanDTO() {
		WellnessPlanDTO wellnessPlanDTO = new WellnessPlanDTO();
		wellnessPlanDTO.setPlanType("Diet");
		wellnessPlanDTO.setDescription("This is a test diet plan.");
		wellnessPlanDTO.setGoals("Lose weight");
		wellnessPlanDTO.setStartDate(LocalDate.now());
		wellnessPlanDTO.setEndDate(LocalDate.now().plusMonths(1));
		return wellnessPlanDTO;
	}

	public static List<WellnessPlanDTO> getWellnessPlanDTOList() {
		return Arrays.asList(getWellnessPlanDTO());
	}

	public static Page<WellnessPlanDTO> getWellnessPlanDTOPage(int page, int size) {
		List<WellnessPlanDTO> wellnessPlanDTOList = getWellnessPlanDTOList();
		PageRequest pageRequest = PageRequest.of(page, size);
		return new PageImpl<>(wellnessPlanDTOList, pageRequest, wellnessPlanDTOList.size());
	}

	public static String asJsonString(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
