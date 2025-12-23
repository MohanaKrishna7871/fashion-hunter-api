package com.fashion.hunter.services.business;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.common.dto.APIResponseStatus;
import com.fashion.hunter.config.LoadPropertiesConfig;
import com.fashion.hunter.services.APIResponseBO;
import com.fashion.hunter.utils.GsonUtils;
import com.fashion.hunter.validation.ValidationResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Service
public class BaseBO {
	private final Logger logger = LogManager.getLogger(this.getClass());

	private APIResponseBO apiResponseBO;

	@Autowired
	private MessageSource messageSource;

	private static Gson gsonBOIncoming = GsonUtils.createIncomingGson();
	
	private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() {
	}.getType();

	public BaseBO() {
		this.apiResponseBO = new APIResponseBO();
	}

	public Map<String, Object> apiRequest(APIRequest apiRequest, String key) {
		return (Map<String, Object>) GsonUtils.serializeObjectFromMap(gsonBOIncoming, (Map<String, Object>) apiRequest.getApiParams().get(key), Map.class);
	}

	public static Map<String, Object> convertToMap(Object object) {
		JsonElement jsonElement = gsonBOIncoming.toJsonTree(object);
		return gsonBOIncoming.fromJson(jsonElement, MAP_TYPE);
	}

	public APIResponse buildInternalSystemErrorResponse(APIRequest apiRequest, Exception ex) {
		String errorCode = apiResponseBO.generateResponseErrorCode();
		logger.fatal("[Error Code: {}]:", errorCode, ex);
		return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
	}

	public String resolveMessage(String msgKey, Object... args) {
		return messageSource.getMessage(msgKey, args, null);
	}

	public APIResponse buildValidationFailureErrorResponse(APIRequest apiRequest, ValidationResult validationResult) {
		APIResponse invalidResponse = new APIResponse();
		invalidResponse.setHttpStatus(200);
		invalidResponse.setApiError(validationResult.getApiError());
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return invalidResponse;
	}
	
	protected String validationMessage(String key) {
		return LoadPropertiesConfig.retrivePropertyKey(key);
	}
	
	public String generateErrorCode() {

		Long base = System.currentTimeMillis();
		char[] ALPHA_LIST = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		StringBuilder errorCode = new StringBuilder();

		for (int index = 0; index < 3; index++) {
			errorCode.append(ALPHA_LIST[new SecureRandom().nextInt(ALPHA_LIST.length)]);
		}

		return errorCode.toString() + "-" + base.toString();
	}
	
}
