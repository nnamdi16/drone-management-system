package com.nnamdi.dronemanagementapp.service.impl;


import com.nnamdi.dronemanagementapp.dto.Data;
import com.nnamdi.dronemanagementapp.dto.DroneDto;
import com.nnamdi.dronemanagementapp.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ExternalApiService {
    private  final RestTemplate restTemplate;
//    @Value("${drone.management.api.base-url}")
//    private String baseUrl;

    public ExternalApiService( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public PageImpl<DroneDto> getDrones(int page, int limit) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("" + "/api/v1/drone")
                .queryParam("page", page)
                .queryParam("limit", limit);

        String url = uriBuilder.toUriString();
        System.out.println(url);


        ResponseEntity<Response<Data<DroneDto>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );


        if (response.getBody() != null && response.getBody().data() != null) {
            List<DroneDto> drones = response.getBody().data().getContent();
            PageRequest pageRequest = PageRequest.of(page, limit);
            return new PageImpl<>(drones, pageRequest, response.getBody().data().getTotalElements());
        } else {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, limit), 0);
        }
    }
}
