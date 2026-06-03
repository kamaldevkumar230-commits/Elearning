package com.elearning.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elearning.demo.model.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findByCourseId(Long courseId);
}