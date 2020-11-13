package com.consdata.tech.domain.issue;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Issue {

    @AggregateIdentifier
    private String issueId;
    private IssueStatus status;
    private String title;
    private String description;

    enum IssueStatus {
        OPEN, RESOLVED, CLOSED
    }

    @CommandHandler
    public Issue(CreateIssueCommand cmd) {
        apply(new IssueCreatedEvent(cmd.issueId(), cmd.title(), cmd.description()));
    }

    @EventSourcingHandler
    public void on(IssueCreatedEvent evt) {
        this.issueId = evt.issueId();
        this.title = evt.title();
        this.description = evt.description();
        this.status = IssueStatus.OPEN;
    }

    @CommandHandler
    public void handle(CloseIssueCommand cmd) {
        if (IssueStatus.RESOLVED == status) {
            apply(new IssueClosedEvent(cmd.issueId()));
        }
    }

    @EventSourcingHandler
    public void on (IssueClosedEvent evt) {
        this.status = IssueStatus.CLOSED;
    }

    @CommandHandler
    public void handle(ReopenIssueCommand cmd) {
        if (IssueStatus.CLOSED == status) {
            apply(new ReopenIssueEvent(cmd.issueId()));
        }
    }

    @EventSourcingHandler
    public void on (ReopenIssueEvent evt) {
        this.status = IssueStatus.OPEN;
    }
}
