package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.Issue;
import com.capstone.feedback.Model.User;
import com.capstone.feedback.Model.enums.Issue_Priority;
import com.capstone.feedback.Repository.FacilityRepository;
import com.capstone.feedback.Repository.IssueRepository;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private UserRepository userRepository;
    // The ImageService is no longer needed

    // Show the form to create a new issue (this method is unchanged)
    @GetMapping("/facility/{facilityId}/issues/new")
    public String showCreateIssueForm(@PathVariable("facilityId") int facilityId, Model model) {
        model.addAttribute("issue", new Issue());
        model.addAttribute("facilityId", facilityId);
        model.addAttribute("priorities", Issue_Priority.values());

        return "create-issue";
    }

    // Process the form submission (this method is now much simpler)
    @PostMapping("/facility/{facilityId}/issues")
    public String createIssue(@PathVariable("facilityId") int facilityId,
                              @ModelAttribute("issue") Issue issue,
                              @AuthenticationPrincipal UserDetails userDetails) {

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facility Id:" + facilityId));
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the relationships and other details
        issue.setFacility(facility);
        issue.setUser(currentUser);
        issue.setCreated_At(LocalDateTime.now());
        issue.setStatus("OPEN"); // Default status


        issueRepository.save(issue);

        return "redirect:/facility/" + facilityId;
    }
}