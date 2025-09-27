package com.paypal.transactionService.repository;

import com.paypal.transactionService.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
}
