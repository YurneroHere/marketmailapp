package com.example.marketmailapp.service;

import com.example.marketmailapp.dto.ClientDTO;
import java.util.List;

public interface ClientService {
     ClientDTO createClient(ClientDTO clientDTO);
     List<ClientDTO> getAllClients();

}
