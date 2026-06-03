package com.elearning.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elearning.demo.service.CourseService;
import com.elearning.demo.service.EnrollmentService;
import com.elearning.demo.model.Course;
import com.elearning.demo.model.Enrollment;
import com.elearning.demo.model.User;



import jakarta.servlet.http.HttpSession;
@Controller
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public String enroll(@RequestParam Long courseId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("loggedInUser");

        Long userId = user.getId();

        String message = enrollmentService.enrollStudent(userId, courseId);

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String paymentPage() {
        return "payment";
    }
    
    
    @PostMapping("/process-payment")
    public String processPayment(@RequestParam Long courseId,
                                 @RequestParam String paymentMethod,
                                 HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        System.out.println("Payment Method: " + paymentMethod);

        enrollmentService.enrollStudent(user.getId(), courseId);

        return "redirect:/success";
    }
    
    @GetMapping("/success")
    public String enrollSuccess() {
        return "successful_enrolled";
    }
    

    @GetMapping("/my-courses")
    public String myCourses(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser"); 

        List<Enrollment> enrollments = enrollmentService.getByUser(user);

        model.addAttribute("enrollments", enrollments);

        return "enrolled_courses";
    }
}