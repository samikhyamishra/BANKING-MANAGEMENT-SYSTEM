package com.indianbank.indianbank.controller;

import com.indianbank.indianbank.dto.BankDto;
import com.indianbank.indianbank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/banks")
public class BankController {
    private  BankService bankService;

 @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest){
        Integer bankId = Integer.valueOf(loginRequest.get("bankId"));
        String password = loginRequest.get("password");
        Boolean i = bankService.isValidUser(bankId, password);
        if (i){
            return ResponseEntity.ok("Login successful!");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID or password");
        }
    }

    @DeleteMapping("/logout/{bankId}")
    public ResponseEntity<String> logout(@PathVariable Integer bankId) {
        try {
            boolean result = bankService.logout(bankId);
            if (result) {
                return ResponseEntity.ok("Logout Successful!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed. Error: " + e.getMessage());
        }
    }

    @PostMapping("/ForgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestEmail) {
        String bankEmail = requestEmail.get("bankEmail");
        if (bankEmail!=null && !bankEmail.isEmpty()){
            String token = bankService.generateToken();
            boolean saved = bankService.saveToken(bankEmail, token);
            if (saved) {
                boolean emailSent = bankService.sendPasswordResetEmail(bankEmail, token);
                if (emailSent) {
                    return ResponseEntity.ok("Password reset email sent successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send password reset email.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save reset token.");
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email parameter is required.");
        }

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean reset = bankService.resetPassword(token, newPassword);
        if (reset) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<BankDto> createBank(@RequestBody BankDto bankDto) {
        BankDto createdBank = bankService.createBank(bankDto);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

   @GetMapping("/all")
    public ResponseEntity<List<BankDto>> getAllBanks() {
        List<BankDto> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{bankId}")
    public ResponseEntity<BankDto> getBankById(@PathVariable Integer bankId) {
        BankDto bank = bankService.getBankById(bankId);
        if (bank != null) {
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

  @PutMapping("/update/{bankId}")
    public ResponseEntity<BankDto> updateBank(@PathVariable Integer bankId, @RequestBody BankDto bankDto) {
        BankDto updatedBank = bankService.updateBank(bankId, bankDto);
        if (updatedBank != null) {
            return new ResponseEntity<>(updatedBank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{bankId}")
    public ResponseEntity<Void> deleteBank(@PathVariable Integer bankId) {
        bankService.deleteBank(bankId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
