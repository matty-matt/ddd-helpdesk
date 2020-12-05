package com.kociszewski.helpdesk.domain.client;

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
    private static final int MAX_NUMBER_OF_PROGRESS_ISSUES = 3;

    @AggregateIdentifier
    private String clientId;
    private String name;
    private Set<String> issuesInProgress;

    @CommandHandler
    public Client(CreateClientCommand cmd) {
       apply(new ClientCreatedEvent(cmd.getClientId(), cmd.getName()));
    }

    @EventSourcingHandler
    public void on(ClientCreatedEvent evt) {
        this.clientId = evt.getClientId();
        this.name = evt.getName();
        this.issuesInProgress = new HashSet<>(MAX_NUMBER_OF_PROGRESS_ISSUES);
    }

    @CommandHandler
    public void handle(CreateClientIssueCommand cmd) {
        if (issuesInProgress.size() == MAX_NUMBER_OF_PROGRESS_ISSUES) {
            log.warn("Client cannot have more than {} issues in progress.", MAX_NUMBER_OF_PROGRESS_ISSUES);
            return;
        }
        apply(new ClientIssueCreatedEvent(cmd.getClientId(), cmd.getIssueId(), cmd.getTitle(), cmd.getDescription()));
    }

    @EventSourcingHandler
    public void on(ClientIssueCreatedEvent event) {
        this.issuesInProgress.add(event.getIssueId());
    }

    @CommandHandler
    public void handle(CloseClientIssueCommand cmd) {
        apply(new ClientIssueClosedEvent(cmd.getClientId(), cmd.getIssueId()));
    }

    @EventSourcingHandler
    public void on(ClientIssueClosedEvent event) {
        this.issuesInProgress.remove(event.getIssueId());
    }
}
