package com.consdata.tech.domain.issue;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ReopenIssueCommand(@TargetAggregateIdentifier String issueId) {
}
