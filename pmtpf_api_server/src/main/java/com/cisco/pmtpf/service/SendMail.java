package com.cisco.pmtpf.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class SendMail {

    public void sendmail(String msg) throws AddressException, MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "outbound.cisco.com");
        props.put("mail.smtp.port", "25");
        //props.put("mail.debug", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("pmtpf@cisco.com"));
        message.setRecipient(RecipientType.TO, new InternetAddress("nkaggina@cisco.com"));
        message.setSubject("PMTPF Notification");
        message.setText(msg, "UTF-8"); 
        message.setSentDate(new Date());
        Transport.send(message);
    }

}