package com.project.psicoproject1.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.psicoproject1.enun.Status;
import com.project.psicoproject1.model.PasswordDTO;
import com.project.psicoproject1.model.PasswordResetToken;
import com.project.psicoproject1.model.User;
import com.project.psicoproject1.repository.PasswordResetTokenRepository;
import com.project.psicoproject1.repository.UserRepository;
import com.project.psicoproject1.service.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private EncriptSenha enc;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private Environment env;

	@Autowired
	private MessageSource messages;

	List<User> users = new ArrayList<>();

	// Registra um novo usuário retornando um status
	public Status registerUser(User newUser) {
		if (userRepository.existsByUsername(newUser.getUsername())) {
			return Status.USER_ALREADY_EXISTS;
		}
		if (userRepository.existsByEmail(newUser.getEmail())) {
			return Status.USER_ALREADY_EXISTS;
		}
		if (StringUtils.isBlank(newUser.getUsername())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		if (StringUtils.isBlank(newUser.getPassword())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		if (StringUtils.isBlank(newUser.getEmail())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		if (!isValidEmailAddress(newUser.getEmail())) {
			return Status.EMAIL_INVALID;
		}
		newUser.setPassword(newPassowordHash(newUser));
		userRepository.save(newUser);
		return Status.SUCCESS;
	}

	// Login de um usuário retornando um status
	public Status loginUser(User user) {
		String newPassword = newPassowordHash(user);
		Optional<User> optional = userRepository.findByUsernameAndPassword(user.getUsername(), newPassword);

		if (optional.isPresent()) {
			User userdb = optional.get();

			if (userdb.getPassword().equals(newPassword)) {
				userdb.setLoggedIn(true);
				userRepository.save(userdb);
				return Status.SUCCESS;
			}
		}
		if (StringUtils.isBlank(user.getUsername())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		return Status.FAILURE;
	}

	// Logout de um usuario retornando um status
	public Status logUserOut(@Valid @RequestBody User user) {
		String newPassword = newPassowordHash(user);
		Optional<User> optional = userRepository.findByUsernameAndPassword(user.getUsername(), newPassword);

		if (optional.isPresent()) {
			User userdb = optional.get();
			userdb.setLoggedIn(false);
			userRepository.save(userdb);
			return Status.SUCCESS;
		}
		if (StringUtils.isBlank(user.getUsername())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return Status.FIELD_NOT_COMPLETED;
		}
		return Status.FAILURE;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	// Transforma senha em uma hash
	public String newPassowordHash(User user) {
		String newPassword = null;
		try {
			newPassword = enc.senhaByte(user.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return newPassword;
	}
	
	//Busca por usuario pelo ID
	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	//Apaga o usuario pelo ID
	public Status delete(Long id) {
		userRepository.findById(id);
		userRepository.deleteById(id);
		return Status.SUCCESS;
	}
	
	//Busca usuario e envia o link pelo email
	public User findUserByEmail(String userEmail) {
		Optional<User> optional = userRepository.findByEmail(userEmail);
		if (optional.isPresent()) {
			User user = optional.get();
			String token = UUID.randomUUID().toString();
			createPasswordResetTokenForUser(user, token);
			mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
			return user;
		}
		
		return null;
	}
	
	//Cria um novo PassworResetToken pelo usuario 
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}
	
	
	public Optional<PasswordResetToken> getUserByPasswordResetToken(String token) {
		Optional<PasswordResetToken> optional = passwordResetTokenRepository.findByToken(token);
		if (optional.isPresent()) {
			return optional;
		}
		return null;
	}

	public void changeUserPassword(User user, String newPassword) {
		user.setPassword(newPassword);
		user.setPassword(newPassowordHash(user));
		userRepository.save(user);
	}

	public String messageChangePassword(String result, Locale locale, Model model, String token) {
		if (result != null) {
			String message = messages.getMessage("auth.message." + result, null, locale);
			return "redirect:/login.html?lang=" + locale.getLanguage() + "&message=" + message;
		} else {
			model.addAttribute("token", token);
			return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
		}
	}

	public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
		String url = contextPath + "/users/changePassword?token=" + token;
		String message = "message.resetPassword";
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	public String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public GenericResponse messageSavePassword(String result, Locale locale, PasswordDTO passwordDto) {
		if (result != null) {
			return new GenericResponse(messages.getMessage("auth.message." + result, null, locale));
		}
		Optional<PasswordResetToken> optional = getUserByPasswordResetToken(passwordDto.getToken());
		if (optional.isPresent()) {
			User user = optional.get().getUser();
			changeUserPassword(user, passwordDto.getNewPassword());
			return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
		} else {
			return new GenericResponse(messages.getMessage("auth.message.invalid", null, locale));
		}
	}
	
	public static boolean isValidEmailAddress(String email) {
	    boolean result = true;
	    try {
	        InternetAddress emailAddr = new InternetAddress(email);
	        emailAddr.validate();
	    } catch (AddressException ex) {
	        result = false;
	    }
	    return result;
	}
}
