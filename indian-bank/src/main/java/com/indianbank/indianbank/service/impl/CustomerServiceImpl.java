package com.indianbank.indianbank.service.impl;

import com.indianbank.indianbank.dto.CustomerDto;
import com.indianbank.indianbank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private JavaMailSender javaMailSender;

    @Autowired
    public CustomerServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JavaMailSender javaMailSender) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.javaMailSender=javaMailSender;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        String sql = "INSERT INTO customers(customer_name, customer_phone_no, customer_address, user_name, email, password, account_no) " +
                "VALUES (:customerName, :customerPhoneNo, :address, :userName, :email, :password, :accountNo)";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerName", customerDto.getCustomerName());
        parameters.put("phoneNo", customerDto.getPhoneNo());
        parameters.put("address", customerDto.getAddress());
        parameters.put("userName",customerDto.getUserName());
        parameters.put("email",customerDto.getEmail());
        parameters.put("password",customerDto.getPassword());
        parameters.put("accountNo",customerDto.getAccountNo());
        namedParameterJdbcTemplate.update(sql, parameters);

        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        List<CustomerDto> customers = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(rs.getInt("customer_id"));
            customerDto.setCustomerName(rs.getString("customer_name"));
            customerDto.setPhoneNo(rs.getString("customer_phone_no"));
            customerDto.setAddress(rs.getString("customer_address"));
            customerDto.setUserName(rs.getString("user_name"));
            customerDto.setEmail(rs.getString("email"));
            customerDto.setPassword(rs.getString("password"));
            customerDto.setAccountNo(rs.getInt("account_no"));
            return customerDto;
        });
        return customers;
    }

    @Override
    public CustomerDto getCustomerById(Integer customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = :customerId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(rs.getInt("customer_id"));
            customerDto.setCustomerName(rs.getString("customer_name"));
            customerDto.setPhoneNo(rs.getString("customer_phone_no"));
            customerDto.setAddress(rs.getString("customer_address"));
            customerDto.setUserName(rs.getString("user_name"));
            customerDto.setEmail(rs.getString("email"));
            customerDto.setPassword(rs.getString("password"));
            customerDto.setAccountNo(rs.getInt("account_no"));
            return customerDto;
        });
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        String sql = "UPDATE customers SET customer_name = :customerName, " +
                "customer_phone_no = :phoneNo, customer_address = :address, " +
                "user_name = :userName, email = :email, password = :password, account_no = :accountNo "+
                "WHERE customer_id = :customerId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerName", customerDto.getCustomerName());
        parameters.put("phoneNo", customerDto.getPhoneNo());
        parameters.put("address", customerDto.getAddress());
        parameters.put("userName", customerDto.getUserName());
        parameters.put("email", customerDto.getEmail());
        parameters.put("password", customerDto.getPassword());
        parameters.put("accountNo", customerDto.getAccountNo());
        parameters.put("customerId", customerDto.getCustomerId());
        namedParameterJdbcTemplate.update(sql, parameters);

        return customerDto;
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        String sql = "DELETE FROM customer WHERE customer_id = :customerId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        namedParameterJdbcTemplate.update(sql, parameters);
    }
    @Override
    public Boolean isValidUser(String userName, String password){

        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName",userName);
        mapSqlParameterSource.addValue("password",password);
        String sql = "SELECT COUNT(*) FROM accounts WHERE user_name = :userName AND password = :password";
        Integer loginUser = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource,Integer.class);
        if(loginUser>0){
            String updateSql = "UPDATE customers SET is_active = true WHERE user_name= :userName";
            int rowsUpdated = namedParameterJdbcTemplate.update(updateSql,mapSqlParameterSource);
            return rowsUpdated>0;
        }
        else {
            return false;
        }
    }
    public boolean logout(String userName){
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userName", userName);
            String sql = "UPDATE customers SET is_active = false WHERE user_name = :userName";
            Integer rowsUpdated = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

//           check if the update operation was successful
            if(rowsUpdated>0){
                System.out.println("Logout Successful!");
                return true;
            }
            else {
                System.out.println("User not found or already logged out");
                return false;
            }
        }
        catch(Exception e){
            System.err.println("Logout Failed! Error: " +e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void registerUser(String customerName, String phoneNo, String userName, String email, String password, String address, Integer accountNo){
        String sql = "INSERT INTO customers(customer_name, customer_phone_no,customer_address, user_name, email, password, account_no) " +
                "VALUES (:customerName, :phoneNo, :address, :userName, :email, :password, :accountNo)";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("customerName", customerName);
        mapSqlParameterSource.addValue("phoneNo", phoneNo);
        mapSqlParameterSource.addValue("address", address);
        mapSqlParameterSource.addValue("userName", userName);
        mapSqlParameterSource.addValue("email", email);
        mapSqlParameterSource.addValue("password",password);
        mapSqlParameterSource.addValue("accountNo", accountNo);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public void lockCustomerAndRelatedAccounts(Integer customerId, String userName) {
        String lockCustomerSql = "UPDATE customers SET is_active = false WHERE customer_id = :customerId";
        MapSqlParameterSource customerParams = new MapSqlParameterSource();
        customerParams.addValue("customerId", customerId);
        namedParameterJdbcTemplate.update(lockCustomerSql, customerParams);

        // Lock related accounts
        String lockAccountSql = "UPDATE accounts SET is_active = false WHERE user_name = :userName";
        MapSqlParameterSource accountParams = new MapSqlParameterSource();
        accountParams.addValue("userName", userName);
        namedParameterJdbcTemplate.update(lockAccountSql, accountParams);

        // Lock related branch
        String lockBranchSql = "UPDATE branch SET is_active = false WHERE customer_id = :customerId";
        MapSqlParameterSource branchParams = new MapSqlParameterSource();
        customerParams.addValue("customerId", customerId);
        namedParameterJdbcTemplate.update(lockAccountSql, accountParams);
    }

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public Boolean saveToken(String email, String token){
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("email", email);
            mapSqlParameterSource.addValue("token", token);
            String sql = "INSERT INTO customers (email, token) VALUES (:email, :token)";
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean sendPasswordResetEmail(String email, String token){
//        creating email message
        try{
            SimpleMailMessage message= new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Dear User, \n\n As per your request to reset the password, click the following link to reset your password:\n\n" +
                    "http://yourdomain.com/reset-password?token=" + token);
            javaMailSender.send(message);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public Boolean resetPassword(String token, String newPassword){
        try{
            String sql= "UPDATE customers SET password = :newPassword WHERE  token = :token ";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("token", token);
            mapSqlParameterSource.addValue("newPassword", newPassword);
            int rowsUpdated = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            // Delete the token from the database after successful password reset
            deleteToken(token);
            return rowsUpdated > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteToken(String token) {
        // Delete the token from the database after successful password reset
        String sql = "DELETE FROM customers WHERE token = :token";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("token", token);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }
}
