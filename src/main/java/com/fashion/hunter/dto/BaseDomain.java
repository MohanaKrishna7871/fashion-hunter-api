package com.fashion.hunter.dto;

import com.fashion.hunter.utils.CommonUtils;
import com.google.gson.annotations.Expose;

import jakarta.validation.constraints.Size;


public class BaseDomain {

	@Expose
	@Size(max = 255)
	private String createdBy;
	@Expose
	private String createdDateTime;
	@Expose
	@Size(max = 255)
	private String updatedBy;
	@Expose
	private String updatedDateTime;
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = CommonUtils.safeTrimUpper(createdBy);
	}
	public String getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = CommonUtils.safeTrimUpper(updatedBy);
	}
	public String getUpdatedDateTime() {
		return updatedDateTime;
	}
	public void setUpdatedDateTime(String updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	
}