package com.kociszewski.helpdesk.infrastracture.issue;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryIssueRepository implements IssueRepository {

    private final Map<String, Map<String, IssueDTO>> clientIssues = new HashMap<>();
    private final Map<String, IssueDTO> issues = new HashMap<>();

    @Override
    public List<IssueDTO> findAllByClientId(String clientId) {
        return new ArrayList<>(clientIssues.get(clientId).values());
    }

    @Override
    public void insert(IssueDTO issue) {
        issues.put(issue.getIssueId(), issue);
        Map<String, IssueDTO> clientIssues = this.clientIssues.getOrDefault(issue.getClientId(), new HashMap<>());
        clientIssues.put(issue.getIssueId(), issue);
        this.clientIssues.put(issue.getClientId(), clientIssues);
    }

    @Override
    public void updateStatusByIssueId(String issueId, String status) {
        IssueDTO originalIssue = issues.get(issueId);
        originalIssue.setStatus(status);
        issues.put(issueId, originalIssue);

        List<String> clientsHavingThisIssue = clientIssues.entrySet().stream()
                .filter(as -> as.getValue().values().stream().anyMatch(issue -> issue.getIssueId().equals(issueId)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        clientsHavingThisIssue.forEach(clientId -> clientIssues.get(clientId).get(issueId).setStatus(status));
    }
}
