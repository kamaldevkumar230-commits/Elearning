package com.elearning.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elearning.demo.model.Lecture;
import com.elearning.demo.service.LectureService;

@Controller
public class LectureController {

    @Autowired
    private LectureService lectureService;

    // 👉 Open upload page
    @GetMapping("/add-lecture/{courseId}")
    public String lecturePage(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "add_lecture";
    }

    // 👉 Save lecture
    @PostMapping("/add-lecture")
    public String saveLecture(@RequestParam String title,
                              @RequestParam Long courseId,
                              @RequestParam("videoFile") MultipartFile file,
                              RedirectAttributes redirectAttributes) {

        try {
            String uploadDir = "uploads/videos/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            Lecture lecture = new Lecture();
            lecture.setTitle(title);
            lecture.setCourseId(courseId);
            lecture.setVideoUrl(fileName);

            lectureService.saveLecture(lecture);

            redirectAttributes.addFlashAttribute("message", "Lecture Uploaded ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/add-lecture/" + courseId;
    }
    
    
    @GetMapping("/course/{courseId}")
    public String viewCourse(@PathVariable Long courseId, Model model) {

        List<Lecture> lectures = lectureService.getLecturesByCourse(courseId);

        model.addAttribute("lectures", lectures);
        model.addAttribute("courseId", courseId);

        return "course_details";
    }
    
    
    @GetMapping("/delete-lecture/{id}/{courseId}")
    public String deleteLecture(@PathVariable Long id,
                                @PathVariable Long courseId) {

        lectureService.deleteLecture(id);

        return "redirect:/course/" + courseId;
    }
    
}