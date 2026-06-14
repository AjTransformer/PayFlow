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
    // @Autowired tells Spring to automatically inject a TransactionRepository bean at startup.
    // At startup, Spring's ApplicationContext performs component scanning, discovers the TransactionRepository interface,
    // creates a dynamic proxy bean that implements it (backed by JpaRepository), and stores it in the IoC container.
    // When this TransactionService bean is instantiated, Spring looks for dependencies marked with @Autowired,
    // retrieves the TransactionRepository bean from the container, and injects it here.
    // This eliminates the need for manual instantiation and enables loose coupling between service and repository layers.
    @Autowired
    private TransactionRepository _transactionRepository;

    // @Autowired also injects the UserRepository here. Spring reuses the same UserRepository bean that was
    // created during component scanning, ensuring all service classes share a single repository instance.
    // This shared bean approach optimizes memory usage and ensures consistent data access patterns.
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
