package com.ruppyrup.app;


import com.ruppyrup.core.models.Employee;
import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.operations.requests.EmployeeDTO;
import com.ruppyrup.operations.utilities.EmployeeConverter;
import com.ruppyrup.persistance.EmployeePersister;
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
public class OperationsControllerIntegrationTest {

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
                "bank",
                "monthly",
                "salary",
                50000f,
                "1234",
                27,
                "",
                false
        );
        EmployeeDTO dto2 = new EmployeeDTO(
                "Ted",
                "bank",
                "weekly",
                "hourlyRate",
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
    void fetchAllEmployees() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees")
                .build();

        ResponseEntity<EmployeeDTO[]> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, EmployeeDTO[].class);
        EmployeeDTO result = response.getBody()[0];
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.name(), is("Bob"));
        assertThat(result.pay(), is(50000.0F));
        assertThat(result.paySchedule(), is("MonthlyPaySchedule"));
        assertThat(result.accountNumber(), is("1234"));
        assertThat(result.payMethod(), is("BankPayMethod"));
        assertThat(result.payType(), is("SalaryPayType"));
    }

    @Test
    void fetchOneEmployees() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees/0")
                .build();

        ResponseEntity<EmployeeDTO> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, EmployeeDTO.class);
        EmployeeDTO result = response.getBody();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.name(), is("Bob"));
        assertThat(result.pay(), is(50000.0F));
        assertThat(result.paySchedule(), is("MonthlyPaySchedule"));
        assertThat(result.accountNumber(), is("1234"));
        assertThat(result.payMethod(), is("BankPayMethod"));
        assertThat(result.payType(), is("SalaryPayType"));
    }

    @Test
    void saveEmployee() {
        EmployeeDTO request = new EmployeeDTO(
                "Fred",
                "bank",
                "weekly",
                "hourlyRate",
                10.0f,
                "557788",
                40,
                "",
                false
        );
        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(request, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees")
                .build();

        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity, EmployeeDTO.class);

        EmployeeDTO result = persister.getAll().stream()
                .map(EmployeeConverter::fromEmployee)
                .filter(dto -> "Fred".equals(dto.name()))
                .findFirst()
                .get();

        assertThat(result.name(), is("Fred"));
        assertThat(result.pay(), is(10.0F));
        assertThat(result.paySchedule(), is("WeeklyPaySchedule"));
        assertThat(result.accountNumber(), is("557788"));
        assertThat(result.payMethod(), is("BankPayMethod"));
        assertThat(result.payType(), is("HourlyPayType"));
    }

    @Test
    void updateEmployee() {
        EmployeeDTO request = new EmployeeDTO(
                "",
                "",
                "",
                "",
                0,
                "0",
                27,
                "",
                false
        );

        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(request, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees/1")
                .build();

        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, entity, EmployeeDTO.class);

        EmployeeDTO result = EmployeeConverter.fromEmployee(persister.get(1));

        assertThat(result.name(), is("Ted"));
        assertThat(result.pay(), is(20.0F));
        assertThat(result.paySchedule(), is("WeeklyPaySchedule"));
        assertThat(result.accountNumber(), is("99944"));
        assertThat(result.payMethod(), is("BankPayMethod"));
        assertThat(result.payType(), is("HourlyPayType"));
        assertThat(result.weeklyHours(), is(27));
    }

    @Test
    void payEmployees() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees/paynow")
                .build();


        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);

        EmployeeDTO result = EmployeeConverter.fromEmployee(persister.get(0));

        assertThat(result.name(), is("Bob"));
        assertThat(result.lastInstruction(), is("Bob has been paid $4166.6665 into account number :: 1234"));
    }

    @Test
    void payEmployeesTwice() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("employees/paynow")
                .build();


        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);

        EmployeeDTO result = EmployeeConverter.fromEmployee(persister.get(0));


        assertThat(result.name(), is("Bob"));
        assertThat(result.lastInstruction(), is("Bob has been paid $4166.6665 into account number :: 1234"));

        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);

        result = EmployeeConverter.fromEmployee(persister.get(0));


        assertThat(result.name(), is("Bob"));
        assertThat(result.lastInstruction(), is("Bob has been paid $4166.6665 into account number :: 1234"));
    }
}
