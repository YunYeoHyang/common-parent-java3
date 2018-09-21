package com.czxy.bos.domain.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "T_USER_ROLE")
public class UserRole {
	@Column(name="USER_ID")
	private int userId;
	@Column(name="ROLE_ID")
	private int roleId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
	
}
