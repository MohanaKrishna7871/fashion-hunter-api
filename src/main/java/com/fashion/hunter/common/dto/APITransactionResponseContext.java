package com.fashion.hunter.common.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APITransactionResponseContext {
	@Expose
	@SerializedName("status")
	private String status;

	public APITransactionResponseContext() {
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
