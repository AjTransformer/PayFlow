package com.example.main.service;

import com.example.main.entity.Transaction;
import com.example.main.entity.User;
import com.example.main.entity.error_response;
import com.example.main.exception.InvalidAmountException;
import com.example.main.exception.UserNotPresentInUpiPayRoleException;
import com.example.main.repository.TransactionRepository;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository _transactionRepository;

    @Autowired
    private UserRepository _userRepository;

    public Transaction sendMoney(String senderUpiId, String receiverUpiId, Double amount) throws UserNotPresentInUpiPayRoleException, InvalidAmountException {
        Optional<User> senderUser = _userRepository.findByUpiId(senderUpiId);
        Optional<User> receiverUser = _userRepository.findByUpiId(receiverUpiId);

        if (senderUser.isEmpty()) {
            throw new UserNotPresentInUpiPayRoleException("Sender User is not present in UPI pay role, please register!!");
        }

        if (receiverUser.isEmpty()) {
            throw new UserNotPresentInUpiPayRoleException("Receiver User is not present in UPI pay role, please register!!");
        }

        if (amount < 1.00 || amount > 200000.00) {
            throw new InvalidAmountException("Invalid amount entered!");
        }

        Transaction transaction = new Transaction();
        transaction.setSenderUpiId(senderUpiId);
        transaction.setReceiverUpiId(receiverUpiId);
        transaction.setAmount(amount);
        transaction.setNote("Money Transfer");
        return _transactionRepository.save(transaction);
    }

    @ExceptionHandler(UserNotPresentInUpiPayRoleException.class)
    public error_response handleUserNotPresent(UserNotPresentInUpiPayRoleException exception) {
        return new error_response(HttpStatus.NOT_FOUND, exception.getMessage(), new Date().toInstant().toEpochMilli(), exception.getStackTrace().toString());
    }

    @ExceptionHandler(InvalidAmountException.class)
    public error_response handleInvalidAmountException(InvalidAmountException exception) {
        return new error_response(HttpStatus.NOT_FOUND, exception.getMessage(), new Date().toInstant().toEpochMilli(), exception.getStackTrace().toString());
    }
}
