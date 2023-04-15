package com.humancloud.resume.web.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;
    public void sendEmail(String to, String subject, String htmlBody)  {
       try
       {
           MimeMessage message = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
           helper.setFrom("vijay.khade@humancloud.co.in","ResumeMaker App");
           helper.setTo(to);
           helper.setSubject(subject);
           helper.setText(htmlBody, true);
           mailSender.send(message);
       }
       catch(MessagingException e)
       {
           System.out.println("Message Error: "+e);
       } catch (UnsupportedEncodingException e) {
           throw new RuntimeException(e);
       }
    }
}
