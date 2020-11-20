#!/bin/bash

#create client
curl -H "Content-Type: application/json" -X POST localhost:8080/clients -d '{"name": "mateusz"}'

#get client by name
CLIENT_ID="$(curl localhost:8080/clients/mateusz)"

#create issue
curl -H "Content-Type: application/json" -H "client: $CLIENT_ID" -X POST localhost:8080/issues -d '{"title": "Nie działająca strona", "description": "Strona nie działa"}'
curl -H "Content-Type: application/json" -H "client: $CLIENT_ID" -X POST localhost:8080/issues -d '{"title": "Problem z aplikacją", "description": "Aplikacja nie działa poprawnie..."}'
curl -H "Content-Type: application/json" -H "client: $CLIENT_ID" -X POST localhost:8080/issues -d '{"title": "Brak możliwości zalogowania się", "description": "Po wpisaniu poprawnych danych logowania, nie można się zalogować"}'
curl -H "Content-Type: application/json" -H "client: $CLIENT_ID" -X POST localhost:8080/issues -d '{"title": "Strona jest nieczytelna", "description": "No nie idzie się rozczytać!"}'

#my issues
curl -H "client: $CLIENT_ID" localhost:8080/issues/$CLIENT_ID | json_pp

#resolve issue
curl -H "Content-Type: application/json" -H "client: $CLIENT_ID" -X PUT localhost:8080/issues/$ISSUE_ID  -d '{"status": "RESOLVED"}'

#close issue
curl -H "Content-Type: application/json" -H "client: $CLIENT_ID" -X PUT localhost:8080/issues/$ISSUE_ID -d '{"status": "CLOSED"}'
