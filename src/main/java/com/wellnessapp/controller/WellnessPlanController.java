package com.wellnessapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellnessapp.dto.WellnessPlanDTO;
import com.wellnessapp.service.WellnessPlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wellness-plans")
public class WellnessPlanController {

	private final WellnessPlanService wellnessPlanService;

	@Autowired
	public WellnessPlanController(WellnessPlanService wellnessPlanService) {
		this.wellnessPlanService = wellnessPlanService;
	}

	@PostMapping
	public ResponseEntity<WellnessPlanDTO> createWellnessPlan(@RequestBody @Valid WellnessPlanDTO wellnessPlanDto) {
		WellnessPlanDTO createdPlan = wellnessPlanService.createWellnessPlan(wellnessPlanDto);
		return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Page<WellnessPlanDTO>> getAllWellnessPlansByUserId(@PathVariable Long userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<WellnessPlanDTO> plans = wellnessPlanService.getAllWellnessPlansByUserId(userId, pageable);
		return ResponseEntity.ok(plans);
	}

	@GetMapping("/{id}")
	public ResponseEntity<WellnessPlanDTO> getWellnessPlanById(@PathVariable Long id) {
		WellnessPlanDTO planDto = wellnessPlanService.getWellnessPlanById(id);
		return ResponseEntity.ok(planDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<WellnessPlanDTO> updateWellnessPlan(@PathVariable Long id,
			@Valid @RequestBody WellnessPlanDTO wellnessPlanDto) {
		WellnessPlanDTO updatedPlan = wellnessPlanService.updateWellnessPlan(id, wellnessPlanDto);
		return ResponseEntity.ok(updatedPlan);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteWellnessPlan(@PathVariable Long id) {
		wellnessPlanService.deleteWellnessPlan(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/filter")
	public ResponseEntity<List<WellnessPlanDTO>> filterWellnessPlansByType(@RequestParam Long userId,
			@RequestParam String planType) {
		List<WellnessPlanDTO> plans = wellnessPlanService.filterWellnessPlansByType(userId, planType);
		return ResponseEntity.ok(plans);
	}

	@PatchMapping("/{id}/goals")
	public ResponseEntity<WellnessPlanDTO> updateWellnessPlanGoals(@PathVariable Long id, @RequestBody String goals) {
		WellnessPlanDTO planDto = wellnessPlanService.updateWellnessPlanGoals(id, goals);
		return ResponseEntity.ok(planDto);
	}

	@GetMapping("/search")
	public ResponseEntity<List<WellnessPlanDTO>> searchWellnessPlans(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
		List<WellnessPlanDTO> plans = wellnessPlanService.searchWellnessPlans(keyword, startDate);
		return ResponseEntity.ok(plans);
	}

	@PostMapping("/{id}/extend")
	public ResponseEntity<WellnessPlanDTO> extendWellnessPlanEndDate(@PathVariable Long id,
			@RequestBody LocalDate newEndDate) {
		WellnessPlanDTO planDto = wellnessPlanService.extendWellnessPlanEndDate(id, newEndDate);
		return ResponseEntity.ok(planDto);
	}

	@GetMapping("/upcoming")
	public ResponseEntity<List<WellnessPlanDTO>> getUpcomingWellnessPlans() {
		List<WellnessPlanDTO> plans = wellnessPlanService.getUpcomingWellnessPlans();
		return ResponseEntity.ok(plans);
	}
}
