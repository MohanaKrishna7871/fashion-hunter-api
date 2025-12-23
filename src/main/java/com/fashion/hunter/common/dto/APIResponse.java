package com.fashion.hunter.common.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIResponse {
	private int httpStatus;
	@Expose
	@SerializedName("apiResponse")
	private APITransactionResponse apiResponse = new APITransactionResponse();
	@Expose
	@SerializedName("apiRequest")
	private APIRequest apiRequest;
	@Expose
	@SerializedName("apiError")
	private APIError apiError;

	public APIResponse() {
	}

	public APITransactionResponse getApiResponse() {
		return this.apiResponse;
	}

	public void setApiResponse(APITransactionResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	public APIRequest getApiRequest() {
		return this.apiRequest;
	}

	public void setApiRequest(APIRequest apiRequest) {
		this.apiRequest = apiRequest;
	}

	public APIError getApiError() {
		return this.apiError;
	}

	public void setApiError(APIError apiError) {
		this.apiError = apiError;
	}

	public int getHttpStatus() {
		return this.httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
}