package com.govtech.cds.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.govtech.cds.DaoConverter;
import com.govtech.cds.domains.User;
import com.govtech.cds.exception.RecordNotFoundException;
import com.govtech.cds.repository.UserEntity;
import com.govtech.cds.repository.UserRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private DaoConverter daoConverter;

	@Override
	public List<User> getAllUsers() {
		List<UserEntity> userList = (List<UserEntity>) repository.findAll();
		if (userList.size() > 0) {
			return daoConverter.toDomain(userList);
		} else {
			return new ArrayList<User>();
		}
	}

	@Override
	public boolean saveToDatabase(MultipartFile file) {
		try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

			// create csv bean reader
			CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader).withType(User.class)
					.withIgnoreLeadingWhiteSpace(true).build();

			// convert `CsvToBean` object to list of users
			List<User> users = csvToBean.parse();

			List<UserEntity> userEntityList = daoConverter.toEntity(users);
			repository.saveAll(userEntityList);

		} catch (Exception ex) {
			throw new RuntimeException("Exception occured while saving record to db. :" + ex.getMessage());
		}

		return true;
	}

	@Override
	public User getUser(Long id) throws RecordNotFoundException {
		Optional<UserEntity> user = repository.findById(id);
		if (user.isPresent()) {
			return daoConverter.transformToUser(user.get());
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}

	@Override
	public List<User> getUsersFilteredBySalary(Double minSalary, Double maxSalary) {
		List<UserEntity> userList = repository.findAllBySalaryBetween(minSalary, maxSalary);
		if (userList.size() > 0) {
			return daoConverter.toDomain(userList);
		} else {
			return new ArrayList<User>();
		}
	}

}