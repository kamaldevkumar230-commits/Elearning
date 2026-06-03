package com.elearning.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elearning.demo.model.Lecture;
import com.elearning.demo.repository.LectureRepository;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepo;

    public void saveLecture(Lecture lecture) {
        lectureRepo.save(lecture);
    }

    public List<Lecture> getLecturesByCourse(Long courseId) {
        return lectureRepo.findByCourseId(courseId);
    }
    
    public void deleteLecture(Long id) {
        lectureRepo.deleteById(id);
    }
    
}