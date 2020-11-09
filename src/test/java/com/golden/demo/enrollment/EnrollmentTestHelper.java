package com.golden.demo.enrollment;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.golden.demo.enrollment.entity.AbstractPerson;
import com.golden.demo.enrollment.entity.Dependent;
import com.golden.demo.enrollment.entity.Enrollee;
import com.golden.demo.enrollment.exception.PersonNotFoundException;
import com.golden.demo.enrollment.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class EnrollmentTestHelper {
	
    

    @Autowired
    protected EnrollmentService enrollmentService;

    
    protected Enrollee createEnrollee(String name, int num, boolean status) {
    	Enrollee enrollee = new Enrollee();
    	Set<Dependent> dependents = new HashSet<>();
    	
    	initializePerson(enrollee, name, num);
    	
    	
    	for (int i = 0; i < num; i++) {
    		dependents.add(createDependent(name, i));
    	}
    	enrollee.setDependents(dependents);
    	enrollee.setPhone(String.format("Phone-%d", num));
    	enrollee.setActive(status);
        return enrollee;
    }
    
    protected Dependent createDependent(String name, int num) {
    	Dependent dependent = new Dependent();
    	
    	initializePerson(dependent, String.format("Son%s", name), num);
        return dependent;
    }
    
    // TBD FIX Repeated code
    protected void initializePerson(AbstractPerson person, String name, int num) {
       	LocalDate localDate = LocalDate.now().minusYears(10 * num);
        
       	person.setFirstName(String.format("First%s%d", name, num));
    	person.setMiddleName(String.format("Middle%s%d", name, num));
    	person.setLastName(String.format("Last%s%d", name, num));
    	person.setBirthYear(localDate.getYear());
    	person.setBirthMonth(localDate.getMonthValue());
    	person.setBirthDayOfMonth(localDate.getDayOfMonth());
    }
    
	protected Enrollee saveAndGetEnrollee(String name, int num, boolean active) throws PersonNotFoundException {
		Enrollee person  = createEnrollee(name, num, active);
		
	    enrollmentService.create(person);
	    Enrollee dbPerson = enrollmentService.findById(person.getId());
	    assertThat(dbPerson.getLastName()).isEqualTo(person.getLastName());
		
	    log.debug(String.format("saveAndGetEnrollee dbPerson=%s", dbPerson));
	    return dbPerson;
	}
	
	protected void updateLastName(Enrollee enrollee, String lastName, int dbVersion) throws PersonNotFoundException {
		enrollee.setLastName(lastName);
	    
	    log.debug(String.format("updatedEnrollee=%s", enrollee));
	    
	    Enrollee updatedEnrollee = enrollmentService.update(enrollee, enrollee.getId());
	    
	    log.debug(String.format("updatedEnrollee=%s", enrollee));
	    
	    assertThat(dbVersion).isEqualTo(updatedEnrollee.getDbVersion());
	    
	    assertThat(updatedEnrollee.getLastName()).isEqualTo(lastName); 
	   
	}
	
	// this test relies on unique name as ids are not set on origional
	private Dependent findDependentInSet(Set<Dependent> dependents, Dependent dependent) {
		//assertThat(dependents.contains(dependent)).isEqualTo(true);
		
		for (Dependent setDependent:dependents) {
			if (setDependent.getFirstName().equals(dependent.getFirstName())  &&
				setDependent.getMiddleName().equals(dependent.getMiddleName()) &&
				setDependent.getLastName().equals(dependent.getLastName())) {
				return setDependent;
			}
		}
		throw new IllegalStateException();
	}
	
	// ids on origin may not be set
	protected void compareEnrollees(Enrollee person1, Enrollee person2) {

	
		assertThat(person1.getFirstName()).isEqualTo( person2.getFirstName());
		assertThat(person1.getMiddleName()).isEqualTo( person2.getMiddleName());
		assertThat(person1.getLastName()).isEqualTo( person2.getLastName());
		
		assertThat(person1.isActive()).isEqualTo(person2.isActive());
		assertThat(person1.getBirthYear()).isEqualTo(person2.getBirthYear());
		assertThat(person1.getBirthMonth()).isEqualTo(person2.getBirthMonth());
		assertThat(person1.getBirthDayOfMonth()).isEqualTo(person2.getBirthDayOfMonth());
		assertThat(person1.getPhone()).isEqualTo(person2.getPhone());	
		
		assertThat(person1.getDependents().size()).isEqualTo(person2.getDependents().size());
		
		//dependents is as set
		Set<Dependent> dependents2 = person2.getDependents();
		
		log.debug(String.format("dependents1::=%s", person1.getDependents()));
		log.debug(String.format("dependents2::=%s", person2.getDependents()));
		
		for (Dependent dependent1: person1.getDependents()) {			
			Dependent dependent2 = findDependentInSet(dependents2, dependent1);
			
			assertThat(dependent2.getFirstName()).isEqualTo( dependent1.getFirstName());
			assertThat(dependent2.getMiddleName()).isEqualTo( dependent1.getMiddleName());
			assertThat(dependent2.getLastName()).isEqualTo( dependent1.getLastName());				
		}
	}
	

}
