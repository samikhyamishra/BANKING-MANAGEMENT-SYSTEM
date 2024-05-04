package com.indianbank.indianbank.controller;

import com.indianbank.indianbank.dto.CustomerDto;
import com.indianbank.indianbank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/allCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Integer customerId) {
        CustomerDto customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto updatedCustomer = customerService.updateCustomer(customerDto);
        if (updatedCustomer != null) {
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest){
        String userName = loginRequest.get("userName");
        String password = loginRequest.get("password");
        Boolean i = customerService.isValidUser(userName,password);
        if (i){
            return ResponseEntity.ok("Login successful!");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> logoutRequest) {
        String userName = logoutRequest.get("userName");
        try {
            boolean result = customerService.logout(userName);
            if (result) {
                return ResponseEntity.ok("Logout Successful!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed. Error: " + e.getMessage());
        }
    }
    @PostMapping ("/userRegister")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> UserRegistrationRequest){
        String customerName = UserRegistrationRequest.get("customerName");
        String phoneNo = UserRegistrationRequest.get("phoneNo");
        String address = UserRegistrationRequest.get("address");
        String userName = UserRegistrationRequest.get("userName");
        String email = UserRegistrationRequest.get("email");
        String password = UserRegistrationRequest.get("password");
        Integer accountNo = Integer.valueOf(UserRegistrationRequest.get("accountNo"));
        try{
            customerService.registerUser( customerName, phoneNo, address, userName, email, password, accountNo);
            return ResponseEntity.ok("User registered successfully!");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed. Error: "+e.getMessage());
        }
    }
    @PostMapping("/ForgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestEmail) {
        String email = requestEmail.get("email");
        if (email!=null && !email.isEmpty()){
            String token = customerService.generateToken();
            boolean saved = customerService.saveToken(email, token);
            if (saved) {
                boolean emailSent = customerService.sendPasswordResetEmail(email, token);
                if (emailSent) {
                    return ResponseEntity.ok("Password reset email sent successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send password reset email.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save reset token.");
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email parameter is required.");
        }

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean reset = customerService.resetPassword(token, newPassword);
        if (reset) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password.");
        }
    }
    @PutMapping("/lock/{customerId}/{userName}")
    public ResponseEntity<String> lockCustomerAndRelatedAccounts(@PathVariable Integer customerId, @PathVariable String userName) {
        try {
            customerService.lockCustomerAndRelatedAccounts(customerId,userName);
            return ResponseEntity.ok("Customer and related accounts locked successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to lock customer and related accounts: " + e.getMessage());
        }
    }

}
