package com.kociszewski.helpdesk.infrastracture.issue;

import java.util.List;

public interface IssueRepository {
    List<IssueDTO> findAllByClientId(String clientId);
    void insert(IssueDTO issue);
    void updateStatusByIssueId(String issueId, String status);
}
