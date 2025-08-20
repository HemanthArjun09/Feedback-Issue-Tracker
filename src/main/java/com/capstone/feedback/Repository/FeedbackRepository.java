package com.capstone.feedback.Repository;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.Feedback;
import com.capstone.feedback.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    // This query finds all feedback for a given facility ID
    List<Feedback> findByFacility(Facility facility);
    boolean existsByUserAndFacility(User user, Facility facility);
}