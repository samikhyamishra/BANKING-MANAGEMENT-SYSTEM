package com.indianbank.indianbank.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class TransactionDto {

    private Integer transactionId;

    private String transactionType;

    private Double transactionAmount;

    private Integer accountNo;

    private Boolean active;

    private Date createdOn = new Date(System.currentTimeMillis());

    private Integer createdBy;

    private Date updatedOn;

    private Integer updatedBy;
    }

