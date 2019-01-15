package com.example.quartzdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quartzdemo.dao.EmployeeDao;
import com.example.quartzdemo.model.Employee;

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