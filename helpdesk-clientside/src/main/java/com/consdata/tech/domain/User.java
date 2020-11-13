package com.consdata.tech.domain;

import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class User {
    private String name;
    private int numberOfCreatedIssues;
}
