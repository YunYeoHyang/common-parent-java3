package com.czxy.bos.domain.transit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @description: 签收信息
 */
@Entity
@Table(name = "T_SIGN_INFO")
public class SignInfo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY , generator = "JDBC")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "SIGN_NAME")
	private String signName;

	@Column(name = "SIGN_TIME")
	private Date signTime;

	@Column(name = "SIGN_TYPE")
	private String signType;

	@Column(name = "ERROR_REMARK")
	private String errorRemark;

	@Column(name = "DESCRIPTION")
	private String description; // 描述
	
	@Transient
	private String transitInfoId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getErrorRemark() {
		return errorRemark;
	}

	public void setErrorRemark(String errorRemark) {
		this.errorRemark = errorRemark;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransitInfoId() {
		return transitInfoId;
	}

	public void setTransitInfoId(String transitInfoId) {
		this.transitInfoId = transitInfoId;
	}

}
