package com.kociszewski.helpdesk.infrastracture.client;

import com.kociszewski.helpdesk.domain.client.ClientCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ClientProjection {

    private final ClientRepository clientRepository;

    public ClientProjection(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @EventHandler
    public void handle(ClientCreatedEvent event) {
        clientRepository.insert(new ClientDTO(event.getClientId(), event.getName()));
    }

    @QueryHandler
    public String handle(GetClientQuery query) {
        return clientRepository.findByName(query.getName()).getId();
    }
}
