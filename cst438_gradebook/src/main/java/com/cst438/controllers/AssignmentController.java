package com.cst438.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.GradeDTO;


@RestController
@CrossOrigin 
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	AssignmentGradeRepository assignmentGradeRepository;
	
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	EnrollmentRepository enrollmentRepository;

	@GetMapping("/gradebook")
	public AssignmentDTO[] getAllNonGradedAssignmentsForInstructor() {
	    String instructorEmail = "dwisneski@csumb.edu";  // instructor's email

	    // Get all assignments for this instructor
	    List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);

	    // Get all enrollments for this instructor
	    List<Enrollment> enrollments = enrollmentRepository.findAllEnrollments();

	    AssignmentDTO[] result = new AssignmentDTO[assignments.size() * enrollments.size()];
	    int index = 0;

	    for (Assignment assignment : assignments) {
	        for (Enrollment enrollment : enrollments) {
	            // Check if there is an assignment grade for this enrollment and assignment
	            List<AssignmentGrade> assignmentGrades = assignmentGradeRepository
	                    .findByAssignmentIdAndStudentID(assignment.getId(), enrollment.getId());

	            // If there are no assignment grades, consider it a non-graded assignment
	            if (assignmentGrades.isEmpty()) {
	                AssignmentDTO dto = new AssignmentDTO(
	                    assignment.getId(),
	                    assignment.getName(),
	                    assignment.getDueDate().toString(),
	                    assignment.getCourse().getTitle(),
	                    assignment.getCourse().getCourse_id()
	                );
	                result[index++] = dto;
	            }
	        }
	    }

	    // Resize the result array to the actual number of missing assignments
	    return result;
	}
	@GetMapping("/assignment")
	public AssignmentDTO[] getAllAssignmentsForInstructor() {
		// get all assignments for this instructor
		String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email) 
		List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
		AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
		for (int i=0; i<assignments.size(); i++) {
			Assignment as = assignments.get(i);
			AssignmentDTO dto = new AssignmentDTO(
					as.getId(), 
					as.getName(), 
					as.getDueDate().toString(), 
					as.getCourse().getTitle(), 
					as.getCourse().getCourse_id());
			result[i]=dto;
		}
		return result;
	}

	

@PostMapping("/createAssignment")
public AssignmentDTO[] createAssignment(@RequestBody Assignment assignment) {
    String instructorEmail = "dwisneski@csumb.edu";  // instructor's email
    // Get all assignments for this instructor
    List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
    AssignmentDTO[] result = new AssignmentDTO[assignments.size() + 1];

    int index = 0;

    for (Assignment assignmentss : assignments) {
        AssignmentDTO dto = new AssignmentDTO(
                assignmentss.getId(),
                assignmentss.getName(),
                assignmentss.getDueDate().toString(),
                assignmentss.getCourse().getTitle(),
                assignmentss.getCourse().getCourse_id()
        );
        result[index++] = dto;
    }

    // Save the new assignment first to get the auto-generated ID
    if (assignment.getCourse() != null) {
    Assignment savedAssignment = assignmentRepository.save(assignment);

    AssignmentDTO dto = new AssignmentDTO(
            savedAssignment.getId(),
            savedAssignment.getName(),
            savedAssignment.getDueDate().toString(),
            savedAssignment.getCourse().getTitle(),
            savedAssignment.getCourse().getCourse_id()
    );
    result[index] = dto;
    }else {
        // Handle the case where the new assignment has no Course assigned
        result[index] = new AssignmentDTO(-1, "Invalid Assignment", "", "No Course Assigned", -1);
    }

    return result;
}
@DeleteMapping("/deleteAssignment/{assignmentID}")
public AssignmentDTO[] deleteAssignment(@PathVariable("assignmentID") int id) {
    String instructorEmail = "dwisneski@csumb.edu";  // instructor's email
    // Get all assignments for this instructor
    List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);

    // Find and remove the assignment from the list
    Assignment assignmentToRemove = null;
    for (Assignment a : assignments) {
        if (a.getId() == id) {
            assignmentToRemove = a;
            break;
        }
    }

    if (assignmentToRemove != null) {
        assignments.remove(assignmentToRemove);

        // Delete the assignment from the database
        assignmentRepository.deleteById(assignmentToRemove.getId());
    }

    // Convert the remaining assignments to DTOs
    AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
    int index = 0;
    for (Assignment assignmentss : assignments) {
        AssignmentDTO dto = new AssignmentDTO(
                assignmentss.getId(),
                assignmentss.getName(),
                assignmentss.getDueDate().toString(),
                assignmentss.getCourse().getTitle(),
                assignmentss.getCourse().getCourse_id()
        );
        result[index++] = dto;
    }

    return result;
}
@PutMapping("/updateAssignment")
public AssignmentDTO[] updateAssignment(@RequestBody Assignment updatedAssignment) {
    String instructorEmail = "dwisneski@csumb.edu";  // instructor's email
    // Get all assignments for this instructor
    List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);

    // Find and update the assignment
    for (int i = 0; i < assignments.size(); i++) {
        Assignment assignment = assignments.get(i);
        if (assignment.getId() == updatedAssignment.getId()) {
            // Update the assignment's properties with the new values
            assignment.setName(updatedAssignment.getName());
            assignment.setDueDate(updatedAssignment.getDueDate());
            // You can also update other properties as needed

            // Save the updated assignment
            assignmentRepository.save(assignment);
        }
    }

    // Create an AssignmentDTO array to hold the updated assignments
    AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
    for (int i = 0; i < assignments.size(); i++) {
        Assignment as = assignments.get(i);
        AssignmentDTO dto = new AssignmentDTO(
                as.getId(),
                as.getName(),
                as.getDueDate().toString(),
                as.getCourse().getTitle(),
                as.getCourse().getCourse_id()
        );
        result[i] = dto;
    }

    return result;
}

@GetMapping("/assignment/{assignmentID}")
public AssignmentDTO[] getAssignmentById(@PathVariable("assignmentID") int id) {
    String instructorEmail = "dwisneski@csumb.edu";  // instructor's email
    // Get all assignments for this instructor
    List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
    List<Assignment> assignments2 = new ArrayList();
    // Find and remove the assignment from the list
    
    for (Assignment a : assignments) {
        if (a.getId() == id) {
            assignments2.add(a) ;
           
        }
    }



    // Convert the remaining assignments to DTOs
    AssignmentDTO[] result = new AssignmentDTO[assignments2.size()];
    int index = 0;
    for (Assignment assignmentss : assignments2) {
        AssignmentDTO dto = new AssignmentDTO(
                assignmentss.getId(),
                assignmentss.getName(),
                assignmentss.getDueDate().toString(),
                assignmentss.getCourse().getTitle(),
                assignmentss.getCourse().getCourse_id()
        );
        result[index++] = dto;
    }

    return result;
}

////	// TODO create CRUD methods for Assignment
	
}

