package com.fashion.hunter.common.dto;

public enum APIResponseStatus {
	SUCCESS("Success"), ERROR("Error");

	private String displayValue;

	private APIResponseStatus(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return this.displayValue;
	}
}