package com.project.psicoproject1.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.psicoproject1.enun.Status;
import com.project.psicoproject1.model.PasswordDTO;
import com.project.psicoproject1.model.User;
import com.project.psicoproject1.repository.UserRepository;
import com.project.psicoproject1.service.GenericResponse;
import com.project.psicoproject1.service.SecurityService;
import com.project.psicoproject1.service.UserService;

@RestController
public class LoginUserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messages;

	@Autowired
	SecurityService securityService;

	@PostMapping("/users/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser) {
		Status status = userService.registerUser(newUser);
		return ResponseEntity.ok().body(status);
	}

	@PostMapping("/users/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody User user) {
		Status status = userService.loginUser(user);
		return ResponseEntity.ok().body(status);
	}

	@PostMapping("/users/logout")
	public ResponseEntity<?> logUserOut(@Valid @RequestBody User user) {
		Status status = userService.logUserOut(user);
		return ResponseEntity.ok().body(status);
	}

	@PostMapping("/users/resetPassword")
	public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		userService.findUserByEmail(userEmail);
		return new GenericResponse("message.resetPasswordEmail");
	}

	@GetMapping("/users/changePassword")
	public String showChangePasswordPage(Locale locale, Model model, @RequestParam("token") String token) {
		String result = securityService.validatePasswordResetToken(token);
		return userService.messageChangePassword(result, locale, model, token);
	}

	@PostMapping("/users/savePassword")
	public GenericResponse savePassword(Locale locale, @Valid @RequestBody PasswordDTO passwordDto) {
		String result = securityService.validatePasswordResetToken(passwordDto.getToken());
		return userService.messageSavePassword(result, locale, passwordDto);
	}
}
