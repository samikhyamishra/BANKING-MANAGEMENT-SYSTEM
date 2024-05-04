package com.indianbank.indianbank.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {

    private Integer branchId;
    private String branchName;
    private String branchAddress;
    private Boolean active;
    private Integer accountNo;
    private Integer customerId;
    private Date createdOn;
    private Integer createdBy;
    private Date updatedOn;
    private Integer updatedBy;
}
