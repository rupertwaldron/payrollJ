package com.ruppyrup.app;


import com.ruppyrup.core.models.Employee;
import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.operations.requests.EmployeeDTO;
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

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);
        String result = response.getBody();
        String expected = """
                {
                       "name" : Bob,
                       "paySchedule" : MonthlyPaySchedule,
                       "payType" : SalaryPayType{salary=50000.0},
                       "paymentDetails" : BankPayMethod {accountNumber='1234', lastInstruction='null'},
                       "unionMember" : false
                }
                {
                       "name" : Ted,
                       "paySchedule" : WeeklyPaySchedule,
                       "payType" : HourlyPayType{weeklyHours=0, hourlyRate=20.0},
                       "paymentDetails" : BankPayMethod {accountNumber='99944', lastInstruction='null'},
                       "unionMember" : false
                }
                """;
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(result, is(expected));
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

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);
        String result = response.getBody();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        String expected = """
                {
                       "name" : Bob,
                       "paySchedule" : MonthlyPaySchedule,
                       "payType" : SalaryPayType{salary=50000.0},
                       "paymentDetails" : BankPayMethod {accountNumber='1234', lastInstruction='null'},
                       "unionMember" : false
                }
                """;
        assertThat(result, is(expected));
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

        Employee result = persister.getAll().stream()
                .filter(dto -> "Fred".equals(dto.getName()))
                .findFirst()
                .get();

        String expected = """
                {
                       "name" : Fred,
                       "paySchedule" : WeeklyPaySchedule,
                       "payType" : HourlyPayType{weeklyHours=0, hourlyRate=10.0},
                       "paymentDetails" : BankPayMethod {accountNumber='557788', lastInstruction='null'},
                       "unionMember" : false
                }
                """;
        assertThat(result.getEmployeeInfo(), is(expected));
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


        String result = persister.get(1).getEmployeeInfo();

        String expected = """
                {
                       "name" : Ted,
                       "paySchedule" : WeeklyPaySchedule,
                       "payType" : HourlyPayType{weeklyHours=27, hourlyRate=20.0},
                       "paymentDetails" : BankPayMethod {accountNumber='99944', lastInstruction='null'},
                       "unionMember" : false
                }
                """;

        assertThat(result, is(expected));
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

        String result = persister.get(0).getEmployeeInfo();

        String expected = """
                {
                       "name" : Bob,
                       "paySchedule" : MonthlyPaySchedule,
                       "payType" : SalaryPayType{salary=50000.0},
                       "paymentDetails" : BankPayMethod {accountNumber='1234', lastInstruction='Bob has been paid $4166.6665 into account number :: 1234'},
                       "unionMember" : false
                }
                """;
        assertThat(result, is(expected));
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

        String result = persister.get(0).getEmployeeInfo();


        String expected = """
                {
                       "name" : Bob,
                       "paySchedule" : MonthlyPaySchedule,
                       "payType" : SalaryPayType{salary=50000.0},
                       "paymentDetails" : BankPayMethod {accountNumber='1234', lastInstruction='Bob has been paid $4166.6665 into account number :: 1234'},
                       "unionMember" : false
                }
                """;
        assertThat(result, is(expected));

        restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);

        result = persister.get(0).getEmployeeInfo();


        expected = """
                {
                       "name" : Bob,
                       "paySchedule" : MonthlyPaySchedule,
                       "payType" : SalaryPayType{salary=50000.0},
                       "paymentDetails" : BankPayMethod {accountNumber='1234', lastInstruction='Bob has been paid $4166.6665 into account number :: 1234'},
                       "unionMember" : false
                }
                """;
        assertThat(result, is(expected));
    }
}
