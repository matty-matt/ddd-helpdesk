package com.consdata.tech.domain.issue;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ResolveIssueCommand(@TargetAggregateIdentifier String issueId) {
}
