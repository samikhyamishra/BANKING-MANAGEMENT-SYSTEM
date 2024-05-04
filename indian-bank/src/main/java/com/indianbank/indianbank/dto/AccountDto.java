package com.indianbank.indianbank.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @NotNull
    private Integer accountNo;
    private String accountType;
    private String accountHolderName;
    private Integer accountBalance ;
    private Integer branchId;
    private String phoneNo;
    private String address;
    private String userName;
    private String email;
    private Boolean active;
    private String password;
    private Date createdOn;
    private Integer createdBy;
    private Date updatedOn;
    private Integer updatedBy;

}
