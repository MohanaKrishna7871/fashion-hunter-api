package com.fashion.hunter.dto;

import java.util.List;

import com.google.gson.annotations.Expose;

public class UserRoleDTO extends BaseDomain {

	@Expose
	private String roleId;
	@Expose
	private String roleCode;
	@Expose
	private String roleName;
	@Expose
	private String roleDesc;
	
	@Expose
	private List<UserRolePermissionDTO> userRolePermission;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<UserRolePermissionDTO> getUserRolePermission() {
		return userRolePermission;
	}

	public void setUserRolePermission(List<UserRolePermissionDTO> userRolePermission) {
		this.userRolePermission = userRolePermission;
	}

}