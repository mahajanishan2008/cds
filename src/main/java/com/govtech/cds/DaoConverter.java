package com.govtech.cds;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.govtech.cds.domains.User;
import com.govtech.cds.repository.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DaoConverter {

	public List<User> toDomain(List<UserEntity> userEntityList) {

		log.debug("Converting userEntity into user");
		return userEntityList.stream().map(userEntity -> transformToUser(userEntity)).collect(Collectors.toList());
	}

	public User transformToUser(UserEntity userEntity) {
		return new User(String.valueOf(userEntity.getId()),
				userEntity.getName(), String.valueOf(userEntity.getSalary()));
	}

	public List<UserEntity> toEntity(List<User> users) {
		log.debug("Converting users into userEntityList");
		return users.stream().map(
				user -> new UserEntity(Long.valueOf(user.getId()), user.getName(), Double.valueOf(user.getSalary())))
				.collect(toList());
	}

}
