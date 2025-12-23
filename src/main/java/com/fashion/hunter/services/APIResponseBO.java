package com.fashion.hunter.services;

import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fashion.hunter.common.dto.APIError;
import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.common.dto.APIResponseStatus;
import com.fashion.hunter.validation.ValidationResult;

@Component
public class APIResponseBO {
	private Random random = new Random();

	public APIResponseBO() {
	}

	public String generateResponseErrorCode() {
		Long base = System.currentTimeMillis();
		char[] alphaList = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		StringBuilder errorCode = new StringBuilder();

		for (int index = 0; index < 3; ++index) {
			errorCode.append(alphaList[this.random.nextInt(alphaList.length)]);
		}

		return errorCode.toString() + "-" + base.toString();
	}

	public APIResponse generateInvalidAPIEndpointErrorResponse(APIRequest apiRequest) {
		APIError apiError = new APIError();
		apiError.setDetailMessage("Please enter a valid transactionId.");
		apiError.setShortMessage("Invalid Request");
		apiError.setErrors((Set) null);
		APIResponse invalidResponse = new APIResponse();
		invalidResponse.setHttpStatus(400);
		invalidResponse.setApiError(apiError);
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		return invalidResponse;
	}

	public APIResponse buildUnserializationFailureErrorResponse(APIRequest apiRequest) {
		APIError apiError = new APIError();
		apiError.setDetailMessage(
				"Request cannot be completed due to error converting JSON to Object. Please re-check your JSON to ensure all values are valid.");
		apiError.setShortMessage("Invalid JSON Values");
		apiError.setErrors((Set) null);
		APIResponse invalidResponse = new APIResponse();
		invalidResponse.setHttpStatus(400);
		invalidResponse.setApiError(apiError);
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		return invalidResponse;
	}

	public APIResponse buildValidationFailureErrorResponse(APIRequest apiRequest, ValidationResult validationResult) {
		APIResponse invalidResponse = new APIResponse();
		invalidResponse.setHttpStatus(400);
		invalidResponse.setApiError(validationResult.getApiError());
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		return invalidResponse;
	}

	public APIResponse buildValidResponse(APIRequest apiRequest, Object objectForResponse) {
		APIResponse response = new APIResponse();
		response.setHttpStatus(200);
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.SUCCESS.getDisplayValue());
		response.getApiResponse().setResponseData(objectForResponse);
		return response;
	}

	public APIResponse buildInternalSystemErrorResponse(APIRequest apiRequest, String errorCode) {
		APIResponse response = new APIResponse();
		response.setHttpStatus(500);
		response.setApiError(new APIError());
		response.getApiError().setShortMessage("API Error");
		response.getApiError().setDetailMessage("Internal System Error.");
		response.getApiError().setErrorCode(errorCode);
		response.getApiError().getErrors().add("Internal system error has occurred (See error code).");
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		return response;
	}

	public APIResponse buildEmptyResponse(APIRequest apiRequest, String emptyResponse) {
		APIResponse response = new APIResponse();
		response.setHttpStatus(400);
		response.setApiError(new APIError());
		response.getApiError().setShortMessage("No Results");
		response.getApiError().setDetailMessage(emptyResponse);
		response.getApiError().setErrors((Set) null);
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		return response;
	}

	public APIResponse buildInvalidRequestResponse(APIRequest apiRequest, String invalidResponseMessage) {
		APIResponse response = new APIResponse();
		response.setHttpStatus(400);
		response.setApiError(new APIError());
		response.getApiError().setShortMessage("Invalid Request");
		response.getApiError().setDetailMessage(invalidResponseMessage);
		response.getApiError().setErrors((Set) null);
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		return response;
	}
}