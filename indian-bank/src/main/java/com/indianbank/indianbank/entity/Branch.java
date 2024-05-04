package com.indianbank.indianbank.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")
public class Branch {


    @Id
    @Column(name = "branch_id", nullable = false)
    private Integer branchId;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "branch_address", nullable = false)
    private String branchAddress;
    @Column(name = "is_active")
    private Boolean active;
    @Column(name = "account_no")
    private Integer accountNo;
    @Column(name = "customer_id")
    private Integer customerId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    @CreationTimestamp
    private Date createdOn;

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
