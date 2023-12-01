package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }
    public Message CreateNewMessage(Message message){
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length()<255){
            if(messageRepository.existsById(message.getPosted_by())){
                return messageRepository.save(message);
            }
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId){
        return messageRepository.findById(messageId);
    }

    public int  deleteMessageById(int messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            //Message message = messageOptional.get();
            messageRepository.deleteById(messageId);
            return 1;
        }
        else{
        return 0;
        }
    }
    
    public int updateMessageTextById(int messageId,String messageText){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message existingMessage = optionalMessage.get();
            existingMessage.setMessage_text(messageText);
            messageRepository.save(existingMessage);
            return 1;
        } else {
            return 0; 
        }
        
    }

    public List<Message> getAllMessagesByAccountId(int postedBy){
        return messageRepository.findAllByPosted_by(postedBy);
    
    }
    
}
