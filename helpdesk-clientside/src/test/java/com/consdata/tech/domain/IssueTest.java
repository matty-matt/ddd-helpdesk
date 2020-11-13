package com.consdata.tech.domain;


import com.consdata.tech.domain.issue.Issue;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IssueTest {

    private FixtureConfiguration<Issue> fixture;

    @BeforeEach
    public void setup() {
        this.fixture = new AggregateTestFixture<>(Issue.class);
    }

    @Test
    public void shouldSendIssue() {
//        this.fixture
//                .givenNoPriorActivity()
//                .when(new CreateIssueCommand(issueId, title, description))
//                .expectEvents(new IssueCreatedEvent(issueId, title, description))

    }
}
