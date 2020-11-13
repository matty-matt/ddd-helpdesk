package com.consdata.tech.domain.client;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CloseClientIssueCommand(@TargetAggregateIdentifier String clientId,
                                      String issueId) {
}
