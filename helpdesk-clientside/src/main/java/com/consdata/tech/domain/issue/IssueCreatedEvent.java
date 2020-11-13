package com.consdata.tech.domain.issue;

import lombok.Value;

@Value
public class IssueCreatedEvent {
    String issueId;
    String clientId;
    String title;
    String description;
}
