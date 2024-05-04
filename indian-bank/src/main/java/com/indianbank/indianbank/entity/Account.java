package com.indianbank.indianbank.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * $table.getTableComment()
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account{
    @Id
    @Column(name = "account_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountNo;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "account_holder_name")
    private String accountHolderName;
    @Column(name = "account_balance")
    private Integer accountBalance;
    @Column(name = "branch_id", length = 20)
    private Integer branchId;
    @Column(name = "phone_no", length = 10)
    private String phoneNo;
    @Column(name = "customer_address")
    private String address;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
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
