package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.Recommendation;
import com.master.practiceReact.models.enums.RecommendationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByStatus(RecommendationStatus status);
    List<Recommendation> findByKidIdAndStatus(Long kidId, RecommendationStatus status); // ✅ keep this
}
