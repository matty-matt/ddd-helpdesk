package com.consdata.tech.domain.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Getter
@Slf4j
public class Client {
    private static final int MAX_OPEN_ISSUES = 3;

    @AggregateIdentifier
    private String clientId;
    private String name;
    private Set<String> openIssues;

    @CommandHandler
    public Client(CreateClientCommand cmd) {
       apply(new ClientCreatedEvent(cmd.getClientId(), cmd.getName()));
    }

    @EventSourcingHandler
    public void on(ClientCreatedEvent evt) {
        this.clientId = evt.getClientId();
        this.name = evt.getName();
        this.openIssues = new HashSet<>(5);
    }

    @CommandHandler
    public void handle(CreateClientIssueCommand cmd) {
        if (openIssues.size() == MAX_OPEN_ISSUES) {
            log.warn("Client cannot have more than {} open issues.", MAX_OPEN_ISSUES);
            return;
        }
        apply(new ClientIssueCreatedEvent(cmd.getClientId(), cmd.getIssueId(), cmd.getTitle(), cmd.getDescription()));
    }

    @EventSourcingHandler
    public void on(ClientIssueCreatedEvent event) {
        this.openIssues.add(event.getIssueId());
    }

    @CommandHandler
    public void handle(CloseClientIssueCommand cmd) {
        apply(new ClientIssueClosedEvent(cmd.getClientId(), cmd.getIssueId()));
    }

    @EventSourcingHandler
    public void on(ClientIssueClosedEvent event) {
        this.openIssues.remove(event.getIssueId());
    }
}
