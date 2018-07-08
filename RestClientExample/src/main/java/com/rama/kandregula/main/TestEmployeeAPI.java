package com.rama.kandregula.main;

import com.rama.kandregula.rs.client.EmployeeClient;
import com.rama.kandregula.rs.model.Employee;

public class TestEmployeeAPI {

	public static void main(String[] args) {
		EmployeeClient client = new EmployeeClient();
		
		Employee emp = client.findEmployee("15");
		System.out.println(emp.toString());
		
		
		emp.setFirstName("NewFirstName");
		emp.setLastName("NewLastName");
		emp.setWorkLocation("REMOTE");
		emp.setAge(19);
		
		Employee newEmp = client.addEmployee(emp);
		System.out.println(newEmp.getId());
		System.out.println(newEmp.toString());
		
	}

}
