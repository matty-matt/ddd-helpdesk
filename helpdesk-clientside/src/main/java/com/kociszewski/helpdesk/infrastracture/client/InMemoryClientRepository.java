package com.kociszewski.helpdesk.infrastracture.client;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryClientRepository implements ClientRepository {
    private final Map<String, String> clients = new HashMap<>();

    @Override
    public String findClientIdByName(String name) {
        return clients.get(name);
    }

    @Override
    public void insertClient(String clientId, String clientName) {
        clients.put(clientName, clientId);
    }
}
