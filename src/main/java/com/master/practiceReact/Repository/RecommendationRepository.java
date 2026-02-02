package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByKidIdAndStatus(Long kidId, String status);
}
