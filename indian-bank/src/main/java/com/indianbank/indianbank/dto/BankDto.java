package com.indianbank.indianbank.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {

    private Integer bankId;
    private String bankName;
    private String bankAddress;
    private String bankEmail;
    private String password;
    private Boolean active;
    private String token;
    private Date createdOn;
    private Integer createdBy;
    private Date updatedOn;
    private Integer updatedBy;
}
