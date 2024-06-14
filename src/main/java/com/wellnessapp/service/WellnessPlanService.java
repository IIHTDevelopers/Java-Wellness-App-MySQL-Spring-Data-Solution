package com.wellnessapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wellnessapp.dto.WellnessPlanDTO;

public interface WellnessPlanService {
	WellnessPlanDTO createWellnessPlan(WellnessPlanDTO wellnessPlan);

	Page<WellnessPlanDTO> getAllWellnessPlansByUserId(Long userId, Pageable pageable);

	WellnessPlanDTO getWellnessPlanById(Long id);

	WellnessPlanDTO updateWellnessPlan(Long id, WellnessPlanDTO wellnessPlan);

	boolean deleteWellnessPlan(Long id);

	List<WellnessPlanDTO> filterWellnessPlansByType(Long userId, String planType);

	WellnessPlanDTO updateWellnessPlanGoals(Long id, String goals);

	List<WellnessPlanDTO> searchWellnessPlans(String keyword, LocalDate startDate);

	WellnessPlanDTO extendWellnessPlanEndDate(Long id, LocalDate newEndDate);

	List<WellnessPlanDTO> getUpcomingWellnessPlans();
}
