package com.project.psicoproject1.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncriptSenha {
	
	public String senhaByte(String password) throws NoSuchAlgorithmException {
		
		StringBuilder hexStringSenhaAdmin = new StringBuilder();
		
		byte messageDigestSenhaAdmin[];
		 		
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			messageDigestSenhaAdmin = algorithm.digest(password.getBytes("UTF-8"));
			
			for (byte b : messageDigestSenhaAdmin) {
				hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		
		}

		return hexStringSenhaAdmin.toString();
	}
	
}
