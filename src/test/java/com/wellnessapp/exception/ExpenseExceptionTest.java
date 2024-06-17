package com.wellnessapp.exception;

import static com.wellnessapp.utils.TestUtils.currentTest;
import static com.wellnessapp.utils.TestUtils.exceptionTestFile;
import static com.wellnessapp.utils.TestUtils.testReport;
import static com.wellnessapp.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

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

import com.wellnessapp.controller.WellnessPlanController;
import com.wellnessapp.dto.WellnessPlanDTO;
import com.wellnessapp.service.WellnessPlanService;
import com.wellnessapp.utils.MasterData;

@WebMvcTest(WellnessPlanController.class)
@AutoConfigureMockMvc
public class ExpenseExceptionTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WellnessPlanService expenseService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateWellnessPlanInvalidDataException() throws Exception {
		WellnessPlanDTO expenseDTO = MasterData.getWellnessPlanDTO();
		expenseDTO.setPlanType("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/wellness-plans")
				.content(MasterData.asJsonString(expenseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateWellnessPlanInvalidDataException() throws Exception {
		WellnessPlanDTO expenseDTO = MasterData.getWellnessPlanDTO();
		expenseDTO.setPlanType("");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/wellness-plans/" + expenseDTO.getId())
				.content(MasterData.asJsonString(expenseDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetWellnessPlanByIdResourceNotFoundException() throws Exception {
		WellnessPlanDTO expenseDTO = MasterData.getWellnessPlanDTO();
		expenseDTO.setId(100L);
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Wellness plan not found");

		when(this.expenseService.getWellnessPlanById(expenseDTO.getId()))
				.thenThrow(new ResourceNotFoundException("Wellness plan not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wellness-plans/" + expenseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testDeleteWellnessPlanByIdResourceNotFoundException() throws Exception {
		WellnessPlanDTO expenseDTO = MasterData.getWellnessPlanDTO();
		expenseDTO.setId(100L);
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Wellness plan not found");

		when(this.expenseService.deleteWellnessPlan(expenseDTO.getId()))
				.thenThrow(new ResourceNotFoundException("Wellness plan not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/wellness-plans/" + expenseDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

}
