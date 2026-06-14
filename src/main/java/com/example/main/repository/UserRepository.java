package com.example.main.repository;

import com.example.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUpiId(String username);

    @Query("SELECT u FROM User u WHERE u.balance > :amount")
    public Optional<List<User>> findUsersWithBalanceGreaterThan(Double balance);
}
