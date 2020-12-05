package com.kociszewski.helpdesk.domain.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Getter
@Slf4j
public class Issue {

    @AggregateIdentifier
    private String issueId;
    private String clientId;
    private IssueStatus status;
    private String title;
    private String description;

    public enum IssueStatus {
        OPEN, RESOLVED, CLOSED
    }

    @CommandHandler
    public Issue(CreateIssueCommand cmd) {
        apply(new IssueCreatedEvent(cmd.getIssueId(), cmd.getClientId(), cmd.getTitle(), cmd.getDescription()));
    }

    @EventSourcingHandler
    public void on(IssueCreatedEvent evt) {
        this.issueId = evt.getIssueId();
        this.clientId = evt.getClientId();
        this.title = evt.getTitle();
        this.description = evt.getDescription();
        this.status = IssueStatus.OPEN;
    }

    @CommandHandler
    public void handle(CloseIssueCommand cmd) {
        if (IssueStatus.RESOLVED != status) {
            log.warn("Cannot close issue that is not resolved yet");
            return;
        }
        apply(new IssueClosedEvent(cmd.getIssueId()));
    }

    @EventSourcingHandler
    public void on(IssueClosedEvent evt) {
        this.status = IssueStatus.CLOSED;
    }

    @CommandHandler
    public void handle(ReopenIssueCommand cmd) {
        if (IssueStatus.CLOSED != status) {
            log.warn("Cannot reopen issue that is not closed");
            return;
        }
        apply(new IssueReopenedEvent(cmd.getIssueId()));
    }

    @EventSourcingHandler
    public void on(IssueReopenedEvent evt) {
        this.status = IssueStatus.OPEN;
    }

    @CommandHandler
    public void handle(ResolveIssueCommand cmd) {
        // Potentially command would come from Worker context
        if (IssueStatus.OPEN != status) {
            log.warn("Cannot resolve issue that is not open");
            return;
        }
        apply(new IssueResolvedEvent(cmd.getIssueId()));

    }

    @EventSourcingHandler
    public void on(IssueResolvedEvent evt) {
        this.status = IssueStatus.RESOLVED;
    }
}
