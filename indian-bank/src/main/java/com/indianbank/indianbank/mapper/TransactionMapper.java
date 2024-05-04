package com.indianbank.indianbank.mapper;
import com.indianbank.indianbank.dto.TransactionDto;
import com.indianbank.indianbank.entity.Transaction;

public class TransactionMapper {
    //maptoaccount will map the transactiondto to the transaction entity
    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDto.getTransactionId());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTransactionAmount(transactionDto.getTransactionAmount());
        transaction.setAccountNo(transactionDto.getAccountNo());
        return transaction;
    }

    //reverse mapping
    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setTransactionAmount(transaction.getTransactionAmount());
        transactionDto.setAccountNo(transaction.getAccountNo());
        return transactionDto;
    }
}


