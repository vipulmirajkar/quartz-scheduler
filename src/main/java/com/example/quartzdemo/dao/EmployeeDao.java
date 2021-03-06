package com.example.quartzdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.quartzdemo.model.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	@Query("Select emailAddress from Employee e where e.dob = sysdate()")
	List<Employee> getEmailList();
	
	@Query("Select emailAddress from Employee e where e.dob = sysdate()")
	Employee getEmail();
}