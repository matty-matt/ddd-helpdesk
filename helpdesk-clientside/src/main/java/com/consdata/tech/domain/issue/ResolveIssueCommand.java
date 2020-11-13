package com.consdata.tech.domain.issue;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class ResolveIssueCommand {
    @TargetAggregateIdentifier
    String issueId;
}
