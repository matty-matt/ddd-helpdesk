package com.kociszewski.helpdesk.infrastracture.storage;

import com.kociszewski.helpdesk.infrastracture.IssueDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryIssueRepository implements IssueRepository {

    private final Map<String, Map<String, IssueDTO>> userIssues = new HashMap<>();
    private final Map<String, IssueDTO> issues = new HashMap<>();

    @Override
    public List<IssueDTO> findAllIssuesByClientId(String clientId) {
        return new ArrayList<>(userIssues.get(clientId).values());
    }

    @Override
    public void insertIssue(String clientId, IssueDTO issue) {
        issues.put(issue.getIssueId(), issue);
        Map<String, IssueDTO> clientIssues = userIssues.getOrDefault(clientId, new HashMap<>());
        clientIssues.put(issue.getIssueId(), issue);
        userIssues.put(clientId, clientIssues);
    }

    @Override
    public void updateIssueStatusByIssueId(String issueId, String status) {
        IssueDTO originalIssue = issues.get(issueId);
        originalIssue.setStatus(status);
        issues.put(issueId, originalIssue);

        List<String> usersHavingThisIssue = userIssues.entrySet().stream()
                .filter(as -> as.getValue().values().stream().anyMatch(issue -> issue.getIssueId().equals(issueId)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        usersHavingThisIssue.forEach(userId -> {
            userIssues.get(userId).get(issueId).setStatus(status);
        });
    }
}
