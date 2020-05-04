package com.govtech.cds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.govtech.cds.domains.User;
import com.govtech.cds.exception.RecordNotFoundException;
import com.govtech.cds.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> users() {
		List<User> list = userService.getAllUsers();

		return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> user(@PathVariable Long id) throws RecordNotFoundException {
		User user = userService.getUser(id);

		return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/users/salary")
	public ResponseEntity<List<User>> usersBySalary(@RequestParam Double minSalary, @RequestParam Double maxSalary) throws RecordNotFoundException {
		List<User> list = userService.getUsersFilteredBySalary(minSalary, maxSalary);

		return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/upload-csv-file")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file) {

		try {
			// validate file
			if (file.isEmpty()) {
				throw new RuntimeException("Empty file.");
			} else {
				userService.saveToDatabase(file);
			}
		} catch (Exception exception) {
			log.error("An exception occured while saving user to database= {}", exception.getMessage());
			return "error-page";

		}
		return "file-upload-status";
	}
}
