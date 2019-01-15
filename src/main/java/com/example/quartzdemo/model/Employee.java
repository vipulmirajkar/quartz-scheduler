package com.example.quartzdemo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int e_id;
	String emailAddress;
	String dob;

	@Transient
	List<Employee> employeeList = new ArrayList<>();
	@Transient
	List<String> emailList = new ArrayList<>();

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	@Override
	public String toString() {
		return "Employee [emailAddress=" + emailAddress + ", dob=" + dob + ", employeeList=" + employeeList
				+ ", emailList=" + emailList + "]";
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
	/*
	 * Date d1 = new Date(); SimpleDateFormat formatter = new
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); String strDate=
	 * formatter.format(d1); System.out.println(strDate);
	 */
}