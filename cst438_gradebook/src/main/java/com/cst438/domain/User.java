package com.cst438.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user_table")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String alias; 	
	private String password;
	private String role;

	

	public User() {
		super();
		this.alias = "";
		this.password = "";
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getA() {
		return alias;
	}


	public void setA(String a) {
		this.alias = a;
	}
	
	public String getRoll() {
		return role;
	}


	public void setRoles(String a) {
		this.role = a;
	}



	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


   
}