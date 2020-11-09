package com.golden.demo.enrollment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=true)
//fields needed for junit
public class DependentDTO extends AbstractPersonDTO implements Comparable<AbstractPersonDTO> {

	@Override
	public int compareTo(AbstractPersonDTO other) {
		//return Long.valueOf(id).compareTo(other.getId());
		return super.compareTo(other);
	}
}

	