package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsWrapper;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsWrapper userDetails = (UserDetailsWrapper) authentication.getPrincipal();
        System.out.println(userDetails.getUser());

        model.addAttribute("user", userService.findById(id).get());

        return "/user";
    }
}