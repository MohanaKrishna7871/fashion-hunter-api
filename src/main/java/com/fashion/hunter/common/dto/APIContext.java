package com.fashion.hunter.common.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIContext {
	@Expose
	@SerializedName("transactionId")
	private String transactionId;
	@Expose
	@SerializedName("source")
	private String source;
	@Expose
	@SerializedName("applicationId")
	private String applicationId;
	@Expose
	@SerializedName("username")
	private String username;
	@Expose
	@SerializedName("protocol")
	private APIRequestProtocol requestProtocol;

	public APIContext() {
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public APIRequestProtocol getRequestProtocol() {
		return this.requestProtocol;
	}

	public void setRequestProtocol(APIRequestProtocol requestProtocol) {
		this.requestProtocol = requestProtocol;
	}
}