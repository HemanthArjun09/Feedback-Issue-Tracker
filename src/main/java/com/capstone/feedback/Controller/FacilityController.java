package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.Feedback;
import com.capstone.feedback.Model.Issue;
import com.capstone.feedback.Model.User;
import com.capstone.feedback.Model.enums.Facility_Types;
import com.capstone.feedback.Model.enums.Issue_Status;
import com.capstone.feedback.Repository.FacilityRepository;
import com.capstone.feedback.Repository.FeedbackRepository;
import com.capstone.feedback.Repository.IssueRepository;
import com.capstone.feedback.Repository.UserRepository;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FacilityController {

    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    // This method shows the form for adding a new facility
    @GetMapping("/add-facility")
    public String showAddFacilityForm(Model model) {

        // Create a new, empty facility object to bind to the form
        model.addAttribute("facility", new Facility());
        model.addAttribute("facilityTypes", Facility_Types.values());
        return "add-facility";
    }

    @PostMapping("/add-facility")
    public String addFacility(@ModelAttribute Facility facility, @AuthenticationPrincipal UserDetails userDetails) {
        // Save the new facility object (which is filled by the form) to the database
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        facility.setFacilityAdmin(currentUser);

        facilityRepository.save(facility);
        return "redirect:/dashboard";
    }

    @GetMapping("/facility/{id}")
    public String showFacilityDetails(@PathVariable("id") int facilityId, Model model) {
        // 1. Fetch the facility itself
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facility Id:" + facilityId));

        Feedback feedback = new Feedback();

        // 2. Fetch all issues for this facility
        List<Issue> issues = issueRepository.findByFacilityOrderByCreatedAtDesc(facility);

        // 3. Fetch all feedback for this facility
        List<Feedback> feedbackList = feedbackRepository.findByFacility(facility);

        // 4. Add all the data to the model
        model.addAttribute("facility", facility);
        model.addAttribute("issues", issues);
        model.addAttribute("feedbackList", feedbackList);
        model.addAttribute("newFeedback", feedback);
        List<Issue_Status> statuses = Arrays.stream(Issue_Status.values())
                .collect(Collectors.toList());
        model.addAttribute("issue_status", statuses);

        // 5. Return the new template
        return "facility-details";
    }

    @PostMapping("/facility/{id}/feedback")
    public String addFeedback(@PathVariable("id") int facilityId,
                              @ModelAttribute("newFeedback") Feedback feedback,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model, RedirectAttributes redirectAttributes) {

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facility Id:" + facilityId));
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set all the necessary context on the feedback object
        if (currentUser.getRole().equalsIgnoreCase("USER")) {
            if (!feedbackRepository.existsByUserAndFacility(currentUser, facility)) {

                feedback.setFacility(facility);
                feedback.setUser(currentUser);


                feedbackRepository.save(feedback);
                redirectAttributes.addFlashAttribute("message", "Feedback added successfully");
            }
            else{
                redirectAttributes.addFlashAttribute("error", "You have already reviewed this facility.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Facility Users cannot review this facility.");
            // Correctly redirect back to the specific facility's page

        }

        return "redirect:/facility/" + facilityId;
    }

}
