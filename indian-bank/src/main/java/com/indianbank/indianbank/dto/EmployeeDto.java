package com.indianbank.indianbank.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Integer employeeId;
    private String employeeName;
    private String employeeAddress;
    private String employeePhoneNumber;
    private String employeeEmail;
    private Boolean active;
    private String token;
    private String password;

    private Date createdOn;
    private Integer createdBy;
    private Date updatedOn;
    private Integer updatedBy;
}
