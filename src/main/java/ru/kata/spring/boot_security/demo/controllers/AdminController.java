package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUser(Model model, Principal principal) {
        List<User> allUser = userService.findAll();
        model.addAttribute("users", allUser);
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("myUser", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("listRoles", roleService.findAll());
        return "admin-panel";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam("id") Long id, @RequestParam("name") String name,
                             @RequestParam("surname") String surname, @RequestParam("age") Short age, @RequestParam("username") String username,
                             @RequestParam("password") String password) {

        userService.updateById(id, name, surname, age, username, password);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}