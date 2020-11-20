package com.kociszewski.helpdesk.infrastracture.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("mongo")
@RequiredArgsConstructor
public class MongoClientRepository implements ClientRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public ClientDTO findByName(String name) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("name").is(name)),
                ClientDTO.class
        );
    }

    @Override
    public void insert(ClientDTO clientDTO) {
        mongoTemplate.insert(clientDTO);
    }
}
