package com.capstone.feedback.Repository;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {



    List<Issue> findByFacilityOrderByCreatedAtDesc(Facility facility);
}