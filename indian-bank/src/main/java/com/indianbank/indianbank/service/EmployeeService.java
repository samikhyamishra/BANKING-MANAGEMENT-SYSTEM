package com.indianbank.indianbank.service;

import com.indianbank.indianbank.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(Integer employeeId);
    EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto);
    void deleteEmployee(Integer employeeId);
     boolean isValidUser(Integer employeeId, String password);
     boolean logout(Integer employeeId);
     void registerEmp(String employeeName, String employeeAddress, String employeePhoneNumber, String employeeEmail, String password);
     String generateToken();
     Boolean saveToken(String employeeEmail, String token);
     Boolean sendPasswordResetEmail(String employeeEmail, String token);
    Map<String, Object> forgotPassword(String employeeEmail);
    Boolean resetPassword(String token, String newPassword);
     void deleteToken(String token);
    void lockEmployee(Integer employeeId);
}
