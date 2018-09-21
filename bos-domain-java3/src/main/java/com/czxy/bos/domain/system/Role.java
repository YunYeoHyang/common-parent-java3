package com.czxy.bos.domain.system;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @description:角色
 */
@Entity
@Table(name = "T_ROLE")
public class Role {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "JDBC")    
	@Column(name = "ID")
	private int id;
	@Column(name = "NAME")
	private String name; // 角色名称
	@Column(name = "KEYWORD")
	private String keyword; // 角色关键字，用于权限控制
	@Column(name = "DESCRIPTION")
	private String description; // 描述

	@Transient
	private Set<User> users = new HashSet<User>(0);

	@Transient
	private Set<Permission> permissions = new HashSet<Permission>(0);
	@Transient
	private Set<Menu> menus = new HashSet<Menu>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

}
