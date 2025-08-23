package com.capstone.feedback.Repository;

import com.capstone.feedback.Model.Comment;
import com.capstone.feedback.Model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find all comments for an issue that are NOT replies (i.e., top-level comments)
    List<Comment> findByIssueAndParentCommentIsNullOrderByCreatedAtAsc(Issue issue);
}