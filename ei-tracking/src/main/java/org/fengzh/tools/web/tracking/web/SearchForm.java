package org.fengzh.tools.web.tracking.web;

import java.util.Date;

public class SearchForm {
	private Date startDate;
	private Date endDate;
	private boolean includeIgnore = false;
	private String pageString;
	private String remoteAddr;

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

	public boolean isIncludeIgnore() {
		return includeIgnore;
	}

	public void setIncludeIgnore(boolean includeIgnore) {
		this.includeIgnore = includeIgnore;
	}

	public String getPageString() {
		return pageString;
	}

	public void setPageString(String pageString) {
		this.pageString = pageString;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

}
