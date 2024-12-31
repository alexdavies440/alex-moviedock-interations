package org.launchcode.moviedock;


import jakarta.mail.MessagingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class MoviedockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviedockApplication.class, args);
//
//		// Create an instance of JavaMailSender
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//
//		// Create an instance of EmailSender
//		EmailSender emailSender = new EmailSender(mailSender);
//
//		// Call the sendEmail method to send an email
//		String recipientEmail = "alexdavies440@gmail.com";
//		String subject = "Hello from Spring Boot";
//		String content = "<p>Hello,</p><p>This is a test email sent from Spring Boot.</p>";
//		emailSender.sendEmail(recipientEmail, subject, content);
//
//		try {
//			emailSender.sendEmail(recipientEmail, subject, content);
//			System.out.println("Email sent successfully.");
//		} catch (MessagingException | UnsupportedEncodingException e) {
//			System.out.println("Failed to send email. Error: " + e.getMessage());
//		}
	}
	}



