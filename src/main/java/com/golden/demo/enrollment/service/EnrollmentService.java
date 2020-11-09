package com.golden.demo.enrollment.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import com.golden.demo.enrollment.entity.Enrollee;
import com.golden.demo.enrollment.repo.EnrollmentRepo;
import com.golden.demo.exception.PersonNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@Slf4j
public class EnrollmentService {
	
    @Autowired
	private EnrollmentRepo enrollmentRepo;
	
    public Iterable<Enrollee> all() {
        return enrollmentRepo.findAll();
    }
    
    public Enrollee findById(long id) throws PersonNotFoundException {
        return enrollmentRepo.findById(id).orElseThrow(PersonNotFoundException::new);
    }


    public Enrollee create(@RequestBody Enrollee person) {   	
    	//validate(person); 
        return enrollmentRepo.save(person);
    }
 
    public void delete(long id) throws PersonNotFoundException {    	  	
    	enrollmentRepo.deleteById(id);
    }
    

    
    @Transactional(propagation=Propagation.REQUIRED)
    public Enrollee update(@RequestBody Enrollee person, long id) throws PersonNotFoundException {
        Enrollee dbPerson = enrollmentRepo.findById(id).orElseThrow(PersonNotFoundException::new);
        
        if (dbPerson.getDbVersion() != person.getDbVersion()  || id != dbPerson.getId() ) {
        	throw new IllegalStateException(String.format("%id is not up-to-date", id));
        }   

        // TBD create trigger
        person.setDbVersion(person.getDbVersion() + 1);
        log.debug(String.format("updating %s", person));
        return enrollmentRepo.save(person);
    }

	
}
