package com.example.marketmailapp.model;

import com.example.marketmailapp.enums.MailStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subject;
    private String emailBody;

    @Enumerated(EnumType.STRING)
    private MailStatus status = MailStatus.NONE; // e.g., CREATED, SENT

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> subscribers;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    // Getters and Setters
}
