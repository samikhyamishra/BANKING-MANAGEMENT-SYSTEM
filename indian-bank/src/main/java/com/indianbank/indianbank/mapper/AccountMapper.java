package com.indianbank.indianbank.mapper;

import com.indianbank.indianbank.dto.AccountDto;
import com.indianbank.indianbank.entity.Account;


public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNo(accountDto.getAccountNo());
        account.setAccountType(accountDto.getAccountType());
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setAccountBalance(accountDto.getAccountBalance());
        account.setBranchId(accountDto.getBranchId());
        account.setPhoneNo(accountDto.getPhoneNo());
        account.setAddress(accountDto.getAddress());
        account.setUserName(accountDto.getUserName());
        account.setEmail(accountDto.getEmail());
        account.setPassword(accountDto.getPassword());
        account.setActive(accountDto.getActive());
        account.setCreatedBy(accountDto.getCreatedBy());
        account.setCreatedOn(accountDto.getCreatedOn());
        account.setUpdatedBy(accountDto.getUpdatedBy());
        account.setUpdatedOn(accountDto.getUpdatedOn());
        return account;
    }

    //reverse mapping
    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNo(account.getAccountNo());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setAccountHolderName(account.getAccountHolderName());
        accountDto.setAccountBalance(account.getAccountBalance());
        accountDto.setBranchId(account.getBranchId());
        accountDto.setPhoneNo(account.getPhoneNo());
        accountDto.setAddress(account.getAddress());
        accountDto.setUserName(account.getUserName());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(account.getPassword());
        accountDto.setActive(account.getActive());
        accountDto.setCreatedBy(account.getCreatedBy());
        accountDto.setCreatedOn(account.getCreatedOn());
        accountDto.setUpdatedBy(account.getUpdatedBy());
        accountDto.setUpdatedOn(account.getUpdatedOn());
        return accountDto;
    }


}