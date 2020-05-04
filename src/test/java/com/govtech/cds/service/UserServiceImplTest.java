package com.govtech.cds.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.govtech.cds.DaoConverter;
import com.govtech.cds.domains.User;
import com.govtech.cds.exception.RecordNotFoundException;
import com.govtech.cds.repository.UserEntity;
import com.govtech.cds.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepo;
	@Mock
	private DaoConverter daoConverter;
	@InjectMocks
	private static UserService userService;

	@BeforeClass
	public static void setUp() {
		userService = new UserServiceImpl();
	}

	@Test
	public void shouldGetAllUsers() {
		Mockito.when(userRepo.findAll()).thenReturn(Arrays.asList(new UserEntity(1L, "Ishan", 10000.0)));
		Mockito.when(daoConverter.toDomain(anyList())).thenCallRealMethod();
		List<User> users = userService.getAllUsers();
		assertEquals(users.size(), 1);

	}

	@Test(expected=RuntimeException.class)
	public void shouldNotSaveToDatabase() {
		userService.saveToDatabase(null);
	}

	@Test
	public void shouldGetUsersFilteredBySalary() {
		Mockito.when(userRepo.findAllBySalaryBetween(1000.0, 2000.0)).thenReturn(Arrays.asList(new UserEntity(1L, "Ishan", 1200.0)));
		Mockito.when(daoConverter.toDomain(Mockito.anyList())).thenCallRealMethod();
		List<User> users = userService.getUsersFilteredBySalary(1000.0, 2000.0);
		assertEquals(users.size(), 1);

	}

	@Test
	public void shouldGetUserById() throws RecordNotFoundException {
		Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(new UserEntity(1L, "Ishan", 1200.0)));
		Mockito.when(daoConverter.transformToUser(Mockito.any())).thenCallRealMethod();
		User user = userService.getUser(1L);
		assertEquals(user.getId(), "1");

	}

	@Test(expected=RecordNotFoundException.class)
	public void shouldThrowExceptionMissingUser() throws RecordNotFoundException {
		Mockito.when(userRepo.findById(1L)).thenReturn(Optional.empty());
		Mockito.when(daoConverter.transformToUser(Mockito.any())).thenCallRealMethod();
		userService.getUser(1L);

	}

}
