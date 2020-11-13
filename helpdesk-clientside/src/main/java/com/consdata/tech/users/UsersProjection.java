package com.consdata.tech.users;

import com.consdata.tech.domain.client.ClientCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsersProjection {
    private Map<String, String> users = new HashMap<>();

    @EventHandler
    public void handle(ClientCreatedEvent event) {
        users.put(event.name(), event.clientId());
    }

    @QueryHandler
    public String handle(GetUserQuery query) {
        return users.get(query.name());
    }
}
