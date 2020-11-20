package com.kociszewski.helpdesk.infrastracture.issue;

import com.kociszewski.helpdesk.domain.client.GetClientIssuesQuery;
import com.kociszewski.helpdesk.domain.issue.Issue;
import com.kociszewski.helpdesk.domain.issue.IssueClosedEvent;
import com.kociszewski.helpdesk.domain.issue.IssueCreatedEvent;
import com.kociszewski.helpdesk.domain.issue.IssueResolvedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueProjection {
    private final IssueRepository issueRepository;

    @EventHandler
    public void handle(IssueCreatedEvent event) {
        IssueDTO issue = IssueDTO.builder()
                .issueId(event.getIssueId())
                .title(event.getTitle())
                .description(event.getDescription())
                .status(Issue.IssueStatus.OPEN.name())
                .clientId(event.getClientId())
                .build();
        issueRepository.insert(issue);
    }

    @QueryHandler
    public List<IssueDTO> handle(GetClientIssuesQuery query) {
        return issueRepository.findAllByClientId(query.getClientId());
    }

    @EventHandler
    public void handle(IssueClosedEvent event) {
        issueRepository.updateStatusByIssueId(event.getIssueId(), Issue.IssueStatus.CLOSED.name());
    }

    @EventHandler
    public void handle(IssueResolvedEvent event) {
        issueRepository.updateStatusByIssueId(event.getIssueId(), Issue.IssueStatus.RESOLVED.name());
    }
}
