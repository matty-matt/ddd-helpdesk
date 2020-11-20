package com.kociszewski.helpdesk.infrastracture;

import com.kociszewski.helpdesk.domain.client.CloseClientIssueCommand;
import com.kociszewski.helpdesk.domain.client.CreateClientCommand;
import com.kociszewski.helpdesk.domain.client.CreateClientIssueCommand;
import com.kociszewski.helpdesk.domain.client.GetClientIssuesQuery;
import com.kociszewski.helpdesk.domain.issue.ResolveIssueCommand;
import com.kociszewski.helpdesk.infrastracture.issue.IssueDTO;
import com.kociszewski.helpdesk.infrastracture.issue.IssueData;
import com.kociszewski.helpdesk.infrastracture.issue.UpdateIssueData;
import com.kociszewski.helpdesk.infrastracture.client.ClientDTO;
import com.kociszewski.helpdesk.infrastracture.client.GetClientQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HelpdeskController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/clients")
    public void createClient(@RequestBody ClientDTO clientDTO) {
        commandGateway.send(new CreateClientCommand(UUID.randomUUID().toString(), clientDTO.getName()));
    }

    @GetMapping("/clients/{name}")
    public String getClientId(@PathVariable String name) {
        return queryGateway.query(new GetClientQuery(name), ResponseTypes.instanceOf(String.class)).join();
    }

    @PostMapping("/issues")
    public void createIssue(
            @RequestHeader("client") String clientId,
            @RequestBody IssueData issueData) {
        commandGateway.send(new CreateClientIssueCommand(clientId,
                UUID.randomUUID().toString(),
                issueData.getTitle(),
                issueData.getDescription()));
    }

    @GetMapping("/issues/{clientId}")
    public List<IssueDTO> getClientIssues(@PathVariable String clientId) {
        return queryGateway.query(new GetClientIssuesQuery(clientId), ResponseTypes.multipleInstancesOf(IssueDTO.class)).join();
    }

    @PutMapping("/issues/{issueId}")
    public void updateIssue(
            @RequestHeader("client") String clientId,
            @RequestBody UpdateIssueData updateIssueData,
            @PathVariable String issueId) {
        switch (updateIssueData.getStatus()) {
            case "RESOLVED" -> commandGateway.send(new ResolveIssueCommand(issueId));
            case "CLOSED" -> commandGateway.send(new CloseClientIssueCommand(clientId, issueId));
        }
    }
}
