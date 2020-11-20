package com.kociszewski.helpdesk.domain.client;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CloseClientIssueCommand{
    @TargetAggregateIdentifier
    String clientId;
    String issueId;
}
