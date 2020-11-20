package com.kociszewski.helpdesk.infrastracture.issue;

import com.kociszewski.helpdesk.domain.issue.Issue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Qualifier("mongo")
public class MongoIssueRepository implements IssueRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<IssueDTO> findAllByClientId(String clientId) {
        return mongoTemplate.find(
                Query.query(Criteria.where("clientId").is(clientId)),
                IssueDTO.class
        );
    }

    @Override
    public void insert(IssueDTO issue) {
        mongoTemplate.insert(issue);
    }

    @Override
    public void updateStatusByIssueId(String issueId, String status) {
        mongoTemplate.findAndModify(
                Query.query(Criteria.where("issueId").is(issueId)),
                Update.update("status", Issue.IssueStatus.CLOSED.name()),
                IssueDTO.class
        );
    }
}
