package com.czxy.bos.vo;

import org.springframework.http.HttpStatus;

import java.util.List;

public class EasyUIResult<T> {
	private long total;
	private List<T> rows;
	public EasyUIResult() {
		super();
	}
	public EasyUIResult(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
