package com.czxy.bos.domain.transit;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.czxy.bos.domain.take_delivery.WayBill;

/**
 * @description: 运输配送信息
 */
@Entity
@Table(name = "T_TRANSIT_INFO")
public class TransitInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY , generator = "JDBC")
	@Column(name = "ID")
	private Integer id;

	@Transient
	private WayBill wayBill;
	@Column(name = "WAYBILL_ID")
	private Integer wayBillId;

	@Transient
	private List<InOutStorageInfo> inOutStorageInfos;


	@Transient
	private DeliveryInfo deliveryInfo;
	@Column(name = "DELIVERY_INFO_ID")
	private Integer deliveryInfoId;

	@Transient
	private SignInfo signInfo;
	@Column(name = "SIGN_INFO_ID")
	private Integer signInfoId;
	@Column(name = "STATUS")
	// 出入库中转、到达网点、开始配置、正常签收、异常
	private String status;

	@Column(name = "OUTLET_ADDRESS")
	private String outletAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WayBill getWayBill() {
		return wayBill;
	}

	public void setWayBill(WayBill wayBill) {
		this.wayBill = wayBill;
	}

	public List<InOutStorageInfo> getInOutStorageInfos() {
		return inOutStorageInfos;
	}

	public void setInOutStorageInfos(List<InOutStorageInfo> inOutStorageInfos) {
		this.inOutStorageInfos = inOutStorageInfos;
	}

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public SignInfo getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(SignInfo signInfo) {
		this.signInfo = signInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutletAddress() {
		return outletAddress;
	}

	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}
	public Integer getWayBillId() {
		return wayBillId;
	}

	public void setWayBillId(Integer wayBillId) {
		this.wayBillId = wayBillId;
	}

	public Integer getDeliveryInfoId() {
		return deliveryInfoId;
	}

	public void setDeliveryInfoId(Integer deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
	}

	public Integer getSignInfoId() {
		return signInfoId;
	}

	public void setSignInfoId(Integer signInfoId) {
		this.signInfoId = signInfoId;
	}
	@Transient//表示不会生成数据库表的列
	public String getTransferInfo(){
		StringBuffer buffer = new StringBuffer();
		// 添加出入库信息
		if(inOutStorageInfos != null && inOutStorageInfos.size()>0){
			// 添加出入库信息
			for(InOutStorageInfo inOutStorageInfo:inOutStorageInfos){
				buffer.append(inOutStorageInfo.getDescription() + "<br/>");
			}
		}
		// 添加配送信息
		if(deliveryInfo != null){
			buffer.append(deliveryInfo.getDescription() + "<br/>");
		}
		// 添加签收信息
		if(signInfo != null){
			buffer.append(signInfo.getDescription() + "<br/>");
		}
		return buffer.toString();
	}
}
