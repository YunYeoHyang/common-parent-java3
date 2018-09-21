package com.czxy.bos.vo;

import java.util.List;

public class PageResult<T> {
	private long totalCount;
	private List<T> pageData;
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public PageResult() {
		super();
	}
	public PageResult(long totalCount, List<T> pageData) {
		super();
		this.totalCount = totalCount;
		this.pageData = pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
}

