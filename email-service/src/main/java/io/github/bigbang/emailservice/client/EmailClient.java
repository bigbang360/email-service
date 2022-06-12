package io.github.bigbang.emailservice.client;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import io.github.bigbang.emailservice.config.EmailConfig;
import io.github.bigbang.emailservice.models.EmailAttachment;
import io.github.bigbang.emailservice.models.EmailMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generates and sends Email Messages.
 */
@Data
@NoArgsConstructor
public class EmailClient {

    private EmailConfig config;

    /**
     * Initializes the service from a custom configuration file.
     * 
     * @param config
     */
    public EmailClient(EmailConfig config) {
        this.config = config;
    }

    /**
     * Authenticates with the server via basic credentials (username, password)
     * to build a Session.
     * 
     * @return session - used as the bases of building messages
     */
    public Session buildSession() {
        return Session.getInstance(config.buildProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getUsername(), config.getPassword());
                    }
                });

    }

    /**
     * Composes a multi-part MIME message and associates it with the current object.
     * 
     * @param emailMessage - message to convert to a sendable MIME message
     * @return message - the resulting MIME message
     * @throws AddressException
     *                            - invalid email address
     * @throws MessagingException
     *                            - error constructing the Message
     */
    public Message composeMime(EmailMessage emailMessage) throws AddressException, MessagingException {

        Message message = new MimeMessage(this.buildSession());
        message.setFrom(new InternetAddress(config.getFrom()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.getRecipients()));
        message.setSubject(emailMessage.getSubject());

        Multipart multipart = new MimeMultipart();

        // Add: Text or HTML body
        BodyPart messageBodyPart = new MimeBodyPart();
        String contentType = emailMessage.isHtmlMessage() ? "text/html" : "text/plain";
        messageBodyPart.setContent(emailMessage.getBody(), contentType);
        multipart.addBodyPart(messageBodyPart);

        // Add: Attachments
        for (EmailAttachment attachment : emailMessage.getAttachments()) {
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(attachment.generatDataHandler());
            messageBodyPart.setFileName(attachment.getFilename());
            multipart.addBodyPart(messageBodyPart);
        }
        message.setContent(multipart);

        return message;
    }

    /**
     * Sends the email.
     * 
     * @param message
     *                - the {@code EmailMessage} object to send.
     */
    public void sendEmail(EmailMessage message) throws MessagingException {
        Transport.send(composeMime(message));
    }

    /**
     * Sends the email.
     * 
     * @param message
     *                - the {@code Message} object to send.
     */
    public void sendEmail(Message message) throws MessagingException {
        Transport.send(message);
    }
}
