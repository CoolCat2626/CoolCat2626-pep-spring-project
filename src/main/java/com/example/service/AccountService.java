package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Account-related operations
 * Handles business logic for accounts
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Register account
     * @param account
     * @return
     */
    public Account registerAccount(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            return null;
        }
        
        return accountRepository.save(account);
    }
    
    /**
     * Login to account
     * @param username 
     * @param password
     * @return
     */
    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        
        return null; 
    }
    
    /**
     * Get account by ID
     * @param accountId
     * @return
     */
    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
    
    /**
     * Check if account exists by ID
     * @param accountId
     * @return 
     */
    public boolean accountExists(Integer accountId) {
        return accountRepository.existsById(accountId);
    }
}
