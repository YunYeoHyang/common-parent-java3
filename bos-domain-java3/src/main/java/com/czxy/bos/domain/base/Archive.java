package com.czxy.bos.domain.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @description:档案类，记录所有的分类信息，在子档中
 */
@Entity
@Table(name = "T_ARCHIVE")
public class Archive {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id; // 主键
	@Column(name = "ARCHIVE_NUM", unique = true )
	private String archiveNum;// 档案编号
	@Column(name = "ARCHIVE_NAME")
	private String archiveName; // 档案名称
	@Column(name = "REMARK")
	private String remark; // 备注
	@Column(name = "HASCHILD")
	private Integer hasChild;// 是否分级 0代表不分级 1代表分级
	@Column(name = "OPERATING_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime;// 操作时间
	@Column(name = "OPERATOR")
	private String operator; // 操作员
	@Column(name = "OPERATING_COMPANY")
	private String operatingCompany; // 操作单位
	@Transient
	private Set<SubArchive> subArchives = new HashSet<>(); // 子档案

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArchiveNum() {
		return archiveNum;
	}

	public void setArchiveNum(String archiveNum) {
		this.archiveNum = archiveNum;
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Set<SubArchive> getSubArchives() {
		return subArchives;
	}

	public void setSubArchives(Set<SubArchive> subArchives) {
		this.subArchives = subArchives;
	}

	public String getOperatingCompany() {
		return operatingCompany;
	}

	public void setOperatingCompany(String operatingCompany) {
		this.operatingCompany = operatingCompany;
	}

	public Integer getHasChild() {
		return hasChild;
	}

	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}

}
