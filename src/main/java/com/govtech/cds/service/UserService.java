package com.govtech.cds.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.govtech.cds.domains.User;
import com.govtech.cds.exception.RecordNotFoundException;

public interface UserService {

	List<User> getAllUsers();

	boolean saveToDatabase(MultipartFile file);

	User getUser(Long id) throws RecordNotFoundException;

	List<User> getUsersFilteredBySalary(Double minSalary, Double maxSalary);

}
