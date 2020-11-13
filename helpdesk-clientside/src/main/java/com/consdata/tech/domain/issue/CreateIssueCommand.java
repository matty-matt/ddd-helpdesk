package com.consdata.tech.domain.issue;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateIssueCommand {
    @TargetAggregateIdentifier
    String issueId;
    String clientId;
    String title;
    String description;
}
