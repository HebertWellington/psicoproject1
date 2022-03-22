package com.project.psicoproject1.config;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class HashConfig {
	
	@Bean
	public StringBuilder builderHash() {
		return new StringBuilder();
	}
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	      mailSender.setHost("smtp.gmail.com");
	      mailSender.setPort(587);
	        
	      mailSender.setUsername("hebertwellington@gmail.com");
	      mailSender.setPassword("rjhde15474");
	        
	      Properties props = mailSender.getJavaMailProperties();
	      props.put("mail.transport.protocol", "smtp");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	      props.put("mail.debug", "true");
	        
	      return mailSender;
		}
	
	  @Bean
	    public MessageSource messageSource() {
	        ResourceBundleMessageSource messageBundleResrc=new ResourceBundleMessageSource();
	        messageBundleResrc.setBasename("messages");
	        return messageBundleResrc;
	    }
	}

