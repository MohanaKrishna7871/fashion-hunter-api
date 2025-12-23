package com.fashion.hunter.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fashion.hunter.common.dto.APIRequest;

public class CommonUtils {

	private final static Logger logger = LogManager.getLogger(CommonUtils.class);

	private static final String DEFAULT_USER = "UNKNOWN";

	public static String formatName(String firstName, String lastName) {
		firstName = StringUtils.upperCase(StringUtils.trimToEmpty(firstName));
		lastName = StringUtils.upperCase(StringUtils.trimToEmpty(lastName));

		return StringUtils.isNotEmpty(lastName) ? lastName + (StringUtils.isNotEmpty(firstName) ? ", " + firstName : "")
				: firstName;
	}

	public static String safeTrimUpper(String value) {
		return (value == null) ? null : value.trim().toUpperCase();
	}

	public static String fetchUserName(APIRequest apiRequest) {
		if (apiRequest == null || apiRequest.getApiContext() == null) {
			return DEFAULT_USER;
		}
		return safeTrimUpper(apiRequest.getApiContext().getUsername());
	}

	public static String safeTrim(String value) {
		return (value == null) ? null : value.trim();
	}

	public static boolean isObjectNotEmpty(final Object object) {
		return object != null;
	}

	public static void logError(Class clazz, Throwable th) {
		Logger loggerObj = LogManager.getLogger(clazz);
		loggerObj.error(ExceptionUtils.getStackTrace(th));
	}

	public static void logInfo(Class clazz, Throwable th) {
		Logger loggerObj = LogManager.getLogger(clazz);
		loggerObj.info(ExceptionUtils.getStackTrace(th));
	}

	public static void logWarn(Class clazz, Throwable th) {
		Logger loggerObj = LogManager.getLogger(clazz);
		loggerObj.warn(ExceptionUtils.getStackTrace(th));
	}

	public static String formatPhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			return "";
		}

		// Remove all non-digit characters
		String digitsOnly = phoneNumber.replaceAll("\\D", "");

		// Check for valid 10-digit number
		if (digitsOnly.length() != 10) {
			return phoneNumber; // Return as-is if not valid
		}

		// Format as XXX-XXX-XXXX
		return String.format("%s-%s-%s", digitsOnly.substring(0, 3), digitsOnly.substring(3, 6),
				digitsOnly.substring(6));
	}

}
