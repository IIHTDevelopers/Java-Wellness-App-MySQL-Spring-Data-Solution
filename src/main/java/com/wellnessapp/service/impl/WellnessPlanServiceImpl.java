package com.wellnessapp.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wellnessapp.dto.WellnessPlanDTO;
import com.wellnessapp.entity.WellnessPlan;
import com.wellnessapp.exception.ResourceNotFoundException;
import com.wellnessapp.repo.WellnessPlanRepository;
import com.wellnessapp.service.WellnessPlanService;

import jakarta.transaction.Transactional;

@Service
public class WellnessPlanServiceImpl implements WellnessPlanService {

	private final WellnessPlanRepository wellnessPlanRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public WellnessPlanServiceImpl(WellnessPlanRepository wellnessPlanRepository) {
		this.wellnessPlanRepository = wellnessPlanRepository;
	}

	@Transactional
	@Override
	public WellnessPlanDTO createWellnessPlan(WellnessPlanDTO wellnessPlanDto) {
		WellnessPlan wellnessPlan = new WellnessPlan();
		populateWellnessPlanEntityFromDto(wellnessPlan, wellnessPlanDto);
		wellnessPlan = wellnessPlanRepository.save(wellnessPlan);
		return convertEntityToDto(wellnessPlan);
	}

	@Override
	public Page<WellnessPlanDTO> getAllWellnessPlansByUserId(Long userId, Pageable pageable) {
		Page<WellnessPlan> plans = wellnessPlanRepository.findAllByUserIdOrderedByPlanTypeAsc(userId, pageable);
		return plans.map(plan -> modelMapper.map(plan, WellnessPlanDTO.class));
	}

	@Override
	public WellnessPlanDTO getWellnessPlanById(Long id) {
		WellnessPlan plan = wellnessPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Wellness plan not found"));
		return convertEntityToDto(plan);
	}

	@Transactional
	@Override
	public WellnessPlanDTO updateWellnessPlan(Long id, WellnessPlanDTO wellnessPlanDto) {
		WellnessPlan wellnessPlan = wellnessPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Wellness plan not found"));
		populateWellnessPlanEntityFromDto(wellnessPlan, wellnessPlanDto);
		wellnessPlan = wellnessPlanRepository.save(wellnessPlan);
		return convertEntityToDto(wellnessPlan);
	}

	@Transactional
	@Override
	public boolean deleteWellnessPlan(Long id) {
		WellnessPlan plan = wellnessPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Wellness plan not found"));
		wellnessPlanRepository.delete(plan);
		return true;
	}

	@Override
	public List<WellnessPlanDTO> filterWellnessPlansByType(Long userId, String planType) {
		List<WellnessPlan> plans = wellnessPlanRepository.findByUserIdAndPlanType(userId, planType);
		return plans.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	@Override
	public WellnessPlanDTO updateWellnessPlanGoals(Long id, String goals) {
		WellnessPlan plan = wellnessPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Wellness plan not found"));
		plan.setGoals(goals);
		plan = wellnessPlanRepository.save(plan);
		return convertEntityToDto(plan);
	}

	@Override
	public List<WellnessPlanDTO> searchWellnessPlans(String keyword, LocalDate startDate) {
		List<WellnessPlan> plans = wellnessPlanRepository.findByDescriptionContainingOrGoalsContaining(keyword,
				keyword);
		if (startDate != null) {
			plans = plans.stream().filter(p -> !p.getStartDate().isBefore(startDate)).collect(Collectors.toList());
		}
		return plans.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	@Override
	public WellnessPlanDTO extendWellnessPlanEndDate(Long id, LocalDate newEndDate) {
		WellnessPlan plan = wellnessPlanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Wellness plan not found"));
		plan.setEndDate(newEndDate);
		plan = wellnessPlanRepository.save(plan);
		return convertEntityToDto(plan);
	}

	@Override
	public List<WellnessPlanDTO> getUpcomingWellnessPlans() {
		LocalDate today = LocalDate.now();
		List<WellnessPlan> plans = wellnessPlanRepository.findByStartDateGreaterThanEqual(today);
		return plans.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	private WellnessPlanDTO convertEntityToDto(WellnessPlan plan) {
		return new WellnessPlanDTO(plan.getPlanType(), plan.getDescription(), plan.getGoals(), plan.getStartDate(),
				plan.getEndDate());
	}

	private void populateWellnessPlanEntityFromDto(WellnessPlan plan, WellnessPlanDTO dto) {
		plan.setPlanType(dto.getPlanType());
		plan.setDescription(dto.getDescription());
		plan.setGoals(dto.getGoals());
		plan.setStartDate(dto.getStartDate());
		plan.setEndDate(dto.getEndDate());
	}
}
