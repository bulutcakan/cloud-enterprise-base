package com.cloud.base.service;

import com.cloud.base.dto.Mail;

public interface MailService {

    public void sendSimpleMessage(String to, String subject, String text);

    void sendEmail(Mail mail);

}
