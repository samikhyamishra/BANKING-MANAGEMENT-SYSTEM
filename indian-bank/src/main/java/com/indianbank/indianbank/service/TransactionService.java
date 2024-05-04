package com.indianbank.indianbank.service;

import com.indianbank.indianbank.dto.AccountDto;
import com.indianbank.indianbank.dto.TransactionDto;

import java.util.List;

    public interface TransactionService {
        TransactionDto createTransaction(TransactionDto transactionDto);
        List<TransactionDto> getAllTransactions();
        TransactionDto getTransactionById(Integer transactionId);

        List<TransactionDto> getTransactionByAccountNo(Integer accountNo);
        TransactionDto updateTransaction( TransactionDto transactionDto);
        //void deleteTransaction(Long id);
    }
