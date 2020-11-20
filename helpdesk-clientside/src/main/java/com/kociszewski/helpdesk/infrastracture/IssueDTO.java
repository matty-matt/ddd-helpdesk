package com.kociszewski.helpdesk.infrastracture;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueDTO {
    private String issueId;
    private String title;
    private String description;
    private String status;
}
