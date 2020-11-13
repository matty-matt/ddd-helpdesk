package com.consdata.tech.domain.issue;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateIssueCommand(@TargetAggregateIdentifier String issueId,
                                 String title,
                                 String description) {}
