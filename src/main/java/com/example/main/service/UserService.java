package com.example.main.service;

import com.example.main.entity.User;
import com.example.main.entity.error_response;
import com.example.main.exception.InvalidAmountException;
import com.example.main.exception.UserNotFound;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    // @Autowired tells Spring to automatically inject a UserRepository bean at startup.
    // At startup, Spring's ApplicationContext performs component scanning, discovers the UserRepository interface,
    // creates a dynamic proxy bean that implements it (backed by JpaRepository), and stores it in the IoC container.
    // When this UserService bean is instantiated, Spring looks for dependencies marked with @Autowired,
    // retrieves the UserRepository bean from the container, and injects it here.
    // This eliminates the need for manual instantiation (new UserRepository()) and enables loose coupling.
    @Autowired
    private UserRepository _userRepository;

    public User registerUser(User user){
        return _userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return _userRepository.findAll();
    }

    public Optional<User> findByUpiId(User user){
        return _userRepository.findByUpiId(user.getUpiId());
    }

    public List<User> getAllUsersWithBalanceGreaterThan(Double balance) throws UserNotFound {
        Optional<List<User>> listOfUserGreaterThan= _userRepository.findUsersWithBalanceGreaterThan(balance);
        if(listOfUserGreaterThan.isEmpty()){
            throw new UserNotFound("No user is present above the requested amount");
        }
        return listOfUserGreaterThan.get();
    }

    @ExceptionHandler(UserNotFound.class)
    public error_response handleUserNotFound(UserNotFound exception) {
        return new error_response(HttpStatus.NOT_FOUND, exception.getMessage(), new Date().toInstant().toEpochMilli(), exception.getStackTrace().toString());
    }
}
