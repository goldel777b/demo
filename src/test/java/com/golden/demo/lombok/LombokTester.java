package com.golden.demo.lombok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;



public class LombokTester {
	 
    @Test
    public void givenAnnotatedUser_thenHasGettersAndSetters() {
        User user = new User();
        user.setFirstName("Test");
        assertEquals(user.getFirstName(), "Test");
    }
 
    @Getter @Setter
    class User {
        private String firstName;
    }
}
