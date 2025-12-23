package com.fashion.hunter.constants;

public class APIMessages {

	public static final String API_CONTEXT_TRANSACTION_ID_REQUIRED_ERROR = "transactionId is required in apiContext object of request.";
	public static final String API_CONTEXT_SOURCE_REQUIRED_ERROR = "source is required in apiContext object of request.";
	public static final String API_CONTEXT_USERNAME_REQUIRED_ERROR = "username is required in apiContext object of request.";
	public static final String API_CONTEXT_PROTOCOL_REQUIRED_ERROR = "protocol is required in apiContext object of request and must be a valid protocol.";
	public static final String API_CONTEXT_REQUIRED_ERROR = "apiContext object is required in request.";
	public static final String API_REQUEST_REQUIRED_ERROR = "Invalid request. Please check your request body and try again.";
	public static final String VALIDATION_FAILURE = "Validation Failure";
	public static final String VALIDATION_FAILURE_DETAIL_MESSAGE = "Cannot process request due to validation failure(s)";
	public static final String INVALID_TRANSACTION_ERROR = "Please enter a valid transactionId.";
	public static final String INVALID_TRANSACTION_SHORT_MESSAGE_ERROR = "Invalid Request";
	public static final String UNSERIALIZATION_SHORT_MESSAGE_ERROR = "Invalid JSON Values";
	public static final String UNSERIALIZATION_ERROR = "Request cannot be completed due to error converting JSON to Object. Please re-check your JSON to ensure all values are valid.";
	public static final String API_ERROR_SHORT_MESSAGE = "API Error";
	public static final String API_ERROR_DETAIL_MESSAGE = "Internal System Error.";
	public static final String API_ERROR_LONG_MESSAGE = "Internal system error has occurred (See error code).";
	public static final String EMPTY_API_RESULT_MESSAGE = "No Results";
	public static final String INVALID_UUID_FORMAT = " is Invalid. Please enter a valid UUID format";

	private APIMessages() {
		throw new IllegalStateException("Constants class");
	}
}
