package com.czxy.bos.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @description:分区
 */
@Entity
@Table(name = "T_SUB_AREA")
public class SubArea {

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "START_NUM")
	private String startNum; // 起始号
	@Column(name = "ENDNUM")
	private String endNum; // 终止号
	@Column(name = "SINGLE")
	private Character single; // 单双号
	@Column(name = "KEY_WORDS")
	private String keyWords; // 关键字
	@Column(name = "ASSIST_KEY_WORDS")
	private String assistKeyWords; // 辅助关键字

	@Column(name = "AREA_ID")
	private String areaId;
	@Transient
	private Area area; // 区域
	@Column(name = "FIXEDAREA_ID")
	private String fixedAreaId;
	@Transient
	private FixedArea fixedArea; // 定区

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Character getSingle() {
		return single;
	}

	public void setSingle(Character single) {
		this.single = single;
	}

	public String getStartNum() {
		return startNum;
	}

	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}

	public String getEndNum() {
		return endNum;
	}

	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getAssistKeyWords() {
		return assistKeyWords;
	}

	public void setAssistKeyWords(String assistKeyWords) {
		this.assistKeyWords = assistKeyWords;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public FixedArea getFixedArea() {
		return fixedArea;
	}

	public void setFixedArea(FixedArea fixedArea) {
		this.fixedArea = fixedArea;
	}

}
