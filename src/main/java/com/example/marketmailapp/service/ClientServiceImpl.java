package com.example.marketmailapp.service;

import com.example.marketmailapp.dto.ClientDTO;
import com.example.marketmailapp.mappers.ClientMapper;
import com.example.marketmailapp.model.Client;
import com.example.marketmailapp.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.clientDTOToClient(clientDTO);
        return clientMapper.clientToClientDTO(clientRepository.save(client));
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
    }

    public List<ClientDTO> getDistinctClients() {
        return clientRepository.findAllDistinctClients().stream()
                .map(singleClient -> {
                    ClientDTO clientDTO = new ClientDTO();
                    clientDTO.setName(singleClient[0].toString());
                    clientDTO.setEmail(singleClient[1].toString());
                    return clientDTO;
                } )
                .collect(Collectors.toList());
    }
}
