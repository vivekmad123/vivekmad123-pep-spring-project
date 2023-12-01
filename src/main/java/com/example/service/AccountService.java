package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Component
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account newRegistration(Account account){
        if(account.getUsername() != null && !account.getUsername().isBlank() && account.getPassword() != null && account.getPassword().length() >= 4){
            Account contains=accountRepository.findAccountByUsername(account.getUsername());
            if(contains == null){
                return accountRepository.save(account);

            }
        }
        return null;
    }   
    public Account userLogin(String username,String password){
        boolean exists=accountRepository.existsAccountByUsernameAndPassword(username,password);
        if(exists){
            return accountRepository.findAccountByUsername(username);
        }
        else{
            return null;
        }
    }

}
