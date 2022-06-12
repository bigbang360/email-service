package io.github.bigbang.emailservice.models;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import lombok.Data;

/**
 * Email Attachments to add to the {@code EmailMessage} class.
 */
@Data
public class EmailAttachment {

    private String filename;

    /**
     * Generates a {@code DataSource} object from the filename.
     * 
     * @return - DataSource
     */
    private DataSource generateDataSource() {
        return new FileDataSource(this.filename);
    }

    /**
     * Creates a {@code DataHandler} object from the {@code DataSource} object
     * based on the filename.
     * 
     * @return - DataHandler
     */
    public DataHandler generatDataHandler() {
        return new DataHandler(generateDataSource());
    }

    /**
     * Creates a {@code DataHandler} object from the specified {@code DataSource}
     * object.
     * 
     * @return - DataHandler
     */
    public DataHandler generatDataHandler(DataSource datasource) {
        return new DataHandler(datasource);
    }
}
