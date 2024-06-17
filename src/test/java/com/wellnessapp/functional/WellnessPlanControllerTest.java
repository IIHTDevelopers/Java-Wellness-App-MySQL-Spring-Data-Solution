package com.wellnessapp.functional;

import static com.wellnessapp.utils.MasterData.asJsonString;
import static com.wellnessapp.utils.MasterData.getWellnessPlanDTO;
import static com.wellnessapp.utils.TestUtils.businessTestFile;
import static com.wellnessapp.utils.TestUtils.currentTest;
import static com.wellnessapp.utils.TestUtils.testReport;
import static com.wellnessapp.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class WellnessPlanControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WellnessPlanService wellnessPlanService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateWellnessPlan() throws Exception {
		WellnessPlanDTO wellnessPlanDTO = getWellnessPlanDTO();
		when(wellnessPlanService.createWellnessPlan(any(WellnessPlanDTO.class))).thenReturn(wellnessPlanDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/wellness-plans")
				.content(asJsonString(wellnessPlanDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.CREATED.value(), businessTestFile);
	}

	@Test
	public void testGetAllWellnessPlansByUserId() throws Exception {
//		List<WellnessPlanDTO> wellnessPlans = Arrays.asList(getWellnessPlanDTO());
//		when(wellnessPlanService.getAllWellnessPlansByUserId(anyLong())).thenReturn(wellnessPlans);
		Page<WellnessPlanDTO> itineraryDTOList = MasterData.getWellnessPlanDTOPage(0, 10);
		when(wellnessPlanService.getAllWellnessPlansByUserId(anyLong(), any(Pageable.class)))
				.thenReturn(itineraryDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wellness-plans/user/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty(), businessTestFile);
	}

	@Test
	public void testGetWellnessPlanById() throws Exception {
		WellnessPlanDTO wellnessPlanDTO = getWellnessPlanDTO();
		when(wellnessPlanService.getWellnessPlanById(anyLong())).thenReturn(wellnessPlanDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wellness-plans/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().equals(asJsonString(wellnessPlanDTO)),
				businessTestFile);
	}

	@Test
	public void testUpdateWellnessPlan() throws Exception {
		WellnessPlanDTO wellnessPlanDTO = getWellnessPlanDTO();
		when(wellnessPlanService.updateWellnessPlan(anyLong(), any(WellnessPlanDTO.class))).thenReturn(wellnessPlanDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/wellness-plans/1")
				.content(asJsonString(wellnessPlanDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.OK.value(), businessTestFile);
	}

	@Test
	public void testDeleteWellnessPlan() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/wellness-plans/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value(),
				businessTestFile);
	}

	@Test
	public void testFilterWellnessPlansByType() throws Exception {
		List<WellnessPlanDTO> wellnessPlans = Arrays.asList(getWellnessPlanDTO());
		when(wellnessPlanService.filterWellnessPlansByType(anyLong(), any(String.class))).thenReturn(wellnessPlans);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wellness-plans/filter?userId=1&planType=Diet")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty(), businessTestFile);
	}

	@Test
	public void testUpdateWellnessPlanGoals() throws Exception {
		WellnessPlanDTO wellnessPlanDTO = getWellnessPlanDTO();
		when(wellnessPlanService.updateWellnessPlanGoals(anyLong(), any(String.class))).thenReturn(wellnessPlanDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/wellness-plans/1/goals").content("New Goals")
				.contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == HttpStatus.OK.value(), businessTestFile);
	}

	@Test
	public void testSearchWellnessPlans() throws Exception {
		List<WellnessPlanDTO> wellnessPlans = Arrays.asList(getWellnessPlanDTO());
		when(wellnessPlanService.searchWellnessPlans(any(String.class), any(LocalDate.class)))
				.thenReturn(wellnessPlans);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/wellness-plans/search?keyword=test&startDate=2023-01-01").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty(), businessTestFile);
	}

	@Test
	public void testGetUpcomingWellnessPlans() throws Exception {
		List<WellnessPlanDTO> wellnessPlans = Arrays.asList(getWellnessPlanDTO());
		when(wellnessPlanService.getUpcomingWellnessPlans()).thenReturn(wellnessPlans);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wellness-plans/upcoming")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), !result.getResponse().getContentAsString().isEmpty(), businessTestFile);
	}
}