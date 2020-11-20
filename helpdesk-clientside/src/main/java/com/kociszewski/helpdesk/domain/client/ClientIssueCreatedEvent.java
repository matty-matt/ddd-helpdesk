package com.kociszewski.helpdesk.domain.client;

import lombok.Value;

@Value
public class ClientIssueCreatedEvent {
    String clientId;
    String issueId;
    String title;
    String description;
}
