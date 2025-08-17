package com.capstone.feedback.Controller;



import com.capstone.feedback.Model.User;
// import com.capstone.feedback.service.UserService; // You'll create this service
import com.capstone.feedback.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {



    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        // Create a new User object to hold the form data
        model.addAttribute("user", new User());
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
         userRepository.save(user);

        System.out.println("New user registered: " + user.getEmail());


        return "redirect:/login?NewUser=true";
    }
}
