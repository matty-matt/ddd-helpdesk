package com.consdata.tech.domain.issue;

public record IssueCreatedEvent(String issueId,
                                String title,
                                String description) {
}
