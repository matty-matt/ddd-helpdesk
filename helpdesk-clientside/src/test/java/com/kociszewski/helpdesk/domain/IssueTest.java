package com.kociszewski.helpdesk.domain;


import com.kociszewski.helpdesk.domain.issue.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueTest {

    private FixtureConfiguration<Issue> fixture;
    private String issueId;
    private String clientId;
    private String title;
    private String description;


    @BeforeEach
    public void setup() {
        this.fixture = new AggregateTestFixture<>(Issue.class);
        this.issueId = UUID.randomUUID().toString();
        this.clientId = UUID.randomUUID().toString();
        this.title = "Issue title!";
        this.description = "Short description";
    }

    @Test
    public void shouldCreateIssue() {
        this.fixture
                .givenNoPriorActivity()
                .when(new CreateIssueCommand(issueId, clientId, title, description))
                .expectEvents(new IssueCreatedEvent(issueId, clientId, title, description))
                .expectState(state -> {
                    assertThat(state.getIssueId()).isEqualTo(issueId);
                    assertThat(state.getClientId()).isEqualTo(clientId);
                    assertThat(state.getDescription()).isEqualTo(description);
                    assertThat(state.getTitle()).isEqualTo(title);
                    assertThat(state.getStatus()).isEqualTo(Issue.IssueStatus.OPEN);
                });
    }

    @Test
    public void shouldResolveIssue() {
        this.fixture
                .given(new IssueCreatedEvent(issueId, clientId, title, description))
                .when(new ResolveIssueCommand(issueId))
                .expectEvents(new IssueResolvedEvent(issueId))
                .expectState(state -> {
                    assertThat(state.getStatus()).isEqualTo(Issue.IssueStatus.RESOLVED);
                });
    }

    @Test
    public void shouldCloseIssue() {
        this.fixture
                .given(
                        new IssueCreatedEvent(issueId, clientId, title, description),
                        new IssueResolvedEvent(issueId))
                .when(new CloseIssueCommand(issueId))
                .expectEvents(new IssueClosedEvent(issueId))
                .expectState(state -> {
                    assertThat(state.getStatus()).isEqualTo(Issue.IssueStatus.CLOSED);
                });
    }

    @Test
    public void shouldReopenIssue() {
        this.fixture
                .given(
                        new IssueCreatedEvent(issueId, clientId, title, description),
                        new IssueResolvedEvent(issueId),
                        new IssueClosedEvent(issueId))
                .when(new ReopenIssueCommand(issueId))
                .expectEvents(new IssueReopenedEvent(issueId))
                .expectState(state -> {
                    assertThat(state.getStatus()).isEqualTo(Issue.IssueStatus.OPEN);
                });
    }
}
