package com.fashion.hunter.validation.business;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fashion.hunter.common.dto.APIError;
import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.constants.APIMessages;
import com.fashion.hunter.constants.DTOConstants;
import com.fashion.hunter.validation.ValidationResult;

@Component
public class BaseValidationBO {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Gets the validation result.
	 *
	 * @param apiRequest
	 *            the api request
	 * @param allValidationExceptions
	 *            the all validation exceptions
	 * @return the validation result
	 */
	public ValidationResult getValidationResult(final APIRequest apiRequest, final Set<String> allValidationExceptions) {
		final APIError apiError = new APIError();
		Boolean isValid = true;
		if (CollectionUtils.isNotEmpty(allValidationExceptions)) {
			isValid = false;
			apiError.setShortMessage(APIMessages.VALIDATION_FAILURE);
			apiError.setDetailMessage(APIMessages.VALIDATION_FAILURE_DETAIL_MESSAGE);
			apiError.setErrors(allValidationExceptions);
		}
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setApiError(apiError);
		validationResult.setIsValid(isValid);
		validationResult.setObjectForValidation(apiRequest);
		return validationResult;
	}

	/**
	 * Common way of building the ValidationResult object.
	 * 
	 * @param validationErrors
	 *            - Set of Strings
	 * @param apiRequest
	 *            object
	 * @return validationResult object
	 */
	public ValidationResult buildValidationResult(APIRequest apiRequest, Set<String> validationErrors) {
		Boolean isValid = true;
		APIError apiError = new APIError();

		if (!validationErrors.isEmpty()) {

			isValid = false;

			apiError.setShortMessage(APIMessages.VALIDATION_FAILURE);
			apiError.setDetailMessage(APIMessages.VALIDATION_FAILURE_DETAIL_MESSAGE);
			apiError.setErrors(validationErrors);
		}

		ValidationResult validationResult = new ValidationResult();
		validationResult.setApiError(apiError);
		validationResult.setIsValid(isValid);
		validationResult.setObjectForValidation(apiRequest);

		return validationResult;
	}

	public String resolveMessage(String msgKey, Object... args) {
		return messageSource.getMessage(msgKey, args, null);
	}

	protected boolean isObjectPresentInMap(final Map<String, Object> apiParams, final String key) {
		return apiParams.isEmpty() || !apiParams.containsKey(key) || !ObjectUtils.isNotEmpty(apiParams.get(key));
	}

	public ValidationResult validateMandatoryField(APIRequest apiRequest, String mandatoryField) {
		final Set<String> allValidationExceptions = new HashSet<>();
		final Map<String, Object> apiParams = apiRequest.getApiParams();
		if (isObjectPresentInMap(apiParams, mandatoryField)) {
			allValidationExceptions.add(resolveMessage("fh.missingParam", mandatoryField));
		}
		return buildValidationResult(apiRequest, allValidationExceptions);
	}

	public ValidationResult validateMandatoryField(APIRequest apiRequest, List<String> mandatoryField) {
		final Set<String> allValidationExceptions = new HashSet<>();
		final Map<String, Object> apiParams = apiRequest.getApiParams();
		for (String field : mandatoryField) {
			if (isObjectPresentInMap(apiParams, field)) {
				allValidationExceptions.add(resolveMessage("fh.missingParam", field));
			}
		}
		return buildValidationResult(apiRequest, allValidationExceptions);
	}
	
	public ValidationResult siteConfigValidateMandatoryField(APIRequest apiRequest, List<String> mandatoryField) {
		final Set<String> allValidationExceptions = new HashSet<>();
		final Map<String, Object> apiParams = apiRequest.getApiParams();
		for (String field : mandatoryField) {
			if (isObjectPresentInMap(apiParams, field)) {
				allValidationExceptions.add(resolveMessage("fh.missingParam", field));
			}
		}
		validateUUIDAttribute(apiParams, allValidationExceptions, DTOConstants.CONFIG_ID);
		return buildValidationResult(apiRequest, allValidationExceptions);
	}
	
	public ValidationResult updateProductImagevalidateMandatoryField(APIRequest apiRequest, String mandatoryField) {
		final Set<String> allValidationExceptions = new HashSet<>();
		final Map<String, Object> apiParams = apiRequest.getApiParams();
		if (isObjectPresentInMap(apiParams, mandatoryField)) {
			allValidationExceptions.add(resolveMessage("fh.missingParam", mandatoryField));
		}
		validateUUIDAttribute(apiParams, allValidationExceptions, DTOConstants.PRODUCT_ID);
		return buildValidationResult(apiRequest, allValidationExceptions);
	}

	public ValidationResult buildGenericValidationResult(APIRequest apiRequest, String exceptionsMsg) throws Exception {
		Set<String> exceptions = new LinkedHashSet<>();
		exceptions.add(exceptionsMsg);
		return buildValidationResult(apiRequest, exceptions);
	}
	
	/** The Constant UUID_PATTERN. */
	private static final Pattern UUID_PATTERN = Pattern
			.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	/**
	 * Checks if is valid UUID.
	 *
	 * @param input
	 *            the input
	 * @return true, if is valid UUID
	 */
	public static boolean isValidUUID(String input) {
		return UUID_PATTERN.matcher(input).matches();
	}
	
	/**
	 * Checks if is valid UUID attribute.
	 *
	 * @param validationMap
	 *            the validation map
	 * @param allValidationExceptions
	 *            the all validation exceptions
	 * @param key
	 *            the key
	 */
	protected void isValidUUIDAttribute(final Map<String, Object> validationMap, final Set<String> allValidationExceptions, final String key) {
		if (MapUtils.isNotEmpty(validationMap) && MapUtils.getObject(validationMap, key) != null && !MapUtils.getString(validationMap, key).isEmpty()) {
			final String dataValue = (String) validationMap.get(key);
			if (!isValidUUID(dataValue)) {
				allValidationExceptions.add(key + APIMessages.INVALID_UUID_FORMAT);
			}
		}
	}
	
	/**
	 * Validate UUID attribute.
	 *
	 * @param apiParams
	 *            the api params
	 * @param validationExceptions
	 *            the validation exceptions
	 * @param attributeName
	 *            the attribute name
	 */
	protected void validateUUIDAttribute(Map<String, Object> apiParams, Set<String> validationExceptions, String... attributeName) {
		for (String UUIDAttribute : attributeName) {
			if (apiParams.containsKey(UUIDAttribute)) {
				isValidUUIDAttribute(apiParams, validationExceptions, UUIDAttribute);
			}
		}
	}
	
}