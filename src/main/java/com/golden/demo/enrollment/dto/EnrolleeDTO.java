package com.golden.demo.enrollment.dto;

import java.util.Set;

import com.golden.demo.enrollment.entity.Dependent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString(callSuper=true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=true)
// fields for junit testing
public class EnrolleeDTO extends AbstractPersonDTO implements Comparable<AbstractPersonDTO> {

    private String phone;

    private boolean active;
    
    private Set<Dependent> dependents;
    
    
	@Override
	public int compareTo(AbstractPersonDTO other) {;
		return super.compareTo(other);
	}
}