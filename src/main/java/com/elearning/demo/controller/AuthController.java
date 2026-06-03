package com.elearning.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.elearning.demo.model.Course;

import com.elearning.demo.model.User;
import com.elearning.demo.repository.UserRepository;
import com.elearning.demo.service.CourseService;

import com.elearning.demo.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {

    @Autowired
    private UserService service;

    // Home Page
    @GetMapping("/")
    public String home(Model model) {

        List<Course> courses = courseService.getAllCourses(); // 🔥 all courses

        model.addAttribute("courses", courses);

        return "index";
    }

    // Register Page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Save User
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        service.register(user);
        return "redirect:/register_success";
    }
    
    @GetMapping("/register_success")
    public String registerSuccess(Model model) {
    	return "register_success";
    }
    
    @GetMapping("/about")
    public String aboutPage(Model model) {
    	return "about";
    }
    
    @GetMapping("/contact")
    public String contactPage(Model model) {
    	return "contact";
    }

    // Login Page
    @GetMapping("/login")
    public String loginPage() {
        return "login";   // ✅ correct
    }

  
    // Dashboard Page (IMPORTANT 🔥)
    @GetMapping("/student_dashboard")
    public String studentDashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) return "redirect:/login";

        List<Course> courses = courseService.getAllCourses(); // 🔥 all courses

        System.out.println("Courses size: " + courses.size()); // 🔥 DEBUG

        model.addAttribute("courses", courses);

        return "student_dashboard";
    }
    

    @Autowired
    private CourseService courseService;

    @GetMapping("/teacher_dashboard")
    public String teacherDashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) return "redirect:/login";

        List<Course> courses = courseService.getTeacherCourses(user.getId());

        model.addAttribute("courses", courses);

        return "teacher_dashboard";
    }
    
    
    

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {  // ✅ add this

        User user = service.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid Email or Password");
            return "login";
        }
        
        if (!user.isApproved()) {
            model.addAttribute("error", "Your account is not approved yet. Please wait for admin approval.");
            return "login";
        }

        // 🔥 VERY IMPORTANT LINE
        session.setAttribute("loggedInUser", user);

        if ("TEACHER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/teacher_dashboard";
        } else {
            return "redirect:/student_dashboard";
        }
    }
    
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/approve-users")
    public String showUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "approve_users";
    }
    
    
    @PostMapping("/approve-user")
    public String approveUser(@RequestParam Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setApproved(true); // 🔥 main logic
            userRepository.save(user);
        }

        return "redirect:/approve-users";
    }
}