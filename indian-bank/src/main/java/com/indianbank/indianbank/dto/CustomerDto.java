package com.indianbank.indianbank.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Integer customerId;
    private String customerName;
    private String phoneNo;
    private String address;
    private String userName;
    private String password;
    private String email;
    private Boolean active;
    private String token;
    private Integer accountNo;
    private Date createdOn = new Date(System.currentTimeMillis());
    private Integer createdBy;
    private Date updatedOn;
    private Integer updatedBy;

}
