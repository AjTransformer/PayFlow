package com.example.main.controller;

import com.example.main.entity.Transaction;
import com.example.main.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService _transactionService;

    @PostMapping("/transactions")
    public Transaction saveTransaction(
            @Valid @RequestBody String senderUpiId,
            @Valid @RequestBody String receiverUpiId,
            @RequestBody double amount) {
        return _transactionService.sendMoney(senderUpiId, receiverUpiId, amount);
    }
}
