package com.czxy.bos.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_FIXEDAREA_COURIER")
public class FixedAreaCourier {
	@Column(name="FIXED_AREA_ID")
	private String fixedAreaId;
	@Column(name="COURIER_ID")
	private Integer courierId;
	public String getFixedAreaId() {
		return fixedAreaId;
	}
	public void setFixedAreaId(String fixedAreaId) {
		this.fixedAreaId = fixedAreaId;
	}
	public Integer getCourierId() {
		return courierId;
	}
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	
	
	
	
	
}
