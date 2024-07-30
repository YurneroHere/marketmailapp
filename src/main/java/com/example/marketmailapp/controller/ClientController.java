package com.example.marketmailapp.controller;

import com.example.marketmailapp.dto.ClientDTO;
import com.example.marketmailapp.model.Client;
import com.example.marketmailapp.service.ClientService;
import com.example.marketmailapp.service.ClientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.createClient(clientDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/client")
    public ResponseEntity<List<ClientDTO>> getClientById() {
        return ResponseEntity.ok(clientService.getDistinctClients());
    }
}
