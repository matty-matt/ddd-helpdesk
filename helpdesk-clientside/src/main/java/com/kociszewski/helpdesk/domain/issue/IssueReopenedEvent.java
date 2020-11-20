package com.kociszewski.helpdesk.domain.issue;

import lombok.Value;

@Value
public class IssueReopenedEvent {
    String issueId;
}
