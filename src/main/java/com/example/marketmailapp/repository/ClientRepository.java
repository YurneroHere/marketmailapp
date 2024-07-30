package com.example.marketmailapp.repository;

import com.example.marketmailapp.dto.ClientDTO;
import com.example.marketmailapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);

    @Query(value = "select distinct name, email from client", nativeQuery = true)
    List<Object[]> findAllDistinctClients();

}
