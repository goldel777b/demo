package com.golden.demo.enrollment;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.golden.demo.enrollment.entity.Enrollee;
import com.golden.demo.enrollment.repo.EnrollmentRepo;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
@ActiveProfiles("test")
public class EnrollmentRepoTest extends EnrollmentTestHelper {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EnrollmentRepo enrolleeRepository;
    
	
	@Test
	public void repoTest() {
	    // given
		boolean active = false;
	    Enrollee jane = createEnrollee("Doe", 10, active);
	    
	    entityManager.persist(jane);
	    entityManager.flush();

	    Optional<Enrollee> searchLori = enrolleeRepository.findById(jane.getId());
	 
	    assertThat(searchLori.get().getLastName()).isEqualTo(jane.getLastName());
	}
	 

}

