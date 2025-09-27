package com.paypal.transactionService.service;

import com.paypal.transactionService.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    List<Transaction> getAllTransaction();

}
