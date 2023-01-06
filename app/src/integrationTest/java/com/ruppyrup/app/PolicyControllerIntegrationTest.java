package com.ruppyrup.app;

import com.ruppyrup.corepolicy.schedulepolicies.SchedulePolicy;
import com.ruppyrup.policy.requests.PolicyRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SchedulePolicy monthlySchedulePolicy;

    @Autowired
    private SchedulePolicy weeklySchedulePolicy;

    private HttpHeaders headers;

    @BeforeEach
    void beforeEach() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
        headers.setAccept(mediaTypes);
    }

    @AfterEach
    void clearUp() {
        weeklySchedulePolicy.setPolicyData(7);
        monthlySchedulePolicy.setPolicyData(31);
    }


    @Test
    void getCurrentSchedulePolicies() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("policy/schedule")
                .build();

        ResponseEntity<PolicyRequest> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, PolicyRequest.class);
        PolicyRequest result = response.getBody();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.monthlySchedule(), is(31));
        assertThat(result.weeklySchedule(), is(7));
    }

    @Test
    void updateSchedulePolicy() {
        PolicyRequest policyRequest = new PolicyRequest(32, 8);
        HttpEntity<PolicyRequest> entity = new HttpEntity<>(policyRequest, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("policy/schedule")
                .build();

        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, entity, String.class);

        assertThat(monthlySchedulePolicy.fetchPolicyData(), is(32));
        assertThat(weeklySchedulePolicy.fetchPolicyData(), is(8));

    }
}
