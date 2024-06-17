package com.wellnessapp.functional;

import static com.wellnessapp.utils.MasterData.asJsonString;
import static com.wellnessapp.utils.MasterData.getUserDTO;
import static com.wellnessapp.utils.MasterData.getUserDTOList;
import static com.wellnessapp.utils.TestUtils.businessTestFile;
import static com.wellnessapp.utils.TestUtils.currentTest;
import static com.wellnessapp.utils.TestUtils.testReport;
import static com.wellnessapp.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wellnessapp.controller.UserController;
import com.wellnessapp.dto.UserDTO;
import com.wellnessapp.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateUser() throws Exception {
		UserDTO userDTO = getUserDTO();
		when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.CREATED.value() ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testListUsers() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		List<UserDTO> usersDTOList = getUserDTOList();
		when(userService.getAllUsers()).thenReturn(usersDTOList);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty() ? "true" : "false",
				businessTestFile);
	}
}
