package com.golden.demo.enrollment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.golden.demo.enrollment.entity.Enrollee;
import com.golden.demo.enrollment.exception.PersonNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
@ActiveProfiles("test")
public class EnrollmentServiceTest  extends EnrollmentTestHelper {

   
	
	 
	@Test
	public void serviceCreate() throws PersonNotFoundException {
		boolean active = true;
		Enrollee dbJane = saveAndGetEnrollee("Jane", 1, !active);
		LocalDate localDate = LocalDate.now().minusYears(10 * 1);
	    
		log.debug(String.format("serivceCreate=%s",  dbJane));
		assertThat(dbJane.getFirstName()).isEqualTo( "FirstJane1");
		assertThat(dbJane.getMiddleName()).isEqualTo("MiddleJane1");
		assertThat(dbJane.getLastName()).isEqualTo("LastJane1");
		
		assertThat(dbJane.isActive()).isEqualTo(!active);
		assertThat(dbJane.getBirthYear()).isEqualTo(localDate.getYear());
		assertThat(dbJane.getBirthMonth()).isEqualTo(localDate.getMonthValue());
		assertThat(dbJane.getBirthDayOfMonth()).isEqualTo(localDate.getDayOfMonth());
		assertThat(dbJane.getPhone()).isEqualTo("Phone-1");	
    
	    assertThat(0).isEqualTo(dbJane.getDbVersion());	  
	}

	
	@Test
	public void serviceCreateAndUpdate() throws PersonNotFoundException {
		boolean active = true;
		Enrollee dbJane = saveAndGetEnrollee("Jane", 1, !active);
		
		updateLastName(dbJane, "Smith", 1);
		updateLastName(dbJane, "SmithAgain", 2);
		updateLastName(dbJane, "Jones", 3);  
	}
	
	
	@Test
	public void serviceCreateDelete() throws PersonNotFoundException {
		boolean active = true;
		Enrollee dbJane = saveAndGetEnrollee("Jane", 2, !active);
	   
	    enrollmentService.delete(dbJane.getId()); 	
	    
	    try {
	    	enrollmentService.findById(dbJane.getId()); 
	    	fail();
	    }
	    catch (PersonNotFoundException e) {
	    	assertTrue(true, "serviceCreateDelete");
	    }
	    
	}
	

	
	@Test
	public void serviceCreateAndGet() throws PersonNotFoundException {
	    // given
		boolean active = true;
		List<Enrollee> personsList = new ArrayList<>();
		
		saveAndGetEnrollee("Jane", 0, !active);
		saveAndGetEnrollee("Dick", 1, !active);
		saveAndGetEnrollee("John", 2, active);
	   
	    
	    Iterable<Enrollee> persons = enrollmentService.all();
	    persons.forEach(p -> personsList.add(p));
	    
	    assertThat(personsList.size()).isEqualTo(3);
	  
	}
	
	
}

