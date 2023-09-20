package com.cst438.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {


	@Query("select a from Assignment a where a.course.instructor= :email order by a.id")
	List<Assignment> findByEmail(@Param("email") String email);
	@Query("SELECT e FROM Assignment e")
	List<Assignment> findAll();
}
