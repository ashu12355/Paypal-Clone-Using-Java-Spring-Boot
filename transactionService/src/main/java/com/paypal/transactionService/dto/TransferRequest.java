package com.paypal.transactionService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {

    private String senderId;
    private String receiverId;
    private String amount;
}
