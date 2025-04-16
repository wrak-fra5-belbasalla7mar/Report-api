package org.fawry.reportapi.service;

import org.fawry.reportapi.exception.NotFoundException;
import org.fawry.reportapi.model.Team;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TeamReportService {

    private final WebClient webClient = WebClient.create("http://localhost:8082/teams");

    public Team getTeamByMemberId(Long memberId) {
        return webClient.get()
                .uri("/team-members?memberId=" + memberId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new NotFoundException("No teams found for memberId: " + memberId)))
                .bodyToMono(Team.class)
                .block();
    }


    public Team getTeamId(Long id) {
        return webClient.get()
                .uri("/"+id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new RuntimeException("No teams found"))
                )
                .bodyToMono(Team.class)
                .block();
    }
}
