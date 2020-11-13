package com.consdata.tech.infrastracture;

import com.consdata.tech.domain.client.GetClientIssuesQuery;
import com.consdata.tech.domain.issue.Issue;
import com.consdata.tech.domain.issue.IssueClosedEvent;
import com.consdata.tech.domain.issue.IssueCreatedEvent;
import com.consdata.tech.domain.issue.IssueResolvedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IssuesProjection {
    private final Map<String, Map<String, IssueDTO>> userIssues = new HashMap<>();
    private final Map<String, IssueDTO> issues = new HashMap<>();

    @EventHandler
    public void handle(IssueCreatedEvent event) {
        IssueDTO issue = new IssueDTO(event.getIssueId(), event.getTitle(), event.getDescription(), Issue.IssueStatus.OPEN.name());
        issues.put(event.getIssueId(), issue);

        Map<String, IssueDTO> clientIssues = userIssues.getOrDefault(event.getClientId(), new HashMap<>());
        clientIssues.put(event.getIssueId(), issue);
        userIssues.put(event.getClientId(), clientIssues);
    }

    @QueryHandler
    public List<IssueDTO> handle(GetClientIssuesQuery query) {
        return new ArrayList<>(userIssues.get(query.getClientId()).values());
    }

    @EventHandler
    public void handle(IssueClosedEvent event) {
        updateIssueStatus(event.getIssueId(), Issue.IssueStatus.CLOSED.name());
    }

    @EventHandler
    public void handle(IssueResolvedEvent event) {
        updateIssueStatus(event.getIssueId(), Issue.IssueStatus.RESOLVED.name());
    }

    private void updateIssueStatus(String issueId, String status) {
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
