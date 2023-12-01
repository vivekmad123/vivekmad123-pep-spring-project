package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
    Account findAccountByUsername(String username);
    boolean existsAccountByUsernameAndPassword(String username,String password);
    
}
