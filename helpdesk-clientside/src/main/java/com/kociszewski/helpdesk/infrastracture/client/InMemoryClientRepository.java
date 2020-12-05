package com.kociszewski.helpdesk.infrastracture.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("memory")
@Primary
public class InMemoryClientRepository implements ClientRepository {
    private final Map<String, ClientDTO> clients = new HashMap<>();

    @Override
    public ClientDTO findByName(String name) {
        return clients.get(name);
    }

    @Override
    public void insert(ClientDTO clientDTO) {
        clients.put(clientDTO.getName(), clientDTO);
    }
}
