package com.kociszewski.helpdesk.domain.issue;

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
