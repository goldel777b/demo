package com.golden.demo.enrollment.entity;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;
import com.golden.demo.enrollment.dto.EnrolleeDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=true)
public class Enrollee extends AbstractPerson implements Comparable<AbstractPerson> {

    
    @Column(nullable = true)
    @JsonView(value = EnrolleeDTO.class)
    private String phone;
    
    @Column(nullable = true)
    @JsonView(value = EnrolleeDTO.class)
    private boolean active;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(value = EnrolleeDTO.class)
    private Set<Dependent> dependents;
    
    
	@Override
	public int compareTo(AbstractPerson other) {
		return super.compareTo(other);
	}
    
//   public static void main(String... args) {
//	    ObjectMapper mapper = new ObjectMapper();
//	    Enrollee enrollee = new Enrollee();
//	    Dependent dependent = new Dependent();
//	    Dependent dependent2 = new Dependent();
//	    Set<Dependent> dependents = new HashSet<>();
//	    
//	    enrollee.setActive(true);
//      enrollee.setBirthYear(1980);
//      enrollee.setBirthMonth(Month.JANUARY);
//      enrollee.setBirthDayOfMonth(10);
//	    enrollee.setDbVersion(1);
//	    enrollee.setFirstName("Mary");
//	    enrollee.setMiddleName("Jane");
//	    enrollee.setLastName("Doe");
//	    enrollee.setId(1000);;
//	    enrollee.setPhone("919-555-1212");
//	    enrollee.setDependents(dependents);
//	    
//	    
//	    dependent.setBirthYear(2000);
//      dependent.setBirthMonth(Month.JANUARY);
//      dependent.setBirthDayOfMonth(11);
//	    dependent.setDbVersion(1);
//	    dependent.setFirstName("Baby");
//	    dependent.setMiddleName("Jane");
//	    dependent.setLastName("Doe");
//	    dependent.setId(1001);
//	    
//	    
//      dependent.setBirthYear(1981);
//      dependent.setBirthMonth(Month.JANUARY);
//      dependent.setBirthDayOfMonth(12);
//	    dependent2.setDbVersion(1);
//	    dependent2.setFirstName("Husband");
//	    dependent2.setMiddleName("Charlie");
//	    dependent2.setLastName("Doe");
//	    dependent2.setId(1002);
//	    
//	    dependents.add(dependent);
//	    dependents.add(dependent2);
//	    
//	    
//	    try {
//	      String json = mapper.writeValueAsString(enrollee);
//	      System.out.println("ResultingJSONstring = " + json);
//	      //System.out.println(json);
//	    } catch (Exception e) {
//	       e.printStackTrace();
//	    }
//   }
//    
   
}