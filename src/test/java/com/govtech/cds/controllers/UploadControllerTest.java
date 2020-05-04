package com.govtech.cds.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.govtech.cds.domains.User;
import com.govtech.cds.exception.RecordNotFoundException;
import com.govtech.cds.service.UserService;


@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest {

	@Mock
	private UserService userService;
	@InjectMocks
	private static UploadController controller;

	@BeforeClass
	public static void setUp() {
		controller = new UploadController();
	}

	@Test
	public void shouldGetAllUsers() {
		Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(new User("1", "Ishan", "10000")));
		ResponseEntity<List<User>> users = controller.users();
		assertEquals(users.getBody().size(), 1);

	}
	@Test
	public void shouldGetUserById() throws RecordNotFoundException {
		Mockito.when(userService.getUser(1L)).thenReturn(new User("1", "Ishan", "10000"));
		ResponseEntity<User> user = controller.user(1L);
		assertEquals(user.getBody().getId(), "1");

	}
	@Test
	public void shouldGetUserBySalaryRange() throws RecordNotFoundException {
		Mockito.when(userService.getUsersFilteredBySalary(1000.0,3000.0)).thenReturn(Arrays.asList(new User("1", "Ishan", "1000.0")));
		ResponseEntity<List<User>> user = controller.usersBySalary(1000.0,3000.0);
		assertEquals(user.getBody().size(), 1);

	}

	@Test
	public void shouldNotUploadCSV() throws RecordNotFoundException {
		Mockito.when(userService.saveToDatabase(Mockito.any())).thenReturn(true);
		String path = controller.uploadCSVFile(null);
		assertEquals(path, "error-page");

	}

	@Test(expected=RecordNotFoundException.class)
	public void shouldThrowExceptionIfUserAbsent() throws RecordNotFoundException {
		Mockito.when(userService.getUser(1L)).thenThrow(new RecordNotFoundException("No Record."));
		ResponseEntity<User> user = controller.user(1L);
		assertEquals(user.getBody().getId(), 1);

	}

}
