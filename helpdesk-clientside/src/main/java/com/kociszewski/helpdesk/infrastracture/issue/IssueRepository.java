package com.kociszewski.helpdesk.infrastracture.issue;

import com.kociszewski.helpdesk.infrastracture.issue.IssueDTO;

import java.util.List;

public interface IssueRepository {
    List<IssueDTO> findAllIssuesByClientId(String clientId);
    void insertIssue(String clientId, IssueDTO issue);
    void updateIssueStatusByIssueId(String issueId, String status);
}
