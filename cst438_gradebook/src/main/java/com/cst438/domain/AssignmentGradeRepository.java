package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentGradeRepository extends CrudRepository <AssignmentGrade, Integer> {
	
	@Query("select a from AssignmentGrade a where a.assignment.id=:assignmentId and a.studentEnrollment.studentEmail=:email")
	AssignmentGrade findByAssignmentIdAndStudentEmail(
			@Param("assignmentId") int assignmentId, 
			@Param("email") String email );

	    @Query("SELECT e FROM AssignmentGrade e")
	    List<AssignmentGrade> findAllAssignmentGrade();
	    
	    @Query("SELECT a FROM AssignmentGrade a WHERE a.assignment.id = :assignmentId AND a.studentEnrollment.id = :studentId")
	    List<AssignmentGrade> findByAssignmentIdAndStudentID(
	            @Param("assignmentId") int assignmentId,
	            @Param("studentId") int student
	    );


	
	
	
}
