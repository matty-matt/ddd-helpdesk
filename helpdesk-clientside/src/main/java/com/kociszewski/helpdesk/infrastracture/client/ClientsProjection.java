package com.kociszewski.helpdesk.infrastracture.client;

import com.kociszewski.helpdesk.domain.client.ClientCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientsProjection {

    private final ClientRepository clientRepository;

    @EventHandler
    public void handle(ClientCreatedEvent event) {
        clientRepository.insert(new ClientDTO(event.getClientId(), event.getName()));
    }

    @QueryHandler
    public String handle(GetClientQuery query) {
        return clientRepository.findByName(query.getName()).getId();
    }
}
