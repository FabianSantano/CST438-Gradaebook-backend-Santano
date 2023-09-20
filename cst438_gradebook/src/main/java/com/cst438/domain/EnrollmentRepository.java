package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer> {
    @Query("SELECT e FROM Enrollment e")
    List<Enrollment> findAllEnrollments();
}
