package com.elearning.demo.service;
import java.util.List;

import com.elearning.demo.model.Course;
import com.elearning.demo.model.Enrollment;
import com.elearning.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elearning.demo.repository.CourseRepository;
import com.elearning.demo.repository.EnrollmentRepository;
import com.elearning.demo.repository.UserRepository;

@Service
public class EnrollmentService {

	@Autowired
	private EnrollmentRepository enrollRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CourseRepository courseRepo;

	public String enrollStudent(Long studentId, Long courseId) {

	    // already enrolled check
	    if (enrollRepo.existsByUserIdAndCourseId(studentId, courseId)) {
	        return "Already Enrolled";
	    }

	    User user = userRepo.findById(studentId).orElse(null);
	    Course course = courseRepo.findById(courseId).orElse(null);

	    if (user == null || course == null) {
	        return "Invalid Data";
	    }

	    Enrollment enrollment = new Enrollment();
	    enrollment.setUser(user);       // ✔ object set
	    enrollment.setCourse(course);   // ✔ object set

	    enrollRepo.save(enrollment);

	    return "Enrolled Successfully";
	}

  //  public List<Enrollment> getStudentCourses(Long studentId) {
       // return enrollRepo.findByStudentId(studentId);
  //  }
    public List<Enrollment> getByUser(User user) {
        return enrollRepo.findByUser(user);
    }
	
}