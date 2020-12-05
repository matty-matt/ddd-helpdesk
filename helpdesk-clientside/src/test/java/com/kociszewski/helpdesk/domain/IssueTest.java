package com.kociszewski.helpdesk.domain;


import com.kociszewski.helpdesk.domain.issue.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

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
}

