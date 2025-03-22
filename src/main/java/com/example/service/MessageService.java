package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Message-related operations
 * Handles business logic for messages
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    /**
     * Create new message
     * @param message
     * @return
     */
    public Message createMessage(Message message) {
        message.setMessageId(null);
        return messageRepository.save(message);
    }
    
    /**
     * Get all messages
     * @return
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Get message by ID
     * @param messageId
     * @return
     */
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }
    
    /**
     * Delete message by ID
     * @param messageId
     * @return
     */
    public int deleteMessageById(Integer messageId) {
        if (!messageRepository.existsById(messageId)) {
            return 0;
        }
        
        messageRepository.deleteById(messageId);
        return 1;
    }
    
    /**
     * Update message by ID
     * @param messageId
     * @param messageText
     * @return 
     */
    public int updateMessageById(Integer messageId, String messageText) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (!messageOptional.isPresent()) {
            return 0;
        }
        
        Message message = messageOptional.get();
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1; 
    }
    
    /**
     * Get messages by account ID
     * @param accountId 
     * @return
     */
    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
