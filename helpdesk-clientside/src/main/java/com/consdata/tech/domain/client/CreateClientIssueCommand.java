package com.consdata.tech.domain.client;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateClientIssueCommand {
    @TargetAggregateIdentifier
    String clientId;
    String issueId;
    String title;
    String description;
}
