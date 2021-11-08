package com.legiz.terasoftproject.test.step.userProfile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legiz.terasoftproject.userProfile.resource.CreateLawyerResource;
import com.legiz.terasoftproject.userProfile.resource.LawyerResource;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;


public class LawyerAddingStepDefinitions {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String endpointPath;
    private ResponseEntity<String> responseEntity;

    @Given("The Endpoint {string} is available")
    public void theEndpointIsAvailable(String endPointPath) {
        this.endpointPath = String.format(endPointPath, randomServerPort);
    }

    @When("A Lawyer Request is sent with values {string}, {string}, {string}")
    public void aLawyerRequestIsSentWithValues(String userName, String password, String lawyerName) {
        CreateLawyerResource resource = new CreateLawyerResource()
                .withUserName(userName)
                .withPassword(password)
                .withLawyerName(lawyerName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<CreateLawyerResource> request = new HttpEntity<>(resource, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

    @Then("A Response with Status {int} is received")
    public void aResponseIsReceivedWithStatus(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @And("A Lawyer Resource with values {string}, {string}, {string} is included in Response Body")
    public void aLawyerResourceIsIncludedInResponseBodyWithValues(String userName, String password, String lawyerName) {
        LawyerResource expectedResource = new LawyerResource()
                .withUserName(userName)
                .withPassword(password)
                .withLawyerName(lawyerName);
        String value = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        LawyerResource actualResource;
        try {
            actualResource = mapper.readValue(value, LawyerResource.class);
        } catch (JsonProcessingException | NullPointerException e) {
            actualResource = new LawyerResource();
        }
        expectedResource.setId(actualResource.getId());
        assertThat(expectedResource).usingRecursiveComparison()
                .isEqualTo(actualResource);

    }
}
