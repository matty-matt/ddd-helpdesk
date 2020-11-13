package com.consdata.tech.domain.client;

import lombok.Value;

@Value
public class ClientCreatedEvent {
    String clientId;
    String name;
}
