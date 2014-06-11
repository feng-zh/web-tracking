package org.fengzh.tools.web.tracking;

public class TopicStatistic {

	private String topic;
	private String timeWindow;
	private int pageViewCount;
	private int uniqueViewCount;
	private TrackingPeriod timeUnit;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(String timeWindow) {
		this.timeWindow = timeWindow;
	}

	public int getPageViewCount() {
		return pageViewCount;
	}

	public void setPageViewCount(int pageViewCount) {
		this.pageViewCount = pageViewCount;
	}

	public int getUniqueViewCount() {
		return uniqueViewCount;
	}

	public void setUniqueViewCount(int uniqueViewCount) {
		this.uniqueViewCount = uniqueViewCount;
	}

	public TrackingPeriod getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TrackingPeriod timeUnit) {
		this.timeUnit = timeUnit;
	}

}
