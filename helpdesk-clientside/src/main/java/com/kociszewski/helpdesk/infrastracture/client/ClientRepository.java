package com.kociszewski.helpdesk.infrastracture.client;

public interface ClientRepository {
    ClientDTO findByName(String name);
    void insert(ClientDTO clientDTO);
}
