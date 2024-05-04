package com.indianbank.indianbank.service.impl;

import com.indianbank.indianbank.controller.CustomerController;
import com.indianbank.indianbank.dto.AccountDto;
import com.indianbank.indianbank.entity.Response;
import com.indianbank.indianbank.entity.Transaction;
import com.indianbank.indianbank.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountsService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource parameters;


    //private CustomerController customerController;
    @Autowired
    private Response response;

    public AccountServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Response createAccount(AccountDto accountDto) {
        // Generate unique account number and branch ID
        Integer accountNo = generateAccountNumber();
        Integer branchId = generateBranchId();

        // Set generated values in the accountDto
        accountDto.setAccountNo(accountNo);
        accountDto.setBranchId(branchId);
        String sql = "INSERT INTO accounts (account_no, account_type, account_holder_name, account_balance, branch_id, phone_no, customer_address, user_name, email, password, created_on, created_by) " +
                "VALUES (:accountNo, :accountType, :accountHolderName, :accountBalance, :branchId, :phoneNo, :address, :userName, :email, :password, :createdOn, :createdBy)";

        parameters = new MapSqlParameterSource();
        parameters.addValue("accountNo", accountDto.getAccountNo());
        parameters.addValue("accountType", accountDto.getAccountType());
        parameters.addValue("accountHolderName", accountDto.getAccountHolderName());
        parameters.addValue("accountBalance", 500);
        parameters.addValue("branchId", accountDto.getBranchId());
        parameters.addValue("phoneNo", accountDto.getPhoneNo());
        parameters.addValue("address", accountDto.getAddress()); // Ensure 'address' is properly added
        parameters.addValue("userName", accountDto.getUserName());
        parameters.addValue("email", accountDto.getEmail());
        parameters.addValue("password", accountDto.getPassword());
        parameters.addValue("createdOn", accountDto.getCreatedOn());
        parameters.addValue("createdBy", accountDto.getCreatedBy());
        namedParameterJdbcTemplate.update(sql, parameters);
        response.setStatus(1);
        response.setError("Account created.");
        response.setPost(Collections.EMPTY_LIST);
        return response;
    }

    private Integer generateAccountNumber() {
        return (int) (Math.random() * 900000000) + 100000000;
    }

    private Integer generateBranchId() {
        return (int) (Math.random() * 900000000) + 100000000;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        String sql = "SELECT * FROM accounts";
        List<AccountDto> accounts = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountNo(rs.getInt("account_no"));
            accountDto.setAccountType(rs.getString("account_type"));
            accountDto.setAccountHolderName(rs.getString("account_holder_name"));
            accountDto.setAccountBalance(rs.getInt("account_balance"));
            accountDto.setBranchId(rs.getInt("branch_id"));
            accountDto.setPhoneNo(rs.getString("phone_no"));
            accountDto.setAddress(rs.getString("customer_address"));
            accountDto.setUserName(rs.getString("user_name"));
            accountDto.setEmail(rs.getString("email"));
            accountDto.setPassword(rs.getString("password"));
            accountDto.setActive(rs.getBoolean("is_active"));
            accountDto.setCreatedOn(rs.getDate("created_on"));
            accountDto.setCreatedBy(rs.getInt("created_by"));
            return accountDto;
        });
        return accounts;
    }

    @Override
    public AccountDto getAccountByAccountNo(Integer accountNo) {
        String sql = "SELECT * FROM accounts WHERE account_no = :accountNo";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountNo", accountNo);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountNo(rs.getInt("account_no"));
            accountDto.setAccountType(rs.getString("account_type"));
            accountDto.setAccountHolderName(rs.getString("account_holder_name"));
            accountDto.setAccountBalance(rs.getInt("account_balance"));
            accountDto.setBranchId(rs.getInt("branch_id"));
            accountDto.setPhoneNo(rs.getString("phoneNo"));
            accountDto.setUserName(rs.getString("user_name"));
            accountDto.setEmail(rs.getString("email"));
            accountDto.setPassword(rs.getString("password"));
            accountDto.setCreatedOn(rs.getDate("created_on"));
            accountDto.setCreatedBy(rs.getInt("created_by"));
            return accountDto;
        });
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        String sql = "UPDATE accounts SET account_type = :accountType, account_holder_name= :accountHolderName, account_balance = :accountBalance, " +
                "branch_id = :branchId, phone_no = :phoneNo, email=:email, updated_on = :updatedOn, updated_by = :updatedBy WHERE account_no = :accountNo";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountType", accountDto.getAccountType());
        parameters.put("accountHolderName", accountDto.getAccountHolderName());
        parameters.put("accountBalance", accountDto.getAccountBalance());
        parameters.put("branchId", accountDto.getBranchId());
        parameters.put("phoneNo", accountDto.getPhoneNo());
        parameters.put("email", accountDto.getEmail());
        parameters.put("updatedOn", accountDto.getUpdatedOn());
        parameters.put("updatedBy", accountDto.getUpdatedBy());
        parameters.put("accountNo", accountDto.getAccountNo());

        namedParameterJdbcTemplate.update(sql, parameters);

        return accountDto;
    }

    @Override
    public void deleteAccount(Integer accountNo) {
        String sql = "DELETE FROM accounts WHERE account_no = :accountNo";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountNo", accountNo);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public boolean depositAmount(Integer accountNo, Double amount) {
        String sql = "UPDATE accounts SET account_balance = account_balance + :amount WHERE account_no = :accountNo";
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("accountNo", accountNo);
        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);
        if (rowsUpdated > 0) {
            String logDepositSql = "INSERT INTO transactions (transaction_type, transaction_amount, account_no, is_active) " +
                    "VALUES (:transactionType, :transactionAmount, :accountNo, :active)";

            Map<String, Object> depositParams = new HashMap<>();
            depositParams.put("transactionType", "DEPOSIT");
            depositParams.put("transactionAmount", amount);
            depositParams.put("accountNo", accountNo);
            depositParams.put("active", true);

            int depositRowsUpdated = namedParameterJdbcTemplate.update(logDepositSql, depositParams);

            return depositRowsUpdated > 0;
        }

        return false;
    }

    @Override
    public boolean withdrawAmount(Integer accountNo, Double amount) {
        String sql = "UPDATE accounts SET account_balance = account_balance - :amount WHERE account_no = :accountNo AND account_balance >= :amount";
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("accountNo", accountNo);
        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);

        if (rowsUpdated > 0) {
            // Log withdrawal transaction
            String logWithdrawalSql = "INSERT INTO transactions (transaction_type, transaction_amount, account_no, is_active) " +
                    "VALUES (:transactionType, :transactionAmount, :accountNo, :active)";

            Map<String, Object> withdrawalParams = new HashMap<>();
            withdrawalParams.put("transactionType", "WITHDRAW");
            withdrawalParams.put("transactionAmount", -amount); // Negative amount for withdrawal
            withdrawalParams.put("accountNo", accountNo);
            withdrawalParams.put("active", true);

            int withdrawalRowsUpdated = namedParameterJdbcTemplate.update(logWithdrawalSql, withdrawalParams);

            return withdrawalRowsUpdated > 0;
        }
        return false;
    }
    @Override
    public Double getAccountBalance(Integer accountNo) {
        String sql = "SELECT account_balance FROM accounts WHERE account_no = :accountNo";
        MapSqlParameterSource parameters = new MapSqlParameterSource("accountNo", accountNo);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, Double.class);
        } catch (Exception e) {
            return null; // Account not found
        }
    }

}



