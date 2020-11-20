package com.kociszewski.helpdesk.infrastracture.client;

public interface ClientRepository {
    String findClientIdByName(String name);
    void insertClient(String clientId, String clientName);
}
