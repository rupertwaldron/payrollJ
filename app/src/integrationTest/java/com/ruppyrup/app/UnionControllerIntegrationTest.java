package com.ruppyrup.app;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.operations.requests.EmployeeDTO;
import com.ruppyrup.operations.utilities.EmployeeConverter;
import com.ruppyrup.persistance.EmployeePersister;
import com.ruppyrup.union.requests.UnionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeePersister persister;

    @Autowired
    private EmployeeFactory employeeFactory;

    private HttpHeaders headers;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void beforeEach() {
        EmployeeDTO dto1 = new EmployeeDTO(
                "Bob",
                "",
                "",
                "monthly",
                50000f,
                "1234",
                27,
                "",
                false
        );
        EmployeeDTO dto2 = new EmployeeDTO(
                "Ted",
                "",
                "",
                "weekly",
                20.0f,
                "99944",
                11,
                "",
                false
        );
        employee1 = employeeFactory.createEmployee(dto1);
        employee2 = employeeFactory.createEmployee(dto2);
        persister.save(employee1);
        persister.save(employee2);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
        headers.setAccept(mediaTypes);
    }

    @AfterEach
    void clearUp() {
        persister.clearAll();
    }

    @Test
    void updateUnionMembership() {
        UnionRequest unionRequest = new UnionRequest(true);
        HttpEntity<UnionRequest> entity = new HttpEntity<>(unionRequest, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("union/1")
                .build();

        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, entity, String.class);

        EmployeeDTO result = EmployeeConverter.fromEmployee(persister.get(1));

        assertThat(result.name(), is("Ted"));
        assertThat(result.pay(), is(20.0F));
        assertThat(result.paySchedule(), is("WeeklyPaySchedule"));
        assertThat(result.accountNumber(), is("99944"));
        assertThat(result.payMethod(), is("BankPayMethod"));
        assertThat(result.payType(), is("HourlyPayType"));
        assertThat(result.isUnionMember(), is(true));

    }
}
