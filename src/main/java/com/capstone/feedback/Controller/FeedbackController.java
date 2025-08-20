package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Feedback;
import com.capstone.feedback.Repository.FeedbackRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedbackController {
    FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping("/addFeedback")
    public String addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);

        return "redirect:/facility-details";
    }
}
