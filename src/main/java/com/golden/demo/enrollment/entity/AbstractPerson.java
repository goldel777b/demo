package com.golden.demo.enrollment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonView;
import com.golden.demo.enrollment.dto.DependentDTO;
import com.golden.demo.enrollment.dto.EnrolleeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractPerson implements Comparable<AbstractPerson> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private long id;
    
    @Column(nullable = false)
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private  String firstName; 
    
    @Column(nullable = false)
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private  String middleName; 
    
    @Column(nullable = false)
   // @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private  String lastName; 
    
    @Column(nullable = false)
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private int birthYear;
    
    @Column(nullable = false)
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private int birthMonth;
    
    @Column(nullable = false)
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    private int birthDayOfMonth;
    
    @Column(nullable = false)	 
    //@Version 
    @JsonView(value = { EnrolleeDTO.class, DependentDTO.class })
    protected long dbVersion;
    
	@Override
	public int compareTo(AbstractPerson other) {
		return Long.valueOf(id).compareTo(other.getId());
	}

}
