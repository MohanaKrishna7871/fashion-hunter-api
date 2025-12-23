package com.fashion.hunter.dto;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fashion.hunter.constants.DTOConstants;
import com.google.gson.annotations.Expose;

public class UserDTO extends BaseDomain {

	@Expose
	private String userId;
	@Expose
	private String username;
	@Expose
	private String password;
	@Expose
	private String email;
	@Expose
	private String statusCode;
	@Expose
	private String status;
	@Expose
	private String isLocked;
	@Expose
	private String lastLoginDateTime;
	@Expose
	private String passwordchangedDateTime;

	// ===== User Details =====
	@Expose
	private String firstName;
	@Expose
	private String lastName;
	@Expose
	private String phoneNumber;

	// ===== Roles =====
	@Expose
	private List<UserRoleDTO> roleCodes;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(String lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getPasswordchangedDateTime() {
		return passwordchangedDateTime;
	}

	public void setPasswordchangedDateTime(String passwordchangedDateTime) {
		this.passwordchangedDateTime = passwordchangedDateTime;
	}

	public List<UserRoleDTO> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<UserRoleDTO> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public String getStatus() {
		return DTOConstants.ACTIVE.equalsIgnoreCase(status) ? "Active" : DTOConstants.INACTIVE.equalsIgnoreCase(status) ? "Inactive" : StringUtils.EMPTY;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

}