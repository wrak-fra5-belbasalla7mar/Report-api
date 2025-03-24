package org.fawry.reportapi.service;


import org.fawry.reportapi.model.Cycle;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EvaluationService {

    private final RestTemplate restTemplate;

    public EvaluationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Cycle getEvaluationData(Long id) {
        String url = String.format("http://localhost:8080/cycles/%d", id);
        return restTemplate.getForObject(url, Cycle.class);
    }
}
