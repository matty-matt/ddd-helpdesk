package com.kociszewski.helpdesk.users;

import com.kociszewski.helpdesk.domain.client.ClientCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsersProjection {
    private final Map<String, String> users = new HashMap<>();

    @EventHandler
    public void handle(ClientCreatedEvent event) {
        users.put(event.getName(), event.getClientId());
    }

    @QueryHandler
    public String handle(GetUserQuery query) {
        return users.get(query.getName());
    }
}
