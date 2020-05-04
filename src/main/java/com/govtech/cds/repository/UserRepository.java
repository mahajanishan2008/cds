package com.govtech.cds.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	List<UserEntity> findAllBySalaryBetween(Double salaryFrom, Double salaryTo);

}
