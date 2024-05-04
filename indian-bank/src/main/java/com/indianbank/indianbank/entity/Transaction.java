package com.indianbank.indianbank.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "transactions")
    public class Transaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "transaction_id")
        private Integer transactionId;

        @Column(name = "transaction_type")
        private String transactionType;

        @Column(name = "transaction_amount")
        private Double transactionAmount;

        @Column(name = "account_no")
        private Integer accountNo;

        @Column(name = "is_active")
        private Boolean active;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_on")
        @CreationTimestamp
        private Date createdOn = new Date(System.currentTimeMillis());

        @Column(name = "created_by")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private Integer createdBy;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "updated_on")
        @UpdateTimestamp
        private Date updatedOn;

        @Column(name = "updated_by")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private Integer updatedBy;

    }


