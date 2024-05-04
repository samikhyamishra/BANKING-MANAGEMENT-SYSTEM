package com.indianbank.indianbank.service.impl;

import com.indianbank.indianbank.dto.TransactionDto;
import com.indianbank.indianbank.repository.TransactionRepository;
import com.indianbank.indianbank.service.TransactionService;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, TransactionRepository transactionRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        String sql = "INSERT INTO transactions (transaction_type, transaction_amount, account_no) " +
                "VALUES (:transactionType, :transactionAmount, :accountNo)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transactionType", transactionDto.getTransactionType());
        parameters.put("transactionAmount", transactionDto.getTransactionAmount());
        parameters.put("accountNo", transactionDto.getAccountNo());

        namedParameterJdbcTemplate.update(sql, parameters);

        return transactionDto;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        String sql="SELECT * FROM transactions";
        List<TransactionDto> transactions = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionId(rs.getInt("transaction_id"));
            transactionDto.setTransactionType(rs.getString("transaction_type"));
            transactionDto.setTransactionAmount(rs.getDouble("transaction_amount"));
            transactionDto.setAccountNo(rs.getInt("account_no"));
            return transactionDto;
        });
        return transactions;
    }

    @Override
    public TransactionDto getTransactionById(Integer transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = :transactionId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transactionId", transactionId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionId(rs.getInt("transaction_id"));
            transactionDto.setTransactionType(rs.getString("transaction_type"));
            transactionDto.setTransactionAmount(rs.getDouble("transaction_amount"));
            transactionDto.setAccountNo(rs.getInt("account_no"));
            return transactionDto;
        });
    }

    @Override
    public List<TransactionDto> getTransactionByAccountNo(Integer accountNo) {
        String sql = "SELECT * FROM transactions WHERE account_no = :accountNo";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountNo", accountNo);
        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionId(rs.getInt("transaction_id"));
            transactionDto.setTransactionType(rs.getString("transaction_type"));
            transactionDto.setTransactionAmount(rs.getDouble("transaction_amount"));
            transactionDto.setAccountNo(rs.getInt("account_no"));
            return transactionDto;
        });
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto transactionDto) {
        String sql = "UPDATE transactions SET transaction_type = :transactionType, transaction_amount = :transactionAmount WHERE account_no = :accountNo";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transactionType", transactionDto.getTransactionType());
        parameters.put("transactionAmount", transactionDto.getTransactionAmount());
       // parameters.put("transactionDate", transactionDto.getTransactionDate());
        parameters.put("accountNo", transactionDto.getAccountNo());

        namedParameterJdbcTemplate.update(sql, parameters);

        return transactionDto;
    }
}
