package com.kociszewski.helpdesk.infrastracture.client;

import com.kociszewski.helpdesk.domain.client.ClientCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientsProjection {
    private final Map<String, String> clients = new HashMap<>();

    @EventHandler
    public void handle(ClientCreatedEvent event) {
        clients.put(event.getName(), event.getClientId());
    }

    @QueryHandler
    public String handle(GetClientQuery query) {
        return clients.get(query.getName());
    }
}
