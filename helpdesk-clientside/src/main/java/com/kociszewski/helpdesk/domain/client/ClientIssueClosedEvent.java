package com.kociszewski.helpdesk.domain.client;

import lombok.Value;

@Value
public class ClientIssueClosedEvent {
    String clientId;
    String issueId;
}
