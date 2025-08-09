package com.capstone.feedback.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardContoller {

    @GetMapping("/dashboard")
    public String showDashboard() {
        // This tells Spring to look for a file named "dashboard.html"
        // in the 'src/main/resources/templates' folder.
        return "dashboard";
    }
}
