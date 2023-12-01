package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService,MessageService messageService) {
        this.accountService = accountService;
        this.messageService=messageService;
    }
   
    @PostMapping("/register")
    public ResponseEntity<Object> registerNewAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.newRegistration(account);

        if (registeredAccount == null) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        
        return ResponseEntity.ok(registeredAccount);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> userLoginStatus(@RequestBody Account account){
        Account isAccountExist = accountService.userLogin(account.getUsername(),account.getPassword());

        if (isAccountExist != null) {
           return ResponseEntity.ok(isAccountExist);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account doesn't exist ");
    }
    @PostMapping("/messages")
    public ResponseEntity<Object> addNewMessage(@RequestBody Message message){
        Message addedMessage=messageService.CreateNewMessage(message);

        if (addedMessage != null) {
           return ResponseEntity.ok(addedMessage);
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request failed");
    }
    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }
    @GetMapping("/messages/{message_id}")
    public Message getMessageById(@PathVariable Integer message_id){
        return messageService.getMessageById(message_id).orElse(null);
    }
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer message_id){
        int rowsUpdated = messageService.deleteMessageById(message_id);
        if(rowsUpdated==1){
            return ResponseEntity.status(HttpStatus.OK).body("1");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("");
        }

    }
    
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Object> updateMessageText(@PathVariable Integer message_id,@RequestBody Message newMessage){
        if(!newMessage.getMessage_text().isBlank() && newMessage.getMessage_text().length() <=255 ){
           int rowsUpdated= messageService.updateMessageTextById(message_id,newMessage.getMessage_text());
            if(rowsUpdated == 1){
                return ResponseEntity.status(HttpStatus.OK).body("1");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request failed");
    }

    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getAllMessagesByPostedBy(@PathVariable int account_id){
        return messageService.getAllMessagesByAccountId(account_id);
    }
    
}
