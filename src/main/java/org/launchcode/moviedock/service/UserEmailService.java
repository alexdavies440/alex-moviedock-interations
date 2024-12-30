//package org.launchcode.moviedock.service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.launchcode.moviedock.models.AppUser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.MailAuthenticationException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//@Service
//public class UserEmailService {
//
//    private final JavaMailSender mailSender;
//    private final SpringTemplateEngine templateEngine;
//
//    public UserEmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
//        this.mailSender = mailSender;
//        this.templateEngine = templateEngine;
//    }
//
//    private String getVerificationMailContent(AppUser appUser) {
//        Context context = new Context();
//        String verificationUrl = String.format("http://localhost:8080/verify?code=%s", appUser.getVerificationCode());
//        context.setVariable("applicationUrl", verificationUrl);
//        return templateEngine.process("user-verify", context);
//    }
//
//    private MimeMessage createMessage(AppUser appUser, String content) throws MessagingException {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//        message.setText(content, true);
//        message.setSubject("Welcome to MyApp XYZ");
//        message.setFrom("noreply@myapp.xyz");
//        message.setTo(appUser.getEmail());
//        return mimeMessage;
//    }
//
//    public void sendVerificationMail(AppUser appUser) {
//        String content = getVerificationMailContent(appUser);
//        try {
//            mailSender.send(createMessage(appUser, content));
//        } catch (MessagingException ex) {
//            throw new MailAuthenticationException("Could not send e-mail to verify user with e-mail '" + appUser.getEmail() + "'", ex);
//        }
//    }
//}