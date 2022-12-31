package com.ruppyrup.app;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.paytypes.SalaryPayType;
import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.operations.requests.CreateEmployeeRequest;
import com.ruppyrup.persistance.EmployeePersister;
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
public class PayrollAppIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeePersister persister;

    @Autowired
    private EmployeeFactory employeeFactory;

    private HttpHeaders headers;
    private Employee employee;

    @BeforeEach
    void beforeEach() {
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "Bob",
                "",
                "",
                "monthly",
                50000f,
                10.0f,
                "1234",
                27
        );
        employee = employeeFactory.createEmployee(request);
        persister.save(employee);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
        headers.setAccept(mediaTypes);
    }

    @Test
    void fetchAllEmployees() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees")
                .build();

        ResponseEntity<Employee[]> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, Employee[].class);
        Employee result = response.getBody()[0];
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getName(), is("bob"));
        assertThat(result.getId(), is(0));
        assertThat(result.isUnionMember(), is(false));
        assertThat(((SalaryPayType)result.getPayType()).getSalary(), is(50000));
    }
}
