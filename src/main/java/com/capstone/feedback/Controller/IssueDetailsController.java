package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Comment;
import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.Issue;
import com.capstone.feedback.Model.User;
import com.capstone.feedback.Model.enums.Issue_Status;
import com.capstone.feedback.Repository.CommentRepository;
import com.capstone.feedback.Repository.FacilityRepository;
import com.capstone.feedback.Repository.IssueRepository;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class IssueDetailsController {

    @Autowired private IssueRepository issueRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    // Display the issue and its comments
    @GetMapping("/issue/{issueId}")
    public String showIssue(@PathVariable("issueId") int issueId, Model model) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue Id:" + issueId));

        List<Comment> comments = commentRepository.findByIssueAndParentCommentIsNullOrderByCreatedAtDesc(issue);

        model.addAttribute("issue", issue);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment()); // For the form

        model.addAttribute("allStatuses", Issue_Status.values());

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

    // In IssueDetailsController.java
    @PostMapping("/issue/{issueId}/status")
    public String updateStatus(@PathVariable("issueId") int issueId,
                               @RequestParam("status") Issue_Status status,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {

        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue Id:" + issueId));

        Facility facility = facilityRepository.findById(issue.getFacility().getFacility_id())
                .orElseThrow(() -> new RuntimeException("Facility not found"));



        // Security Check
        String userRole = currentUser.getRole();
        boolean isAdmin = "ROLE_ADMIN".equals(userRole);

        boolean isAssignedFacilityAdmin = false;
        if(  facility.getFacilityAdmin()!= null){
            if(facility.getFacilityAdmin().getUserId().equals(currentUser.getUserId())){
                isAssignedFacilityAdmin = true;
            }
        }

        if (isAdmin || isAssignedFacilityAdmin) {
            Issue_Status oldStatus = issue.getStatus();
            if (oldStatus != status) {
                issue.setStatus(status);
                issueRepository.save(issue);

                // Auto-generate a comment for the status change
                Comment autoComment = new Comment();
                autoComment.setIssue(issue);
                autoComment.setUser(currentUser);
                autoComment.setCreatedAt(LocalDateTime.now());
                autoComment.setAdminComment(true);
                autoComment.setContent("Administrator updated status from '" + oldStatus.name() + "' to '" + status.name() + "'.");
                commentRepository.save(autoComment);

                redirectAttributes.addFlashAttribute("message", "Issue status updated successfully!");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to modify this issue.");
        }
        return "redirect:/issue/" + issueId;
    }

}