package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;
    /**
     * Register account
     * @param account
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty() || 
            account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
    }

    /**
     * Login to account
     * @param account
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedInAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(loggedInAccount);
    }

    /**
     * Create message
     * @param message
     * @return
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        if (message.getMessageText() == null || message.getMessageText().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    
        if (message.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        if (message.getPostedBy() == null || !accountService.accountExists(message.getPostedBy())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        Message createdMessage = messageService.createMessage(message);
        
        return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
    }

    /**
     * Get all messages
     * @return
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    /**
     * Get message by ID
     * @param message_id
     * @return
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessageById(@PathVariable int message_id) {
        Message message = messageService.getMessageById(message_id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Delete message by ID
     * @param message_id 
     * @return
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int message_id) {
        int rowsAffected = messageService.deleteMessageById(message_id);
        if (rowsAffected == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
    }

    /**
     * Update message by ID
     * @param message_id
     * @param message
     * @return
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        if (message.getMessageText() == null || message.getMessageText().isEmpty() || 
            message.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        int rowsAffected = messageService.updateMessageById(message_id, message.getMessageText());
        if (rowsAffected == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
    }

    /**
     * Get messages by account ID
     * @param account_id
     * @return
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int account_id) {
        List<Message> messages = messageService.getMessagesByAccountId(account_id);

        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
