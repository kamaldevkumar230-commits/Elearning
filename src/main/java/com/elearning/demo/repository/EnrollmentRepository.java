package com.elearning.demo.repository;
import com.elearning.demo.model.Enrollment;
import com.elearning.demo.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // ✅ Get enrollments by user
    List<Enrollment> findByUser(User user);

    // ✅ Check already enrolled
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    // ✅ Delete enrollments by course
    @Modifying
    @Transactional
    @Query("DELETE FROM Enrollment e WHERE e.course.id = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
