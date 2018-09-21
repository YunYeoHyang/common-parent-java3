package com.czxy.bos.domain.base;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @description:快递员
 */
@Entity
@Table(name = "T_COURIER")
public class Courier {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id; // 主键
	@Column(name = "COURIER_NUM", unique = true)
	private String courierNum; // 快递员工号
	@Column(name = "NAME")
	private String name; // 快递员姓名
	@Column(name = "TELEPHONE")
	private String telephone; // 快递员联系电话
	@Column(name = "PDA")
	private String pda; // PDA号
	@Column(name = "DELTAG")
	private Character deltag; // 作废标志 1 为标记作废
	@Column(name = "CHECK_PWD")
	private String checkPwd; // 查台密码
	@Column(name = "TYPE")
	private String type; // 取件员类型
	@Column(name = "COMPANY")
	private String company; // 所属单位
	@Column(name = "VEHICLE_TYPE")
	private String vehicleType; // 车辆类型
	@Column(name = "VEHICLE_NUM")
	private String vehicleNum; // 车牌号
	@Column(name = "STANDARD_ID")
	private Integer standardId;		//收派标准外键
	@Column(name = "TAKETIME_ID")
	private Integer takeTimeId;		//收派时间外键
	
	//用户名+company组合，用于快递员分配定区使用
	@Transient
	private String info;
	
	
	@Transient
	private Standard standard;		//收派标准
	@Transient
	private TakeTime takeTime;		//收派时间
	@Transient
	private Set<FixedArea> fixedAreas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourierNum() {
		return courierNum;
	}

	public void setCourierNum(String courierNum) {
		this.courierNum = courierNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Character getDeltag() {
		return deltag;
	}

	public void setDeltag(Character deltag) {
		this.deltag = deltag;
	}

	public String getCheckPwd() {
		return checkPwd;
	}

	public void setCheckPwd(String checkPwd) {
		this.checkPwd = checkPwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public String getPda() {
		return pda;
	}

	public void setPda(String pda) {
		this.pda = pda;
	}

	public Integer getStandardId() {
		return standardId;
	}

	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}

	
	public Integer getTakeTimeId() {
		return takeTimeId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	public TakeTime getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(TakeTime takeTime) {
		this.takeTime = takeTime;
	}

	public Set<FixedArea> getFixedAreas() {
		return fixedAreas;
	}

	public void setFixedAreas(Set<FixedArea> fixedAreas) {
		this.fixedAreas = fixedAreas;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
