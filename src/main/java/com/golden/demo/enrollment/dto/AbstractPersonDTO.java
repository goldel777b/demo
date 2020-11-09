package com.golden.demo.enrollment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// fields are needed for junit tests
public class AbstractPersonDTO implements Comparable<AbstractPersonDTO> {

    private long id;
    
    private  String firstName; 

    private  String middleName; 
    
    private  String lastName; 
    
    private int birthYear;
    
    private int birthMonth;
    
    private int birthDayOfMonth;

    protected long dbVersion;
    
	@Override
	public int compareTo(AbstractPersonDTO other) {
		return Long.valueOf(id).compareTo(other.getId());
	}
}
