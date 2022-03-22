package com.project.psicoproject1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.psicoproject1.enun.Status;
import com.project.psicoproject1.model.User;
import com.project.psicoproject1.repository.UserRepository;
import com.project.psicoproject1.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> findAll() {
		List<User> list = userService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = userService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping("/users/{id}")
	public Status deleteUsers(@PathVariable Long id) {
		return userService.delete(id);
	}
}