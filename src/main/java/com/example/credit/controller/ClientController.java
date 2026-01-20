package com.example.credit.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.credit.model.Client;
import com.example.credit.service.ClientService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;





@CrossOrigin
@RestController
@RequestMapping(path = "/clients")
public class ClientController {
    
    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return this.clientService.getClientById(id);
    }
    

    @GetMapping
    public List<Client> getAllClients() {
        return this.clientService.getClients();
    }

    @PostMapping
    public void addClient(@RequestBody Client client) {
        this.clientService.registerClient(client);
    }

    @DeleteMapping
    public void removeClient(@PathParam(value = "id") Long id){
        this.clientService.deleteClient(id);
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        this.clientService.updateClient(client);
        return ResponseEntity.ok(client);
    }
    @GetMapping("/comptes/{id}")
    public Client getMethodName(@PathVariable long id) {
        return this.clientService.getClientByIdCompte(id);
    }
    
   
}