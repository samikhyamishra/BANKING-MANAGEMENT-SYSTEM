package com.indianbank.indianbank.service.impl;

import com.indianbank.indianbank.dto.BankDto;
import com.indianbank.indianbank.repository.BankRepository;
import com.indianbank.indianbank.service.BankService;
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
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private  MapSqlParameterSource mapSqlParameterSource;

    private JavaMailSender javaMailSender;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.bankRepository = bankRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;}


    @Override
    public BankDto createBank(BankDto bankDto) {
        String sql = "INSERT INTO bank (bank_name, bank_address, created_on, created_by) " +
                "VALUES (:bankName, :bankAddress, :createdOn, :createdBy)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bankName", bankDto.getBankName());
        parameters.put("bankAddress", bankDto.getBankAddress());
        parameters.put("createdOn", bankDto.getCreatedOn());
        parameters.put("createdBy", bankDto.getCreatedBy());

        namedParameterJdbcTemplate.update(sql, parameters);

        return bankDto;
    }

    @Override
    public List<BankDto> getAllBanks() {
        String sql = "SELECT * FROM bank";
        List<BankDto> banks = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            BankDto bankDto = new BankDto();
            bankDto.setBankId(rs.getInt("bank_id"));
            bankDto.setBankName(rs.getString("bank_name"));
            bankDto.setBankAddress(rs.getString("bank_address"));
            bankDto.setCreatedOn(rs.getDate("created_on"));
            bankDto.setCreatedBy(rs.getInt("created_by"));
            bankDto.setUpdatedOn(rs.getDate("updated_on"));
            bankDto.setUpdatedBy(rs.getInt("updated_by"));
            return bankDto;
        });
        return banks;
    }

    @Override
    public BankDto getBankById(Integer bankId) {
        String sql = "SELECT * FROM bank WHERE bank_id = :bankId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bankId", bankId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
            BankDto bankDto = new BankDto();
            bankDto.setBankId(rs.getInt("bank_id"));
            bankDto.setBankName(rs.getString("bank_name"));
            bankDto.setBankAddress(rs.getString("bank_address"));
            bankDto.setCreatedOn(rs.getDate("created_on"));
            bankDto.setCreatedBy(rs.getInt("created_by"));
            bankDto.setUpdatedOn(rs.getDate("updated_on"));
            bankDto.setUpdatedBy(rs.getInt("updated_by"));
            return bankDto;
        });
    }

    @Override
    public BankDto updateBank(Integer bankId, BankDto bankDto) {
        String sql = "UPDATE bank SET bank_name = :bankName, bank_address = :bankAddress, " +
                "updated_on = :updatedOn, updated_by = :updatedBy WHERE bank_id = :bankId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bankName", bankDto.getBankName());
        parameters.put("bankAddress", bankDto.getBankAddress());
        parameters.put("updatedOn", bankDto.getUpdatedOn());
        parameters.put("updatedBy", bankDto.getUpdatedBy());
        parameters.put("bankId", bankId);

        namedParameterJdbcTemplate.update(sql, parameters);

        return bankDto;
    }

    @Override
    public void deleteBank(Integer bankId) {
        String sql = "DELETE FROM bank WHERE bank_id = :bankId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bankId", bankId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public boolean isValidUser(Integer bankId, String password){

        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("bankId",bankId);
        mapSqlParameterSource.addValue("password",password);
        String sql = "SELECT COUNT(*) FROM bank WHERE bank_id = :bankId AND password = :password";
        Integer loginEmp= namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource,Integer.class);
        if(loginEmp > 0) {
            String updateSql = "UPDATE bank SET is_active = true WHERE bank_id= :bankId";
            int rowsUpdated = namedParameterJdbcTemplate.update(updateSql,mapSqlParameterSource);
            return rowsUpdated>0;
        }
        else {
            return false;
        }
    }

    public boolean logout(Integer bankId){
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("bankId", bankId);
            String sql = "UPDATE bank SET is_active = false WHERE bank_id = :bankId";
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

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public Boolean saveToken(String bankEmail, String token){
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("bankEmail", bankEmail);
            mapSqlParameterSource.addValue("token", token);
            String sql = "INSERT INTO bank (bankEmail, token) VALUES (:bank_email, :token)";
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean sendPasswordResetEmail(String bankEmail, String token){
//        creating email message
        try{
            SimpleMailMessage message= new SimpleMailMessage();
            message.setTo(bankEmail);
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
            String sql= "UPDATE bank SET password = :newPassword WHERE token = :token";
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
        String sql = "DELETE FROM bank WHERE token = :token";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("token", token);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

    }
}