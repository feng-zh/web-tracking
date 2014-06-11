package org.fengzh.tools.web.tracking;

import java.util.Map;

public class TrackingStatistic {
	private int totalCount;
	private int uniqRemoteAddrCount;
	private int uniqUserCount;
	private int newUserCount;
	private Map<String, ? extends Number> osData;
	private Map<String, ? extends Number> browserData;

	public void setOsData(Map<String, ? extends Number> osData) {
		this.osData = osData;
	}

	public void setBrowserData(Map<String, ? extends Number> browserData) {
		this.browserData = browserData;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getUniqRemoteAddrCount() {
		return uniqRemoteAddrCount;
	}

	public void setUniqRemoteAddrCount(int uniqRemoteAddrCount) {
		this.uniqRemoteAddrCount = uniqRemoteAddrCount;
	}

	public int getUniqUserCount() {
		return uniqUserCount;
	}

	public void setUniqUserCount(int uniqUserCount) {
		this.uniqUserCount = uniqUserCount;
	}

	public int getNewUserCount() {
		return newUserCount;
	}

	public void setNewUserCount(int newUserCount) {
		this.newUserCount = newUserCount;
	}

	public Map<String, ? extends Number> getOsData() {
		return osData;
	}

	public Map<String, ? extends Number> getBrowserData() {
		return browserData;
	}

}
