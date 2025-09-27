package com.paypal.transactionService.controller;

import com.paypal.transactionService.entity.Transaction;
import com.paypal.transactionService.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction/")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Transaction transaction){
        Transaction created = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(created);
    }
    @GetMapping("getAllTransaction")
    public List<Transaction> getAllTransaction(){
        return transactionService.getAllTransaction();
    }
}
