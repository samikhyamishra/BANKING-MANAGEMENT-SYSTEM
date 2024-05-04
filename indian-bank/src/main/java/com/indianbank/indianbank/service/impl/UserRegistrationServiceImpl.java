package com.indianbank.indianbank.service.impl;

import com.indianbank.indianbank.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource parameters;

    @Autowired
    public UserRegistrationServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void registerUser(String customerName, String phoneNo, String address,String userName, String email, String password, Integer accountno) {
        String sql = "INSERT INTO customers(customer_name, customer_phone_no, customer_address, user_name, email, password, account_no) " +
                "VALUES (:customerName,:phoneNo, :address, :userName, :email, :password, :accountNo)";
        parameters = new MapSqlParameterSource();
        parameters.addValue("customerName", customerName);
//        parameters.addValue("middleName", middleName);
//        parameters.addValue("lastName", lastName);
        parameters.addValue("phoneNo", phoneNo);
        parameters.addValue("userName", userName);
        parameters.addValue("email", email);
        parameters.addValue("password", password);
        parameters.addValue("address",address);
        parameters.addValue("accountNo",accountno);
        namedParameterJdbcTemplate.update(sql, parameters);
    }
}

