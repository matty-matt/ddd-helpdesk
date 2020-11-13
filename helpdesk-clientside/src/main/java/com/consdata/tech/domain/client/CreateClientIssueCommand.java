package com.consdata.tech.domain.client;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateClientIssueCommand(@TargetAggregateIdentifier String clientId,
                                       String issueId,
                                       String title,
                                       String description) {
}
