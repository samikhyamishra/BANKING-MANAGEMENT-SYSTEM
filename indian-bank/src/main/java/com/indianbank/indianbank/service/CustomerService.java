package com.indianbank.indianbank.service;

import com.indianbank.indianbank.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    List<CustomerDto> getAllCustomers();
    CustomerDto getCustomerById(Integer customerId);
    CustomerDto updateCustomer(CustomerDto customerDto);
    void deleteCustomer(Integer customerId);
    Boolean isValidUser(String accountNo, String password);
    public boolean logout(String userName);
    public String generateToken();
    public Boolean saveToken(String customerEmail, String token);
    public Boolean sendPasswordResetEmail(String customerEmail, String token);
    public Boolean resetPassword(String token, String newPassword);
    public void deleteToken(String token);
    void registerUser(String customerName, String phoneNo, String address, String userName, String customerEmail, String password, Integer accountNo);
    void lockCustomerAndRelatedAccounts(Integer customerId,  String userName);
}
