package org.fawry.reportapi.service;


import org.fawry.reportapi.exception.NotFoundException;
import org.fawry.reportapi.model.Cycle;
import org.fawry.reportapi.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class EvaluationService {

    private final RestTemplate restTemplate;

    public EvaluationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Cycle getCycleData(Long id) {
        String url = String.format("http://localhost:8083/cycles/%d", id);
        return restTemplate.getForObject(url, Cycle.class);
    }

    public Map<Long, Double> avgRating(Long id) {
        String url = String.format("http://localhost:8083/ratings/cycle/rate/%d", id);
        ResponseEntity<Map<Long, Double>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<Long, Double>>() {}
        );
        return response.getBody();
    }

    private final WebClient webClient = WebClient.create("http://localhost:8080/employee");
    public User getUserById(Long id) {
        User user = webClient.get()
                .uri("/find?id=" + id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        response.createException().flatMap(error ->
                                Mono.error(new NotFoundException("User not found with id: " + id))
                        )
                )
                .bodyToMono(User.class)
                .block();
        if (user == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        return user;
    }

}
