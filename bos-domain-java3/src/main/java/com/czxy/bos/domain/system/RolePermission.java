package com.czxy.bos.domain.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_ROLE_PERMISSION")
public class RolePermission {
	@Column(name= "ROLE_ID")
	private int roleId;
	@Column(name = "PERMISSION_ID")
	private int permissionId;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
}