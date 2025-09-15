package com.capstone.feedback.Controller;

import com.capstone.feedback.Model.User;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserRepository userRepository;

    // This method is fine as is, it needs the database call
    @ModelAttribute("userName")
    public String addUserNameToModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
            return (user != null) ? user.getFirstName() : null;
        }
        return null;
    }

    // ✅ This version is more efficient - NO DATABASE CALL
    @ModelAttribute("userRole")
    public String addUserRoleToModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Get role directly from the authorities list, assuming one role per user
            return userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse(null);
        }
        return null;
    }
}