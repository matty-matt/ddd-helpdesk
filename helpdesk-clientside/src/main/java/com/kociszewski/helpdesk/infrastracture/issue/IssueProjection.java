package com.kociszewski.helpdesk.infrastracture.issue;

import org.springframework.stereotype.Service;

@Service
public class IssueProjection {

    private IssueRepository issueRepository;

    public IssueProjection(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }
}
