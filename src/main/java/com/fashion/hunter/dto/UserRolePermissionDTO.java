package com.fashion.hunter.dto;

import com.google.gson.annotations.Expose;

public class UserRolePermissionDTO extends BaseDomain {

	@Expose
	private String permissionId;
	@Expose
	private String permissionCode;
	@Expose
	private String permissionName;
	@Expose
	private String permissionDesc;

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}
}