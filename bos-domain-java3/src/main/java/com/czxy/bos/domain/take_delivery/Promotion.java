package com.czxy.bos.domain.take_delivery;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.czxy.bos.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @description:促销信息实体类
 */
@Entity
@Table(name = "T_PROMOTION")
public class Promotion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "TITLE")
	private String title; // 宣传概要(标题)
	@Column(name = "TITLE_IMG")
	private String titleImg; // 宣传图片
	@Column(name = "ACTIVE_SCOPE")
	private String activeScope;// 活动范围
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "START_DATE")
	private Date startDate; // 发布时间
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "END_DATE")
	private Date endDate; // 失效时间
	
	@Column(name = "UPDATE_TIME")
	private Date updateTime; // 更新时间
	@Column(name = "UPDATE_UNIT")
	private String updateUnit; // 更新单位
	@Column(name = "UPDATE_USER")
	private String updateUser;// 更新人 后续与后台用户关联
	@Column(name = "STATUS")
	private String status = "1"; // 状态 可取值：1.进行中 2. 已结束
	@Column(name = "DESCRIPTION")
	private String description; // 宣传内容(活动描述信息)

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getActiveScope() {
		return activeScope;
	}

	public void setActiveScope(String activeScope) {
		this.activeScope = activeScope;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUnit() {
		return updateUnit;
	}

	public void setUpdateUnit(String updateUnit) {
		this.updateUnit = updateUnit;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * TODO 图片路径如何处理？
	 * @return
	 */
	public String getTitleImg() {
		if(titleImg !=null && titleImg.startsWith(Constants.BOS_MANAGEMENT_HOST)){
			return titleImg;
		}
		return Constants.BOS_MANAGEMENT_HOST+titleImg;
	}

}
