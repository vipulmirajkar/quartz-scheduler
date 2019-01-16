package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeDao employeeDao;

	public Employee getRoleById(Long id) {
		return employeeDao.getEmail();
	}

	@Override
	public List<Employee> getEmailAddress() {
		return employeeDao.getEmailList();
	}

	@Override
	public Employee getEmployeeEmail() {
		return employeeDao.getEmail();
	}
}