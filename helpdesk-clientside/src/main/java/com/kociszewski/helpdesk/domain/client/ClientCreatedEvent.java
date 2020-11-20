package com.kociszewski.helpdesk.domain.client;

import lombok.Value;

@Value
public class ClientCreatedEvent {
    String clientId;
    String name;
}
