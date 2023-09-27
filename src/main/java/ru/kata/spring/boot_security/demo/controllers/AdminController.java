package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showSingleUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id).get());
        return "/show";
    }

    @GetMapping("/new")
    public String showNewUserPage(Model model) {
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, String name, String surname, Short age) {
        userService.updateById(id, name, surname, age);
        return "/edit";
    }

    @PostMapping("edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, String name, String surname, Short age) {
        userService.updateById(id, name, surname, age);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}