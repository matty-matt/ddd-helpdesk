package com.consdata.tech.domain.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Getter
public class Client {
    private String clientId;
    private String name;
    private Set<String> openIssues;

    @CommandHandler
    public Client(CreateClientCommand cmd) {
       apply(new ClientCreatedEvent(cmd.clientId(), cmd.name()));
    }

    @EventSourcingHandler
    public void on(ClientCreatedEvent evt) {
        this.clientId = evt.clientId();
        this.name = evt.name();
        this.openIssues = new HashSet<>(5);
    }

    @CommandHandler
    public void handle(CreateClientIssueCommand cmd) {
        if (openIssues.size() < 5) {
            apply(new ClientIssueCreatedEvent(cmd.clientId(), cmd.issueId()));
        }
    }

    @EventSourcingHandler
    public void on(ClientIssueCreatedEvent event) {
        this.openIssues.add(event.issueId());
    }

    @CommandHandler
    public void handle(CloseClientIssueCommand cmd) {
        apply(new ClientIssueCreatedEvent(cmd.clientId(), cmd.issueId()));
    }

    @EventSourcingHandler
    public void on(ClientIssueClosedEvent event) {
        this.openIssues.remove(event.issueId());
    }
}
