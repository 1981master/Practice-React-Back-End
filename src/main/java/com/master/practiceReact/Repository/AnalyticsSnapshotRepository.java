package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.AnalyticsSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnalyticsSnapshotRepository extends JpaRepository<AnalyticsSnapshot, Long> {
    List<AnalyticsSnapshot> findByKidIdOrderBySnapshotDateDesc(Long kidId);
    List<AnalyticsSnapshot> findByKidIdOrderBySnapshotDateAsc(Long kidId);
}
