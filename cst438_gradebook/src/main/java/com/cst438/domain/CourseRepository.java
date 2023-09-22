package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository <Course, Integer> {
//    @Query("SELECT a FROM Course a WHERE a.course.instructor =:instructor")
//    List<Course> findByCourseTitleAndCourseID(
//            @Param("instructor") String title
//            
//    );

}
