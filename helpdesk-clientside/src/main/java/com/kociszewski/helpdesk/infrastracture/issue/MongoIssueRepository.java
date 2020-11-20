package com.kociszewski.helpdesk.infrastracture.issue;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoIssueRepository extends MongoRepository<IssueDTO, String> {

    List<IssueDTO> findAllByClientId(String clientId);

    default void updateStatusByIssueId(String issueId, String status) {
        // DO NOT TRY THIS ON PROD :)
        IssueDTO issue = findById(issueId).orElseThrow();
        issue.setStatus(status);
        save(issue);
    }
}
