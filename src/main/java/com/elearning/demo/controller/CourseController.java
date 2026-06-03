package com.elearning.demo.controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elearning.demo.model.User;
import com.elearning.demo.model.Course;
import com.elearning.demo.service.CourseService;


import jakarta.servlet.http.HttpSession;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Add Course Page
    @GetMapping("/add-course")
    public String addCoursePage(Model model) {
        model.addAttribute("course", new Course());
        return "add_course_form";
    }
    
    
   
    

    // Save Course
    @PostMapping("/add-course")
    public String saveCourse(@ModelAttribute Course course,
                             @RequestParam("imageFile") MultipartFile file,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null) return "redirect:/login";

        try {
            course.setTeacherId(user.getId());

            String uploadDir = "uploads/images/";
            String fileName = file.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            course.setImageUrl(fileName);

            courseService.addCourse(course);

            redirectAttributes.addFlashAttribute("message", "Course Added");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/teacher_dashboard";
    }

    // Show all courses (student view)
    @GetMapping("/courses")
    public String allCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }
    
    @PostMapping("/delete-course")
    public String deleteCourse(@RequestParam Long courseId) {

        courseService.deleteCourse(courseId);

        return "redirect:/teacher_dashboard";
    }
    
    
}