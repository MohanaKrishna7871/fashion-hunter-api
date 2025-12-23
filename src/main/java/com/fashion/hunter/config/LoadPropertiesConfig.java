package com.fashion.hunter.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class LoadPropertiesConfig {

	private static final Logger logger = LogManager.getLogger(LoadPropertiesConfig.class);
	
	// To add the properties files in the list to be loaded
	private static final List<String> PROPERTIES_FILES = Arrays.asList("validationmessages-fashionHunter.properties");

	private static Properties properties = new Properties();

	public LoadPropertiesConfig() {
		init();
	}

	public static void init() {
		try {
			loadProperties(PROPERTIES_FILES);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private static void loadProperties(List<String> propertiesFiles) {
		for (String file : propertiesFiles) {
			try {
				final Properties loadedProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(file));
				if (loadedProperties == null) {
					throw new IOException();
				}
				properties.putAll(loadedProperties);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public static void reinit() {
		logger.info("Reinitializing properties.");
		destroy();
		init();
		logger.info("Properties reinitialized.");
	}

	private static void destroy() {
		properties = null;
		logger.info("Properties destroyed.");
	}

	public static String retrivePropertyKey(final String key) {
		if (properties != null && properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return StringUtils.EMPTY;
	}
}
