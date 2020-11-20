package com.kociszewski.helpdesk.infrastracture.issue;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoIssueRepository extends MongoRepository<IssueDTO, String> {
    List<IssueDTO> findAllByClientId(String clientId);
    void updateStatusByIssueId(String issueId, String status);
}
