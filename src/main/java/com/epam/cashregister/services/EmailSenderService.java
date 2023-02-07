package com.epam.cashregister.services;

import com.epam.cashregister.services.exceptions.ResourceException;
import com.epam.cashregister.services.utils.PropertiesManager;
import org.apache.log4j.Logger;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

public class EmailSenderService {

    private static EmailSenderService instance;

    private final String username;
    private final String password;
    private final String host;
    private final int port;
    private final Logger LOG = Logger.getLogger(EmailSenderService.class);
    private volatile boolean isSending = false;


    private EmailSenderService() throws ResourceException {
        Properties emailProp = PropertiesManager.getPropertyFile("email.properties");
        if (emailProp != null) {
            username = emailProp.getProperty("username");
            password = emailProp.getProperty("password");
            host = emailProp.getProperty("host");
            port = Integer.parseInt(emailProp.getProperty("port"));
        } else throw new ResourceException("Unable to find email.properties file");
    }

    public static EmailSenderService getInstance() throws ResourceException {
        if (instance == null) instance = new EmailSenderService();
        return instance;
    }

    private void send (String to, String from, String headerField, String messageBody, String filePath, String fileName) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Create a default MimeMessage object.
        Message message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

        // Set Subject: header field
        message.setSubject(headerField);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Now set the actual message
        messageBodyPart.setText(messageBody);

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        if (filePath != null && fileName != null) {
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        }

        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport.send(message);

        System.out.println("Sent message successfully....");
    }

    public void sendReceipt(String to, String filePath, String fileName) {

        new Thread(() -> {
            if (isSending) {
                LOG.debug("Start waiting");
                while (isSending) {
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                LOG.debug("End of waiting");
            } else isSending = true;

            int tries = 0;
            do {
                LOG.debug("Attempt to send receipt");
                try {
                    send(to, username, "CASH REGISTER RECEIPT", "", filePath, fileName);
                    LOG.debug("Receipt sent successfully");
                    isSending = false;
                    break;
                } catch (Exception e) {
                    if (tries == 5) {
                        LOG.warn("Check failed to send after 5 attempts, no more attempts");
                        break;
                    }
                    tries += 1;
                    LOG.debug("Failed to send receipt, try again");
                    try {
                        Thread.sleep(60_000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } while (true);
        }).start();

    }

}
