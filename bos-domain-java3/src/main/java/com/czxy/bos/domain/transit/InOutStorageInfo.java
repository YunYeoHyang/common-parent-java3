package com.czxy.bos.domain.transit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 出入库信息
 */
@Entity
@Table(name = "T_IN_OUT_STORAGE_INFO")
public class InOutStorageInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "OPERATION")
	private String operation; // 操作类型 ： 入库、出库、到达网点

	@Column(name = "ADDRESS")
	private String Address; // 仓库、网点 地址

	@Column(name = "DESCRIPTION")
	private String description; // 描述
	
	@Column(name = "TRANSIT_INFO_ID")
	private Integer transitInfoId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTransitInfoId() {
		return transitInfoId;
	}

	public void setTransitInfoId(Integer transitInfoId) {
		this.transitInfoId = transitInfoId;
	}

}
