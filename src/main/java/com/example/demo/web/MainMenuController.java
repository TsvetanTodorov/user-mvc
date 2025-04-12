package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main-menu")
public class MainMenuController {

    @GetMapping
    public ModelAndView showMainMenu() {

        return new ModelAndView("main-menu");
    }
}