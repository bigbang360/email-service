package io.github.bigbang.emailservice.models;

import java.util.ArrayList;
import java.util.List;

import io.github.bigbang.emailservice.injector.TemplateInjector;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A clean placeholder for the Email Message detauks before it gets converted
 * to a MIME Java Message.
 */
@Data
@NoArgsConstructor
public class EmailMessage {

    private String subject;
    private String body;
    private boolean htmlMessage;
    private List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();

    /** Create an EmailMessage. */
    public EmailMessage(String subject, String body, boolean htmlMessage) {
        this.subject = subject;
        this.body = body;
        this.htmlMessage = htmlMessage;
    }

    /**
     * Builds a message by injecting a template.
     * 
     * @param subject
     * @param templateInjector
     */
    public EmailMessage(String subject, TemplateInjector templateInjector) {
        this.subject = subject;
        this.htmlMessage = true;
        this.body = templateInjector.generateHtmlFromTemplate();
    }

    /**
     * Adds to the existing body text of the email message.
     * 
     * @param text - to be added to the body
     */
    public void addToBody(String text) {
        if (text != null && !text.isEmpty()) {
            if (this.body != null) {
                this.body = this.body + "\n\n" + text;
            } else {
                this.body = text;
            }
        }
    }

    /**
     * Adds an {@code EmailAttachment} to the list of attachments
     * associated with this message.
     * 
     * @param attachment - to be added to message
     */
    public void addAttachment(EmailAttachment attachment) {
        this.attachments.add(attachment);
    }
}
