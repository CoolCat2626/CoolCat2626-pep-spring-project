package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Message entity
 * Provides database operations for messages
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Find all messages posted by a specific account
     * @param postedBy 
     * @return
     */
    List<Message> findByPostedBy(Integer postedBy);
}
