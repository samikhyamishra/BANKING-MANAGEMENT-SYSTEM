package com.indianbank.indianbank.mapper;

import com.indianbank.indianbank.dto.BankDto;
import com.indianbank.indianbank.entity.Bank;

public class BankMapper {
    public static BankDto toBankDto(Bank bank) {
        BankDto bankDto = new BankDto();
        bankDto.setBankId(bank.getBankId());
        bankDto.setBankName(bank.getBankName());
        bankDto.setBankAddress(bank.getBankAddress());
        bankDto.setBankEmail(bank.getBankEmail());
        bankDto.setActive(bank.getActive());
        bankDto.setToken(bank.getToken());
        bankDto.setPassword(bank.getPassword());
        bankDto.setCreatedOn(bank.getCreatedOn());
        bankDto.setCreatedBy(bank.getCreatedBy());
        bankDto.setUpdatedOn(bank.getUpdatedOn());
        bankDto.setUpdatedBy(bank.getUpdatedBy());
        return bankDto;
}

    public static Bank toBank(BankDto bankDto) {
        Bank bank = new Bank();
        bank.setBankId(bankDto.getBankId());
        bank.setBankName(bankDto.getBankName());
        bank.setBankAddress(bankDto.getBankAddress());
        bank.setBankEmail(bankDto.getBankEmail());
        bank.setActive(bankDto.getActive());
        bank.setToken(bankDto.getToken());
        bank.setPassword(bankDto.getPassword());
        bank.setCreatedOn(bankDto.getCreatedOn());
        bank.setCreatedBy(bankDto.getCreatedBy());
        bank.setUpdatedOn(bankDto.getUpdatedOn());
        bank.setUpdatedBy(bankDto.getUpdatedBy());
        return bank;
    }
}
