package com.elearning.demo.service;
import java.util.List;
import com.elearning.demo.model.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elearning.demo.repository.CourseRepository;
import com.elearning.demo.repository.EnrollmentRepository;



@Service
public class CourseService {

	
    @Autowired
    private CourseRepository courseRepo;
    
    
    @Autowired
    private EnrollmentRepository enrollRepo;

    public Course addCourse(Course course) {
        return courseRepo.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public List<Course> getTeacherCourses(Long teacherId) {
        return courseRepo.findByTeacherId(teacherId);
    }

    @Transactional
    public void deleteCourse(Long id) {

        enrollRepo.deleteByCourseId(id);

        courseRepo.deleteById(id);
    }

	public Course getCourseById(Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}
}