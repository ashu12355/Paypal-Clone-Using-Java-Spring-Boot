package com.paypal.transactionService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transactionService.entity.Transaction;
import com.paypal.transactionService.repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final ObjectMapper objectMapper;

    public TransactionServiceImpl(TransactionRepo transactionRepo, ObjectMapper objectMapper) {
        this.transactionRepo = transactionRepo;
        this.objectMapper = objectMapper;
    }

    @Override
    public Transaction createTransaction(Transaction transactionRequest) {



        Transaction transaction = new Transaction();
        transaction.setSenderId(transactionRequest.getSenderId());
        transaction.setReceiverId(transactionRequest.getReceiverId());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        return transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepo.findAll();
    }
}
