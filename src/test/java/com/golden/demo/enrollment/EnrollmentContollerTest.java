package com.golden.demo.enrollment;

import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.golden.demo.enrollment.controller.EnrollmentController;
import com.golden.demo.enrollment.controller.HomeController;
import com.golden.demo.enrollment.entity.Enrollee;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = Replace.ANY)
//@TestPropertySource(locations="classpath:application-test.properties")
public class EnrollmentContollerTest extends EnrollmentTestHelper {
	
	private final static String API = "http://localhost:%d/golden/enrollment";
	
	@LocalServerPort
	private int port;
	
    @Value("${spring.application.name}")
    String appName;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private HomeController homeController;
	
	@Autowired
	private EnrollmentController enrollmentController;


	
	@Test
	public void contextLoadHome() throws Exception {
		assertThat(homeController).isNotNull();
	}
	
	
	@Test
	public void contextLoadsEnrollment() throws Exception {
		assertThat(enrollmentController).isNotNull();
	}
	

	@Test
	public void homeDefaultMessage() throws Exception {
		String result = this.restTemplate.getForObject("http://localhost:" + port + "/golden/home", String.class);
		
		log.debug(String.format("homeDefaultMessage=%s", result));
		assertThat(result).contains(appName);
	}
	
	private void controllerCreateAndGetMessage(Enrollee person) throws Exception {
		boolean active = true;
		String  url = String.format(API, port);
		ResponseEntity<Enrollee> response = restTemplate.postForEntity(url, person, Enrollee.class);
		Enrollee personResponse  = response.getBody();
		String url2 = String.format("%s/%d", url, personResponse.getId());
		ResponseEntity<Enrollee> response2 = restTemplate.getForEntity(url2, Enrollee.class);
		Enrollee personResponse2  = response2.getBody();
		
		log.debug(String.format("RESPONSE=%s", response));
		log.debug(String.format("url2=%s RESPONSE2=%s",url2, response2));
				
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		compareEnrollees(personResponse, person);
		compareEnrollees(personResponse2, person);
	}
	
	@Test
	public void controllerCreateAndGetMessage() throws Exception {
		boolean active = true;
		Enrollee person  = createEnrollee("ControllerJane1", 0, active);
		
		controllerCreateAndGetMessage(person);
	}
	
	@Test
	public void controllerCreateAndGetWithDependent() throws Exception {
		boolean active = true;
		Enrollee person  = createEnrollee("ControllerJane2", 1, active);
		
		controllerCreateAndGetMessage(person);
	}
	
	@Test
	public void controllerCreateAndGetWithThreeDependents() throws Exception {
		boolean active = true;
		Enrollee person  = createEnrollee("ControllerJane2", 3, active);
		
		controllerCreateAndGetMessage(person);
	}
	
	
	@Test
	public void controllerCreateAndUpdate() throws Exception {
		boolean active = true;
		Enrollee person  = createEnrollee("ControllerJane2", 1, !active);
		String  url = String.format(API, port);
		ResponseEntity<Enrollee> response = restTemplate.postForEntity(url, person, Enrollee.class);
		Enrollee personResponse  = response.getBody();
		String url2 = String.format("%s/%d", url, personResponse.getId());
		ResponseEntity<Enrollee> response2 = response2 = restTemplate.getForEntity(url2, Enrollee.class);
		Enrollee personResponse2 = response2.getBody();
		

		
		log.debug(String.format("RESPONSE=%s", response));
		log.debug(String.format("RESPONSE2=%s", response2));

		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
	
		
		compareEnrollees(personResponse, person);
		
		personResponse.setLastName("Updated");
		restTemplate.put(url2, personResponse);
		
		response2 = restTemplate.getForEntity(url2, Enrollee.class);
		personResponse2  = response2.getBody();
		

		log.debug(String.format("2nd RESPONSE2=%s", response2));
		
		
		compareEnrollees(personResponse2, personResponse);
	}
	
	
	@Test
	public void controllerCreateAndDelete() throws Exception {
		boolean active = true;
		Enrollee person  = createEnrollee("ControllerJane2", 1, !active);
		String  url = String.format(API, port);
		ResponseEntity<Enrollee> response = restTemplate.postForEntity(url, person, Enrollee.class);
		Enrollee personResponse  = response.getBody();
		String url2 = String.format("%s/%d", url, personResponse.getId());
		ResponseEntity<Enrollee> response2 = response2 = restTemplate.getForEntity(url2, Enrollee.class);
		Enrollee personResponse2 = response2.getBody();
		String deleteResponse;

		
		log.debug(String.format("RESPONSE=%s", response));
		log.debug(String.format("url2=%s, RESPONSE2=%s", url2, response2));

		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
	
		
		compareEnrollees(personResponse, person);
		

		restTemplate.delete(url2);
		
		deleteResponse =  this.restTemplate.getForObject(url2, String.class);
		
		

		log.debug(String.format("url=%s, DELETE GET RESPONSE=%s", url2, deleteResponse));
		
		
		assertThat(deleteResponse).isEqualTo("Person not found");
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void controllerCreateAndGetAll() throws Exception {
		boolean active = true;
		Enrollee person1  = createEnrollee("ControllerJane1A", 0, active);
		Enrollee person2  = createEnrollee("ControllerJane1B", 1, active);
		Enrollee person3  = createEnrollee("ControllerJane1C", 2, active);
		Enrollee person4  = createEnrollee("ControllerJane1D", 3, active);
		String  url = String.format(API, port);
		ResponseEntity<List<Enrollee>> response;
		List<Enrollee> enrollees = new ArrayList<>();

		
		controllerCreateAndGetMessage(person1);
		controllerCreateAndGetMessage(person2);
		controllerCreateAndGetMessage(person3);
		controllerCreateAndGetMessage(person4);
		
		response = (ResponseEntity<List<Enrollee>>) restTemplate.getForEntity(url, enrollees.getClass());
		enrollees = response.getBody();
		
		
		log.debug(String.format("enrollees=%s",  enrollees));
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
				
		// tests run in parallel, so other tests may have not cleaned up
		assertThat(enrollees.size()).isGreaterThanOrEqualTo(4);
		
		
	}
	
	
}

