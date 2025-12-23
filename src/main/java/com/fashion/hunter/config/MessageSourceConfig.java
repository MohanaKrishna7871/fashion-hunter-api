package com.fashion.hunter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for setting up message source properties.
 */
@Configuration
public class MessageSourceConfig {

	private static final String HART_PORTAL_VALIDATION_MESSAGES_BASE = "validationmessages-fashionHunter";
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames(HART_PORTAL_VALIDATION_MESSAGES_BASE);
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultEncoding("UTF-8");
		return source;
	}
}
