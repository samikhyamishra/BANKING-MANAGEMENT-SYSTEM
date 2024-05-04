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
@Table(name = "bank")
public class Bank {

    @Id
    @Column(name = "bank_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_address", nullable = false)
    private String bankAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Boolean active;
    @Column(name = "bank_email")
    private String bankEmail;
    @Column(name = "token")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    @CreationTimestamp
    private Date createdOn =new Date(System.currentTimeMillis());

    @Column(name = "created_by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer createdBy;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn;

    @Column(name = "updated_by")
    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    private Integer updatedBy;

}
