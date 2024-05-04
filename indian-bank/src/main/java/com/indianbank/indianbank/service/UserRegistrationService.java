package com.indianbank.indianbank.service;

import org.springframework.stereotype.Service;

public interface UserRegistrationService {
    void registerUser(String customerName, String phoneNo, String address, String userName, String email, String password, Integer accountNo);
}
