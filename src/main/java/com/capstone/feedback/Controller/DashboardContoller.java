package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.User;
import com.capstone.feedback.Repository.FacilityRepository;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class DashboardContoller {
    private final UserRepository userRepository;

    private final FacilityRepository facilityRepository;

    public DashboardContoller(UserRepository userRepository, FacilityRepository facilityRepository) {
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<Facility> facilities = facilityRepository.findAll();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("userName", user.getFirstName());
        model.addAttribute("facilities", facilities);
        return "dashboard";
    }
}
