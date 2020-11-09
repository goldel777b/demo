package com.golden.demo.enrollment.entity;



import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=true)
public class Dependent extends AbstractPerson implements Comparable<AbstractPerson> {
	 

	@Override
	public int compareTo(AbstractPerson other) {
		//return Long.valueOf(id).compareTo(other.getId());
		return super.compareTo(other);
	}
}