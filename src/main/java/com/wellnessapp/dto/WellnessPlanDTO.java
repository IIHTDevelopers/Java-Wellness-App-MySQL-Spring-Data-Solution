package com.wellnessapp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WellnessPlanDTO {
	private Long id;
	private Long userId;

	@NotBlank(message = "Plan type is required")
	@Size(min = 3, max = 20, message = "Plan type must be between 3 and 20 characters")
	private String planType;

	@NotBlank(message = "Description is required")
	@Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
	private String description;

	@NotBlank(message = "Goals are required")
	@Size(min = 5, max = 200, message = "Goals must be between 5 and 200 characters")
	private String goals;

	@NotNull(message = "Start date is required")
	@FutureOrPresent(message = "Start date must be in the future or present")
	private LocalDate startDate;

	@NotNull(message = "End date is required")
	@FutureOrPresent(message = "End date must be in the future or present")
	private LocalDate endDate;

	public WellnessPlanDTO() {
	}

	public WellnessPlanDTO(Long id, Long userId, String planType, String description, String goals, LocalDate startDate,
			LocalDate endDate) {
		this.id = id;
		this.userId = userId;
		this.planType = planType;
		this.description = description;
		this.goals = goals;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public WellnessPlanDTO(String planType, String description, String goals, LocalDate startDate, LocalDate endDate) {
		this.planType = planType;
		this.description = description;
		this.goals = goals;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
