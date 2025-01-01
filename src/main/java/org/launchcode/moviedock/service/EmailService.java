package org.launchcode.moviedock.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String subject;
    private String recipient;
    private String msgBody;

    public void sendEmail(String subject, String recipient, String msgBody) {

        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//                message.setFrom("no-reply@moviedock.com");
                message.setTo(recipient);
                message.setSubject(subject);
                message.setText(msgBody);
//                message.addInline("myLogo", new ClassPathResource("img/mylogo.gif"));
//                message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));
            }
        });
    }
}
