package com.example.marketmailapp.mappers;

import com.example.marketmailapp.dto.ClientDTO;
import com.example.marketmailapp.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO clientToClientDTO(Client client);
    Client clientDTOToClient(ClientDTO clientDTO);
}