package com.fashion.hunter.common.dto;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIError {
	@Expose
	@SerializedName("shortMessage")
	private String shortMessage;
	@Expose
	@SerializedName("detailMessage")
	private String detailMessage;
	@Expose
	@SerializedName("code")
	private String errorCode;
	@Expose
	@SerializedName("errors")
	private Set<String> errors = new HashSet();

	public APIError() {
	}

	public String getShortMessage() {
		return this.shortMessage;
	}

	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	public String getDetailMessage() {
		return this.detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public Set<String> getErrors() {
		return this.errors;
	}

	public void setErrors(Set<String> errors) {
		this.errors = errors;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}