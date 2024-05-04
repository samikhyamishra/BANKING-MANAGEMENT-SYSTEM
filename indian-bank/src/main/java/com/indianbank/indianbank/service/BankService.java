package com.indianbank.indianbank.service;

import com.indianbank.indianbank.dto.BankDto;

import java.util.List;

public interface BankService {
//    boolean isValidUser(Integer bankId, String password);
//    boolean logout(Integer bankId);
    BankDto createBank(BankDto bankDto);
    List<BankDto> getAllBanks();
    BankDto getBankById(Integer bankId);
    BankDto updateBank(Integer bankId, BankDto bankDto);
    void deleteBank(Integer bankId);
     boolean isValidUser(Integer bankId, String password);
     boolean logout(Integer bankId);
     String generateToken();
     Boolean saveToken(String bankEmail, String token);
     Boolean sendPasswordResetEmail(String bankEmail, String token);
     Boolean resetPassword(String token, String newPassword);
     void deleteToken(String token);
}
