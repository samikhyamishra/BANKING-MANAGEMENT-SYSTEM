package com.indianbank.indianbank.controller;

import com.indianbank.indianbank.dto.BankDto;
import com.indianbank.indianbank.dto.EmployeeDto;
import com.indianbank.indianbank.service.BankService;
import com.indianbank.indianbank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest){
        Integer employeeId = Integer.valueOf(loginRequest.get("employeeId"));
        String password = loginRequest.get("password");
        Boolean i = employeeService.isValidUser(employeeId, password);
        if (i){
            return ResponseEntity.ok("Login successful!");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID or password");
        }
    }

    @DeleteMapping("/logout/{employeeId}")
    public ResponseEntity<String> logout(@PathVariable Integer employeeId) {
        try {
            boolean result = employeeService.logout(employeeId);
            if (result) {
                return ResponseEntity.ok("Logout Successful!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed. Error: " + e.getMessage());
        }
    }

    @PostMapping("/empRegister")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> UserRegistrationRequest) {
        String employeeName = UserRegistrationRequest.get("employeeName");
        String employeeAddress = UserRegistrationRequest.get("employeeAddress");
        String employeePhoneNumber = UserRegistrationRequest.get("employeePhoneNumber");
        String employeeEmail = UserRegistrationRequest.get("employeeEmail");
        String password = UserRegistrationRequest.get("password");
        employeeService.registerEmp(employeeName, employeeAddress, employeePhoneNumber, employeeEmail, password);
        return ResponseEntity.ok("Employee registered successfully!");
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestEmail) {
        String employeeEmail = requestEmail.get("employeeEmail");
        employeeService.forgotPassword(employeeEmail);
        return ResponseEntity.ok("Reset email sent successfully!");
}

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        employeeService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset done!");
    }
    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable Integer empId) {
        EmployeeDto employee = employeeService.getEmployeeById(empId);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Integer empId, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(empId, employeeDto);
        if (updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<Void> deleteBank(@PathVariable Integer empId) {
        employeeService.deleteEmployee(empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/lock/{employeeId}")
    public ResponseEntity<String> lockEmployee(@PathVariable("employeeId") Integer employeeId) {
        try {
            employeeService.lockEmployee(employeeId);
            return ResponseEntity.ok("Employee locked successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to lock employee.");
        }
    }

}
