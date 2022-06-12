package io.github.bigbang.emailservice.injector;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A TemplateInjector allows Thymeleaf to populate an existing HTML
 * template and read the results formatted as a String.
 */
@Data
@NoArgsConstructor
public class TemplateInjector {

    private String template;
    private Map<String, Object> properties = new HashMap<String, Object>();

    private SpringTemplateEngine templateEngine;

    public TemplateInjector(String template) {
        this.template = template;
    }

    /**
     * Adds a value to be injected to a template.
     * 
     * @param key
     * @param value
     */
    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    /**
     * Clears the properties associated with this particular template.
     */
    public void clearProperties() {
        this.properties.clear();
    }

    /**
     * Returns the HTML String after injecting the template.
     * 
     * @return HTML message as a String
     */
    public String generateHtmlFromTemplate() {
        final Context context = new Context();
        context.setVariables(this.properties);
        return templateEngine.process(this.template, context);

    }

}

