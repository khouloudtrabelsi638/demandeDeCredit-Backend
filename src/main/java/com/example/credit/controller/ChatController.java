package com.example.credit.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.credit.service.ChatGPTService;

@CrossOrigin
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatGPTService chatGPTService;

    public ChatController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody Map<String, String> body) {
        try {
            String userMessage = body.get("message");
            System.out.println("Received message: " + userMessage);

            String reply = chatGPTService.askLocalModel(userMessage);
            
            System.out.println("Reply from OpenAI: " + reply);
            return ResponseEntity.ok(reply);
        } catch (Exception e) {
            System.err.println("Exception in chat: " + e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
