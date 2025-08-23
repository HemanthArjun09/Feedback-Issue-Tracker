package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Comment;
import com.capstone.feedback.Model.Issue;
import com.capstone.feedback.Model.User;
import com.capstone.feedback.Repository.CommentRepository;
import com.capstone.feedback.Repository.IssueRepository;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class IssueDetailsController {

    @Autowired private IssueRepository issueRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;

    // Display the issue and its comments
    @GetMapping("/issue/{issueId}")
    public String showIssue(@PathVariable("issueId") int issueId, Model model) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue Id:" + issueId));

        List<Comment> comments = commentRepository.findByIssueAndParentCommentIsNullOrderByCreatedAtAsc(issue);

        model.addAttribute("issue", issue);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment()); // For the form
        return "issue-details";
    }

    // Handle new comment submissions
    @PostMapping("/issue/{issueId}/comments")
    public String postComment(@PathVariable("issueId") int issueId,
                              @ModelAttribute("newComment") Comment comment,
                              @AuthenticationPrincipal UserDetails userDetails) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue Id:" + issueId));
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        comment.setIssue(issue);
        comment.setUser(currentUser);
        comment.setCreatedAt(LocalDateTime.now());

        // Check if the user is an admin or facility admin to set the flag
        String role = currentUser.getRole();
        if ("ROLE_ADMIN".equals(role) || "FACILITY_USER".equals(role) || "ROLE_FACILITY_USER".equals(role)) {
            comment.setAdminComment(true);
        }

        commentRepository.save(comment);

        return "redirect:/issue/" + issueId;
    }
}