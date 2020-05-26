package com.cloud.base.service.impl;

import com.cloud.base.constans.MailType;
import com.cloud.base.dto.Mail;
import com.cloud.base.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendEmail(Mail mail) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.addAttachment("template-cover.png", new ClassPathResource("images/cloud.png"));
            Context context = new Context();
            context.setVariables(mail.getProps());
            String html = null;
            if (mail.getMailType().equals(MailType.ACTIVATE))
                html = templateEngine.process("register-template", context);
            else if (mail.getMailType().equals(MailType.RESET_PASSWORD))
                html = templateEngine.process("reset-template", context);

            helper.setTo(mail.getMailTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            emailSender.send(message);
        } catch (Exception ex) {
            //LOG VE EXCEPTION
        }
    }
}
