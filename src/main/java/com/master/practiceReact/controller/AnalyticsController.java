package com.master.practiceReact.controller;
import com.master.practiceReact.models.Entity.Attempt;
import com.master.practiceReact.repository.*;
import com.master.practiceReact.models.Entity.Recommendation;
import com.master.practiceReact.models.enums.RecommendationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:3000")
public class AnalyticsController {

    private final LearningSessionRepository sessionRepo;
    private final AttemptRepository attemptRepo;
    private final TopicProgressRepository topicProgressRepo;
    private final AnalyticsSnapshotRepository snapshotRepo;
    private final RecommendationRepository recommendationRepo;
    private final ToDoRepository todoRepo;
    private final KidRepository kidRepository;
    private final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
    public AnalyticsController(LearningSessionRepository sessionRepo, AttemptRepository attemptRepo, TopicProgressRepository topicProgressRepo, AnalyticsSnapshotRepository snapshotRepo, RecommendationRepository recommendationRepo, ToDoRepository todoRepo, KidRepository kidRepository) {
        this.sessionRepo = sessionRepo;
        this.attemptRepo = attemptRepo;
        this.topicProgressRepo = topicProgressRepo;
        this.snapshotRepo = snapshotRepo;
        this.recommendationRepo = recommendationRepo;
        this.todoRepo = todoRepo;
        this.kidRepository = kidRepository;
    }

    // -----------------------------
    // OVERVIEW
    // -----------------------------
    @GetMapping("/kids/{kidId}/overview")
    public Map<String, Object> overview(@PathVariable Long kidId) {
        logger.info("Requesting Kid overview for Kid with ID: {}", kidId );
        var sessions = sessionRepo.findByKidId(kidId);
        var attempts = attemptRepo.findBySessionKidId(kidId);

        long totalMinutes = sessions.stream()
                .filter(s -> s.getEndTime() != null)
                .mapToLong(s ->
                        Duration.between(s.getStartTime(), s.getEndTime()).toMinutes()
                )
                .sum();

        long correct = attempts.stream()
                .filter(Attempt::isCorrect)
                .count();

        Map<String, Object> res = new HashMap<>();
        res.put("sessions", sessions.size());
        res.put("totalMinutes", totalMinutes);
        res.put("totalAttempts", attempts.size());
        res.put("correctAttempts", correct);
        res.put(
                "accuracy",
                attempts.isEmpty() ? 0 : (correct * 100.0 / attempts.size())
        );

        return res;
    }

    // -----------------------------
    // TIME SERIES
    // -----------------------------
    @GetMapping("/kids/{kidId}/time-series")
    public List<Map<String, Object>> timeSeries(@PathVariable Long kidId) {

        return sessionRepo.findByKidId(kidId).stream()
                .filter(s -> s.getEndTime() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getStartTime().toLocalDate().toString(),
                        Collectors.summingLong(
                                s -> Duration.between(
                                        s.getStartTime(),
                                        s.getEndTime()
                                ).toMinutes()
                        )
                ))
                .entrySet()
                .stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("day", e.getKey());
                    map.put("minutes", e.getValue());
                    return map;
                })
                .sorted(Comparator.comparing(m -> m.get("day").toString()))
                .toList();
    }

    // -----------------------------
    // ACCURACY TREND
    // -----------------------------
    @GetMapping("/kids/{kidId}/accuracy-trend")
    public List<?> accuracyTrend(@PathVariable Long kidId) {
        return snapshotRepo.findByKidIdOrderBySnapshotDateAsc(kidId);
    }

    // -----------------------------
    // TOPIC MASTERY
    // -----------------------------
    @GetMapping("/kids/{kidId}/topics")
    public List<?> topicMastery(@PathVariable Long kidId) {
        return topicProgressRepo.findByKidId(kidId);
    }

    // -----------------------------
    // RECOMMENDATIONS
    // -----------------------------
    @GetMapping("/kids/{kidId}/recommendations")
    public List<Recommendation> recommendations(@PathVariable Long kidId) {
        // Convert the string to enum
        RecommendationStatus status = RecommendationStatus.valueOf("ACTIVE");
        return recommendationRepo.findByKidIdAndStatus(kidId, status);
    }
    // -----------------------------
    // TODO PROGRESS
    // -----------------------------
    // -----------------------------

//    @GetMapping("/kids/{kidId}/todos")
//    public List<Map<String, Object>> todos(@PathVariable Long kidId) {
//        logger.info("Request TODO for Parent/Kid with ID: {}", kidId);
//        var todos = todoRepo.findByKidIdAndArchivedFalse(kidId);
//        logger.info("ToDo size: {}", todos != null ? todos.size(): 0);
//        return todos.stream()
//                .map(t -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", t.getId());
//                    map.put("text", t.getText());
//                    map.put("completed", t.getCompleted());
//                    map.put("priority", t.getPriority());
//                    map.put("createdAt", t.getCreatedAt());
//                    map.put("updatedAt", t.getUpdatedAt());
//                    map.put("completedAt", t.getCompletedAt());
//                    return map;
//                })
//                .toList();
//    }

}
