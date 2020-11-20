package com.kociszewski.helpdesk.domain.client;

import lombok.Value;

@Value
public class GetClientIssuesQuery {
    String clientId;
}
