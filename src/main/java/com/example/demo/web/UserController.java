package com.example.demo.web;

import com.example.demo.user.client.UserClient;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserClient userClient;

    @Autowired
    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/create")
    public ModelAndView showCreateUserPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-user");
        modelAndView.addObject("userCreateRequest", UserCreateRequest.builder().build());

        return modelAndView;
    }

    @PostMapping
    public String createUser(@ModelAttribute UserCreateRequest userCreateRequest, Model model) {

        ResponseEntity<UserResponse> response = userClient.createUser(userCreateRequest);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            model.addAttribute("userResponse", response.getBody());
            return "user-info";
        } else {
            model.addAttribute("errorMessage", "Error creating user. Please try again.");
            return "error";
        }
    }

    @GetMapping("/user-details")
    public String getUserById(@RequestParam("userId") UUID userId, Model model) {
        try {
            // Call the Feign Client method and get the ResponseEntity
            ResponseEntity<UserResponse> responseEntity = userClient.getUserById(userId);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Extract the UserResponse from the ResponseEntity
                UserResponse userResponse = responseEntity.getBody();

                // Add the user to the model to display in the next page
                model.addAttribute("user", userResponse);

                // Return the view to display user details
                return "user-details";  // This refers to the 'user-details.html' template
            } else {
                // Handle error, if status code is not 2xx
                model.addAttribute("error", "User not found or invalid ID.");
                return "error";
            }
        } catch (Exception e) {
            // Handle exception (e.g., network error, invalid UUID, etc.)
            model.addAttribute("error", "An error occurred while fetching the user details.");
            return "error";
        }
    }

    @GetMapping("/find")
    public String showFindUserForm() {
        return "find-user";
    }

}

