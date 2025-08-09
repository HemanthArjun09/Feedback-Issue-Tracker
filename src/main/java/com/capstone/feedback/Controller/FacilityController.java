package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.Facility;
import com.capstone.feedback.Model.enums.Facility_Types;
import com.capstone.feedback.Repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FacilityController {

    @Autowired
    private FacilityRepository facilityRepository;

    // This method shows the form for adding a new facility
    @GetMapping("/add-facility")
    public String showAddFacilityForm(Model model) {
        // Create a new, empty facility object to bind to the form
        model.addAttribute("facility", new Facility());
        return "add-facility";
    }

    @PostMapping("/add-facility")
    public String addFacility(@ModelAttribute Facility facility) {
        // Save the new facility object (which is filled by the form) to the database
        facilityRepository.save(facility);
        return "redirect:/dashboard"; // Redirect back to the dashboard to see the new entry
    }
}
