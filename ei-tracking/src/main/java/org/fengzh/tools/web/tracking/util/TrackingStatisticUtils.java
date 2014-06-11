package org.fengzh.tools.web.tracking.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.fengzh.tools.web.tracking.TrackingEntry;
import org.fengzh.tools.web.tracking.TrackingPeriod;
import org.fengzh.tools.web.tracking.TrackingSeries.TrackingSeriesList;
import org.fengzh.tools.web.tracking.TrackingStatistic;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public class TrackingStatisticUtils {
	public static TrackingStatistic createStatistic(
			List<TrackingEntry> entryList) {
		Set<String> uniqRemoteAddr = new HashSet<String>();
		Set<String> uniqUser = new HashSet<String>();
		int newUserCount = 0;
		Map<String, AtomicInteger> osData = new HashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> browserData = new HashMap<String, AtomicInteger>();
		for (TrackingEntry entry : entryList) {
			uniqRemoteAddr.add(entry.getRemoteAddr());
			uniqUser.add(entry.getUid());
			increaseCount(osData, entry.getOsInfo());
			increaseCount(browserData, entry.getBrowserInfo());
			if (!entry.isReturnUser()) {
				newUserCount++;
			}
		}

		TrackingStatistic statistic = new TrackingStatistic();
		statistic.setTotalCount(entryList.size());
		statistic.setNewUserCount(newUserCount);
		statistic.setUniqRemoteAddrCount(uniqRemoteAddr.size());
		statistic.setUniqUserCount(uniqUser.size());
		statistic.setBrowserData(browserData);
		statistic.setOsData(osData);
		return statistic;
	}

	public static TrackingSeriesList createSeries(
			List<TrackingEntry> orderedEntryList, TrackingPeriod trackingPeriod) {
		TrackingSeriesList seriesList = new TrackingSeriesList();
		seriesList.setPeriod(trackingPeriod);
		MutableDateTime dateTime = trackingPeriod.roundFloor(orderedEntryList
				.isEmpty() ? DateTime.now() : new DateTime(orderedEntryList
				.get(0).getTime()));
		seriesList.setMinTime(dateTime.toDateTime());
		Date seriesEntryTime = null;
		for (TrackingEntry entry : orderedEntryList) {
			while (dateTime.getMillis() <= entry.getTime().getTime()) {
				seriesEntryTime = dateTime.toDate();
				trackingPeriod.next(dateTime);
			}
			if (seriesEntryTime == null) {
				seriesEntryTime = new Date(dateTime.getMillis());
			}
			sumSeries(seriesList, seriesEntryTime, entry);
		}
		seriesList.setMaxTime(dateTime.toDateTime());
		return seriesList;
	}

	private static void sumSeries(TrackingSeriesList seriesList, Date time,
			TrackingEntry entry) {
		seriesList.addSeriesEntry("Total Count", time, null);
		seriesList.addSeriesEntry("Visitor", time, entry.getUid());
		seriesList.addSeriesEntry("Client IP", time, entry.getRemoteAddr());
		if (!entry.isReturnUser()) {
			seriesList.addSeriesEntry("New Visitor", time, entry.getUid());
		}
	}

	private static void increaseCount(Map<String, AtomicInteger> data,
			String key) {
		AtomicInteger count = data.get(key);
		if (count == null) {
			count = new AtomicInteger();
			data.put(key, count);
		}
		count.incrementAndGet();
	}
}
