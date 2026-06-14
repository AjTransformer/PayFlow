package com.example.main.controller;
import com.example.main.entity.Transaction;
import com.example.main.entity.User;
import com.example.main.exception.UserNotFound;
import com.example.main.service.TransactionService;
import com.example.main.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService _userService;

    @Autowired
    private TransactionService _transactionService;

    @PostMapping("users")
    public User registersUser(@Valid @RequestBody User user){
        return _userService.registerUser(user);
    }

    @GetMapping("users")
    public List<User> findAllUsers(){
        return _userService.getAllUsers();
    }

    @GetMapping("users")
    public List<User> findUsersWithBalanceGreaterThan(@RequestBody Double balance) throws UserNotFound {
        return _userService.getAllUsersWithBalanceGreaterThan(balance);
    }

    @GetMapping("users/upi/{upiId}")
    public Optional<User> findByUserId(@PathVariable("upiId") User user){
        return _userService.findByUpiId(user);
    }

    @PostMapping("/transactions")
    public Transaction saveTransaction(@Valid @RequestBody String senderUpiId,@Valid @RequestBody String receiverUpiId,@RequestBody double amount){
        return _transactionService.sendMoney(senderUpiId,receiverUpiId,amount);
    }
}
