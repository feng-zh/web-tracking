package org.fengzh.tools.web.tracking.web;

import java.util.Date;

import org.fengzh.tools.web.tracking.TrackingPeriod;

public class ChartForm {
	private Date startDate;
	private Date endDate;
	private TrackingPeriod period = TrackingPeriod.HALF_HOUR;
	private boolean includeIgnore = false;

	public boolean isIncludeIgnore() {
		return includeIgnore;
	}

	public void setIncludeIgnore(boolean includeIgnore) {
		this.includeIgnore = includeIgnore;
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

	public TrackingPeriod getPeriod() {
		return period;
	}

	public void setPeriod(TrackingPeriod period) {
		this.period = period;
	}

}
