package com.consdata.tech.infrastracture;

import com.consdata.tech.domain.client.CloseClientIssueCommand;
import com.consdata.tech.domain.client.CreateClientCommand;
import com.consdata.tech.domain.client.CreateClientIssueCommand;
import com.consdata.tech.users.GetUserQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HelpdeskController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/users")
    public void createUser(@RequestBody UserDTO userDTO) {
        commandGateway.send(new CreateClientCommand(UUID.randomUUID().toString(), userDTO.name()));
    }

    @GetMapping("/users/{name}")
    public UserDTO getUser(@PathVariable String name) {
        return queryGateway.query(new GetUserQuery(name), ResponseTypes.instanceOf(UserDTO.class)).join();
    }

    @PostMapping("/issues")
    public void createIssue(@RequestBody IssueDTO issueDTO) {
        commandGateway.send(new CreateClientIssueCommand(issueDTO.clientId(),
                UUID.randomUUID().toString(),
                issueDTO.title(),
                issueDTO.description()));
    }

    @PutMapping("/issues/{issueId}")
    public void closeIssue(@RequestHeader("client") String clientId,
            @PathVariable String issueId) {
        commandGateway.send(new CloseClientIssueCommand(clientId, issueId));
    }
}
