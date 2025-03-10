package com.kociszewski.helpdesk.domain.client;

import com.kociszewski.helpdesk.domain.issue.CloseIssueCommand;
import com.kociszewski.helpdesk.domain.issue.CreateIssueCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDomainService {
    private final CommandGateway commandGateway;

    @EventHandler
    public void handle(ClientIssueCreatedEvent event) {
        commandGateway.send(new CreateIssueCommand(event.getIssueId(), event.getClientId(), event.getTitle(), event.getDescription()));
    }

    @EventHandler
    public void handle(ClientIssueClosedEvent event) {
        commandGateway.send(new CloseIssueCommand(event.getIssueId()));
    }
}
