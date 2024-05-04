package com.indianbank.indianbank.service;

import com.indianbank.indianbank.dto.AccountDto;
import com.indianbank.indianbank.entity.Response;

import java.util.List;

public interface AccountsService {
    Response createAccount(AccountDto accountDto);
    List<AccountDto> getAllAccounts();
    AccountDto getAccountByAccountNo(Integer accountNo);
    AccountDto updateAccount(AccountDto accountDto);
    void deleteAccount(Integer accountNo);
    boolean depositAmount(Integer accountNo, Double amount);
    boolean withdrawAmount(Integer accountNo, Double amount);
    Double getAccountBalance(Integer accountNo);
}
