package com.czxy.bos.domain.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "T_ROLE_MENU")
public class RoleMenu {
	@Column(name = "ROLE_ID")
	private int roleId;
	@Column(name = "MENU_ID")
	private int menuId;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
}