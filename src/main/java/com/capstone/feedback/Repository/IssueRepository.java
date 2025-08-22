package com.capstone.feedback.Repository;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
    // Spring Data JPA automatically creates the query from the method name


    List<Issue> findByFacilityOrderByCreatedAtDesc(Facility facility);
}