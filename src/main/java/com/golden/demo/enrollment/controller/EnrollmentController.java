package com.golden.demo.enrollment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golden.demo.enrollment.dto.EnrolleeDTO;
import com.golden.demo.enrollment.entity.Enrollee;
import com.golden.demo.enrollment.exception.PersonNotFoundException;
import com.golden.demo.enrollment.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;



// not production ready - don't give direct access to database
// authentication/auth also missing
@RestController
@RequestMapping("/enrollment")
@Slf4j
public class EnrollmentController  {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    
	@GetMapping("")
    @JsonView(EnrolleeDTO.class)
    public List<Enrollee> findAll() {
    	List<Enrollee> enrollees = new ArrayList<>();
    	
    	enrollmentService.all().forEach(e -> enrollees.add(e));
        return enrollees;
    }
    
	@GetMapping("/{id}")
    @JsonView(EnrolleeDTO.class)
    public Enrollee get(@PathVariable Long id) throws PersonNotFoundException, JsonProcessingException {
        Enrollee enrollee = enrollmentService.findById(id);
        
        log.debug(String.format("EnrollmentController::get=%d %s", id, enrollee));
        
        return enrollee;
    }


	@PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(EnrolleeDTO.class)
    public Enrollee create(@RequestBody Enrollee person) {
        return enrollmentService.create(person);
    }

	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws PersonNotFoundException {
    	enrollmentService.delete(id);
    }
 

	@PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(EnrolleeDTO.class)
    public void update(@RequestBody Enrollee person, @PathVariable Long id) throws PersonNotFoundException {

        enrollmentService.update(person, id);
    }
}