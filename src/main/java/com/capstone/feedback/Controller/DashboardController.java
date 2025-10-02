package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Repository.FacilityRepository;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class DashboardController {
    private final FacilityRepository facilityRepository;

    public DashboardController(UserRepository userRepository, FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
        
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Facility> facilities = facilityRepository.findAll();
        model.addAttribute("facilities", facilities);
        return "dashboard";
    }
}
