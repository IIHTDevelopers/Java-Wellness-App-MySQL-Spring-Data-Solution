package com.wellnessapp.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wellnessapp.entity.WellnessPlan;

@Repository
public interface WellnessPlanRepository extends JpaRepository<WellnessPlan, Long> {
	List<WellnessPlan> findByUserId(Long userId);

	List<WellnessPlan> findByUserIdAndPlanType(Long userId, String planType);

	List<WellnessPlan> findByDescriptionContainingOrGoalsContaining(String descriptionKeyword, String goalsKeyword);

	Page<WellnessPlan> findAllByUserIOrderByPlanTypeAsc(Long userId, Pageable pageable);

	/**
	 * Fetches a paginated list of WellnessPlan entities for a given user ID, sorted
	 * by plan type in ascending order.
	 */
	@Query("SELECT wp FROM WellnessPlan wp WHERE wp.user.id = :userId ORDER BY wp.planType ASC")
	Page<WellnessPlan> findAllByUserIdOrderedByPlanTypeAsc(Long userId, Pageable pageable);

	// Method to find wellness plans with a start date greater than or equal to a
	// specified date
	List<WellnessPlan> findByStartDateGreaterThanEqual(LocalDate date);
}
