package com.kociszewski.helpdesk.infrastracture.issue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IssueDTO {
    private String issueId;
    private String title;
    private String description;
    private String status;
    private String clientId;
}
