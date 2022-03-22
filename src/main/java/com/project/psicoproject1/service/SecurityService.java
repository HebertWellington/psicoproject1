package com.project.psicoproject1.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.psicoproject1.model.PasswordResetToken;
import com.project.psicoproject1.repository.PasswordResetTokenRepository;
import com.project.psicoproject1.repository.UserRepository;

@Service
public class SecurityService {
	
	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;
	
	public String validatePasswordResetToken(String token) {
	    final Optional<PasswordResetToken> optional = passwordTokenRepository.findByToken(token);

	    return !isTokenFound(optional.get()) ? "invalidToken"
	            : isTokenExpired(optional.get()) ? "expired"
	            : null;
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
	    return passToken != null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
	    final Calendar cal = Calendar.getInstance();
	    return passToken.getExpiryDate().before(cal.getTime());
	}

	
}
