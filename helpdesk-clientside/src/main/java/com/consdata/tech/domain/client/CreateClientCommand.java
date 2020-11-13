package com.consdata.tech.domain.client;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateClientCommand {
    @TargetAggregateIdentifier
    String clientId;
    String name;
}
