package io.github.bigbang.emailservice.config;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.github.bigbang.emailservice.client.EmailClient;
import lombok.Data;

/**
 * Configuration class which reads mail related properties
 * from application properties that are prefixed with
 * "mail.smtp". This current implementation only supports and
 * has been tested with TSL based Gmail hosts.
 */
@Data
@Component
@ConfigurationProperties(prefix = "mail.smtp")
public class EmailConfig {

    private String username;
    private String password;

    private String host;
    private String port;
    private String auth;
    private StartTls starttls;
    private SslConfig ssl;

    private String from;
    private String recipients;
    private String cc;

    /**
     * Sub-class to manage prefix: "mail.smtp.starttls" properties.
     */
    @Data
    public static class StartTls {
        private String enable;
    }

    /**
     * Sub-class to manage prefix: "mail.smtp.ssl" properties.
     */
    @Data
    public static class SslConfig {
        private String trust;
        private String protocols;
    }

    /**
     * Builds a properties object using the connfiguration properties that will be
     * injected into the Email Service.
     */
    public Properties buildProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.starttls.enable", this.starttls.enable);
        props.put("mail.smtp.ssl.trust", this.ssl.trust);
        props.put("mail.smtp.ssl.protocols", this.ssl.protocols);
        return props;
    }

    /**
     * Creates an {@code EmailClient} from the current Configuration.
     * 
     * @return emailer client
     */
    public EmailClient buildEmailClient() {
        return new EmailClient(this);
    }

}
