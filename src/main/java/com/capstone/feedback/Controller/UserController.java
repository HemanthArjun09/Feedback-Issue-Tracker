package com.capstone.feedback.Controller;



import com.capstone.feedback.Model.User;
// import com.capstone.feedback.service.UserService; // You'll create this service
import com.capstone.feedback.Model.enums.User_Types;
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {



    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        // Add the empty User object for the form
        model.addAttribute("user", new User());

        // ✅ Add the list of roles to the model
        // We get all values from the User_Types enum and filter out ADMIN
        List<User_Types> userRoles = Arrays.stream(User_Types.values())
                .filter(role -> role != User_Types.ADMIN)
                .collect(Collectors.toList());
        model.addAttribute("roles", userRoles);

        return "signup"; // Returns the signup.html template
    }

    /**
     * Processes the user registration form submission.
     */
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

         String encodedPassword = encoder.encode(user.getPassword());
         user.setPassword(encodedPassword);
        // ===================================================================

        // Here, you would call your service to save the user.
        // The service would also check if the email is already taken.
//        user.setRole("USER");
         userRepository.save(user);
        System.out.println("New user registered: " + user.getEmail());


        return "redirect:/login?NewUser=true";
    }

    @GetMapping("/account-details")
    public String showAccountDetails(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Find the currently logged-in user from the database
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Add the user object to the model
        model.addAttribute("user", currentUser);

        // Return the name of our new HTML template
        return "account-details";
    }
}
