package com.kociszewski.helpdesk.infrastracture.storage;

import com.kociszewski.helpdesk.infrastracture.IssueDTO;

import java.util.List;

public interface IssueRepository {
    List<IssueDTO> findAllIssuesByClientId(String clientId);
    void insertIssue(String clientId, IssueDTO issue);
    void updateIssueStatusByIssueId(String issueId, String status);
}
