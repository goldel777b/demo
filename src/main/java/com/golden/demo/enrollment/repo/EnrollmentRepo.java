package com.golden.demo.enrollment.repo;

import org.springframework.data.repository.CrudRepository;

import com.golden.demo.enrollment.entity.Enrollee;

public interface EnrollmentRepo extends CrudRepository<Enrollee, Long> {
}