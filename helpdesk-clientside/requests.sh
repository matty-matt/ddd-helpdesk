#!/bin/bash

#create user
curl -H "Content-Type: application/json" -X POST localhost:8080/users -d '{"name": "mateusz"}'

#get user by name
USER_ID="$(curl localhost:8080/users/mateusz)"

#create issue
curl -H "Content-Type: application/json" -H "client: $USER_ID" -X POST localhost:8080/issues -d '{"title": "Nie działająca strona", "description": "Strona nie działa"}'

#my issues
curl -H "client: $USER_ID" localhost:8080/issues/$USER_ID | json_pp

#resolve issue
curl -H "Content-Type: application/json" -H "client: $USER_ID" -X PUT localhost:8080/issues/$ISSUE_ID  -d '{"status": "RESOLVED"}'

#close issue
curl -H "Content-Type: application/json" -H "client: $USER_ID" -X PUT localhost:8080/issues/$ISSUE_ID -d '{"status": "CLOSED"}'
