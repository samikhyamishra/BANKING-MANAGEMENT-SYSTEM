package com.indianbank.indianbank.mapper;

import com.indianbank.indianbank.dto.EmployeeDto;
import com.indianbank.indianbank.entity.Employee;

public class EmployeeMapper {
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setEmployeeName(employeeDto.getEmployeeName());
        employee.setEmployeeAddress(employeeDto.getEmployeeAddress());
        employee.setEmployeePhoneNumber(employeeDto.getEmployeePhoneNumber());
        employee.setEmployeeEmail(employeeDto.getEmployeeEmail());
        employee.setActive(employeeDto.getActive());
        employee.setToken(employeeDto.getToken());
        employee.setPassword(employeeDto.getPassword());
        employee.setCreatedOn(employeeDto.getCreatedOn());
        employee.setCreatedBy(employeeDto.getCreatedBy());
        employee.setUpdatedOn(employeeDto.getUpdatedOn());
        employee.setUpdatedBy(employeeDto.getUpdatedBy());
        return employee;
    }

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setEmployeeName(employee.getEmployeeName());
        employeeDto.setEmployeeAddress(employee.getEmployeeAddress());
        employeeDto.setEmployeePhoneNumber(employee.getEmployeePhoneNumber());
        employeeDto.setEmployeeEmail(employee.getEmployeeEmail());
        employeeDto.setActive(employee.getActive());
        employeeDto.setToken(employee.getToken());
        employeeDto.setPassword(employee.getPassword());
        employeeDto.setCreatedOn(employee.getCreatedOn());
        employeeDto.setCreatedBy(employee.getCreatedBy());
        employeeDto.setUpdatedOn(employee.getUpdatedOn());
        employeeDto.setUpdatedBy(employee.getUpdatedBy());
        return employeeDto;
    }
}
