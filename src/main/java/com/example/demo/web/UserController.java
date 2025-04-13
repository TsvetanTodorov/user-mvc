package com.example.demo.web;

import com.example.demo.user.client.UserClient;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserEditRequest;
import com.example.demo.user.dto.UserResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserClient userClient;

    @Autowired
    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public ModelAndView showMainMenu() {

        List<UserResponse> users = userClient.getAllUsers().getBody();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users");

        return modelAndView;
    }

    @GetMapping("/create-user")
    public String showCreateUserForm(Model model) {

        model.addAttribute("userCreateRequest", UserCreateRequest.builder().build());

        return "create-user";
    }


    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable("id") UUID id, Model model) {

        UserResponse user = userClient.getUserById(id).getBody();
        model.addAttribute("user", user);

        return "edit-user";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        userClient.deleteUser(id);

        return "redirect:/users";
    }

    @GetMapping("/user-info/{id}")
    public String getUserInfo(@PathVariable("id") UUID id, Model model) {
        UserResponse user = userClient.getUserById(id).getBody();
        model.addAttribute("user", user);

        return "user-info";
    }

    @PostMapping
    public String createUser(@ModelAttribute UserCreateRequest userCreateRequest, RedirectAttributes redirectAttributes) {
        try {
            UserResponse createdUser = userClient.createUser(userCreateRequest).getBody();
            redirectAttributes.addFlashAttribute("user", createdUser);

            return "redirect:/users/user-info";

        } catch (FeignException.Conflict ex) {
            String errorMessage = "User with the given email or phone number already exists.";
            redirectAttributes.addFlashAttribute("error", errorMessage);

            return "redirect:/users/create-user";
        }
    }

    @GetMapping("/user-info")
    public String showUserDetails(Model model) {

        UserResponse user = (UserResponse) model.getAttribute("user");

        model.addAttribute("user", user);

        return "user-info";
    }


    @PatchMapping("/update-user")
    public String updateUser(@RequestBody UserEditRequest dto) {

        UUID userId = dto.getId();

        UserEditRequest request = UserEditRequest.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();

        userClient.updateUser(userId, request);

        return "redirect:/users";
    }


}

