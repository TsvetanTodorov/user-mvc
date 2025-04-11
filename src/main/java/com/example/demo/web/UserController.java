package com.example.demo.web;

import com.example.demo.user.client.UserClient;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}

