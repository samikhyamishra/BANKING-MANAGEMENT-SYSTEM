package com.indianbank.indianbank.controller;

import com.indianbank.indianbank.dto.AccountDto;
//import com.indianbank.indianbank.entity.Account;
import com.indianbank.indianbank.entity.Response;
import com.indianbank.indianbank.service.AccountsService;
import com.indianbank.indianbank.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/accounts" )
public class AccountController {
    private AccountsService accountsService;
    private UserRegistrationService userRegistrationService;

    private Response response;

    @Autowired
    public AccountController(AccountsService accountsService, UserRegistrationService userRegistrationService, Response response) {
        this.accountsService = accountsService;
        this.userRegistrationService=userRegistrationService;
        this.response=response;
    }
    //Add Account REST API
    @PostMapping("/create")
    public ResponseEntity<Response> createAccount(@RequestBody AccountDto accountDto) {
        Response createdAccount = accountsService.createAccount(accountDto);
        if (createdAccount.getStatus()==1){
            String customerName = accountDto.getAccountHolderName();
            String phoneNo = accountDto.getPhoneNo();
            String address = accountDto.getAddress();
            String userName = accountDto.getUserName();
            String email = accountDto.getEmail();
            String password = accountDto.getPassword();
            Integer accountNo = accountDto.getAccountNo();
            userRegistrationService.registerUser(customerName,phoneNo,address, userName,email,password,accountNo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createdAccount);
        }
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountsService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountNo}")
    public ResponseEntity<AccountDto> getAccountByAccountNo(@PathVariable("accountNo") Integer accountNo) {
        AccountDto account = accountsService.getAccountByAccountNo(accountNo);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountsService.updateAccount(accountDto);
        if (updatedAccount != null) {
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{accountNo}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountNo") Integer accountNo) {
        accountsService.deleteAccount(accountNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/deposit/{accountNo}")
    public ResponseEntity<String> depositAmount(@PathVariable("accountNo") Integer accountNo, @RequestParam("amount") Double amount) {
        boolean deposited = accountsService.depositAmount(accountNo, amount);
        if (deposited) {
            return ResponseEntity.ok("Amount deposited successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to deposit amount.");
        }
    }

    @PostMapping("/withdraw/{accountNo}")
    public ResponseEntity<String> withdrawAmount(@PathVariable("accountNo") Integer accountNo, @RequestParam("amount") Double amount) {
        boolean withdrawn = accountsService.withdrawAmount(accountNo, amount);
        if (withdrawn) {
            return ResponseEntity.ok("Amount withdrawn successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to withdraw amount.");
        }
    }
    // Check Balance for Account REST API
    @GetMapping("/checkBalance/{accountNo}")
    public ResponseEntity<Response> getAccountBalance(@PathVariable("accountNo") Integer accountNo) {
        Double balance = accountsService.getAccountBalance(accountNo);
        if (balance != null) {
            Response response = new Response();
            response.setStatus(1);
            response.setError("Account balance retrieved successfully.");
            response.setPost(List.of(balance.toString())); // Convert balance to string for response
            return ResponseEntity.ok(response);
        } else {
            response.setStatus(0);
            response.setError("Account not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
//    @PostMapping("/addAccount")
//    public ResponseEntity<Account> addAccount(@RequestBody AccountDto accountDto){
//        return new ResponseEntity<>(accountsService.createAccount(accountDto), HttpStatus.CREATED);
//    }


