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
        IssueDTO originalIssue = issues.get(event.getIssueId());
        originalIssue.setStatus(Issue.IssueStatus.CLOSED.name());
        issues.put(event.getIssueId(), originalIssue);

        Map<String, Map<String, IssueDTO>> modifiedUserIssues = userIssues.entrySet().stream()
                .filter(as -> as.getValue().values().stream().anyMatch(issue -> issue.getIssueId().equals(event.getIssueId())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        modifiedUserIssues.forEach((userId, mapOfIssues) -> {
            Map<String, IssueDTO> modifiedIssues = mapOfIssues.values()
                    .stream()
                    .map(issueDTO -> new IssueDTO(issueDTO.getIssueId(), issueDTO.getTitle(), issueDTO.getDescription(), Issue.IssueStatus.CLOSED.name()))
                    .collect(Collectors.toMap(IssueDTO::getIssueId, as -> as));
            userIssues.put(userId, modifiedIssues);
        });
    }

    @EventHandler
    public void handle(IssueResolvedEvent event) {
        IssueDTO originalIssue = issues.get(event.getIssueId());
        originalIssue.setStatus(Issue.IssueStatus.RESOLVED.name());
        issues.put(event.getIssueId(), originalIssue);

        Map<String, Map<String, IssueDTO>> modifiedUserIssues = userIssues.entrySet().stream()
                .filter(as -> as.getValue().values().stream().anyMatch(issue -> issue.getIssueId().equals(event.getIssueId())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        modifiedUserIssues.forEach((userId, mapOfIssues) -> {
            Map<String, IssueDTO> modifiedIssues = mapOfIssues.values()
                    .stream()
                    .map(issueDTO -> new IssueDTO(issueDTO.getIssueId(), issueDTO.getTitle(), issueDTO.getDescription(), Issue.IssueStatus.RESOLVED.name()))
                    .collect(Collectors.toMap(IssueDTO::getIssueId, as -> as));
            userIssues.put(userId, modifiedIssues);
        });
    }
}
