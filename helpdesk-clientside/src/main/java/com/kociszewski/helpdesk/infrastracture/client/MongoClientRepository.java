package com.kociszewski.helpdesk.infrastracture.client;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoClientRepository extends MongoRepository<ClientDTO, String> {
    ClientDTO findByName(String name);
}
