package com.indianbank.indianbank.controller;
import com.indianbank.indianbank.dto.TransactionDto;
import com.indianbank.indianbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @RestController
    @RequestMapping("/api/transactions")
    public class TransactionController {
        private TransactionService transactionService;

        @Autowired
        public TransactionController(TransactionService transactionService) {
            this.transactionService = transactionService;
        }

        @PostMapping("/create")
        public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
            TransactionDto createdTransaction = transactionService.createTransaction(transactionDto);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        }

        @GetMapping("/allTransactions")
        public ResponseEntity<List<TransactionDto>> getAllTransactions() {
            List<TransactionDto> transactions = transactionService.getAllTransactions();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Integer transactionId) {
            TransactionDto transaction = transactionService.getTransactionById(transactionId);
            if (transaction != null) {
                return new ResponseEntity<>(transaction, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/byAccountNo/{accountNo}")
        public ResponseEntity<List<TransactionDto>> getTransactionsByAccountNo(@PathVariable Integer accountNo) {
            List<TransactionDto> transactions = transactionService.getTransactionByAccountNo(accountNo);
            if (!transactions.isEmpty()) {
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }


        @PutMapping("/update/{id}")
        public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
            TransactionDto updatedTransaction = transactionService.updateTransaction(transactionDto);
            if (updatedTransaction != null) {
                return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

//        private TransactionService transactionService;
//
//       @Autowired
//        public TransactionController(TransactionService transactionService) {
//            this.transactionService = transactionService;
//        }
//
//       @PostMapping("/create")
//        public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
//            TransactionDto createdTransaction = transactionService.createTransaction(transactionDto);
//            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
//        }
//
//        @GetMapping("/allTransactions")
//        public ResponseEntity<List<TransactionDto>> getAllTransactions() {
//            List<TransactionDto> transactions = transactionService.getAllTransactions();
//            return new ResponseEntity<>(transactions, HttpStatus.OK);
//        }
//
//        @GetMapping("/{id}")
//        public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
//            TransactionDto transaction = transactionService.getTransactionById(id);
//            if (transaction != null) {
//                return new ResponseEntity<>(transaction, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }
//
//        @PutMapping("/update/{id}")
//        public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
//            TransactionDto updatedTransaction = transactionService.updateTransaction(id, transactionDto);
//            if (updatedTransaction != null) {
//                return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }
//
//        @DeleteMapping("/delete/{id}")
//        public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
//            transactionService.deleteTransaction(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }


