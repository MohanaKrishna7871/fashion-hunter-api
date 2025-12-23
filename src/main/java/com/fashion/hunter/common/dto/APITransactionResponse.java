package com.fashion.hunter.common.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APITransactionResponse {
	@Expose
	@SerializedName("responseContext")
	private APITransactionResponseContext responseContext = new APITransactionResponseContext();
	@Expose
	@SerializedName("responseData")
	private Object responseData;

	public APITransactionResponse() {
	}

	public APITransactionResponseContext getResponseContext() {
		return this.responseContext;
	}

	public void setResponseContext(APITransactionResponseContext responseContext) {
		this.responseContext = responseContext;
	}

	public Object getResponseData() {
		return this.responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
}
