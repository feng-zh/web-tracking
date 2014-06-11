package org.fengzh.tools.web.tracking;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public class TrackingSeries {
	public static class SeriesEntry {
		private int nullCount = 0;
		private Set<Object> set = new HashSet<Object>();
		private Date time;

		public SeriesEntry(Date time) {
			this.time = time;
		}

		public void addData(Object obj) {
			if (obj == null) {
				nullCount++;
			} else {
				set.add(obj);
			}
		}

		public int getCount() {
			return set.size() + nullCount;
		}

		public Set<Object> getData() {
			return set;
		}

		public Date getTime() {
			return time;
		}

	}

	public static class TrackingSeriesList {
		private DateTime minTime;
		private DateTime maxTime;
		private TrackingPeriod period;
		private Map<String, TrackingSeries> seriesList = new LinkedHashMap<String, TrackingSeries>();

		public void addSeriesEntry(String name, Date time, Object data) {
			TrackingSeries series = seriesList.get(name);
			if (series == null) {
				series = new TrackingSeries(name);
				seriesList.put(name, series);
			}
			series.addEntry(time, data);
		}

		public DateTime getMinTime() {
			return minTime;
		}

		public TrackingPeriod getPeriod() {
			return period;
		}

		public DateTime getMaxTime() {
			return maxTime;
		}

		public void setMaxTime(DateTime maxTime) {
			this.maxTime = maxTime;
		}

		public Map<Date, Map<String, Integer>> getFilledSeriesData() {
			return getSeriesData0(true);
		}

		public Map<Date, Map<String, Integer>> getSeriesData() {
			return getSeriesData0(false);
		}

		private Map<Date, Map<String, Integer>> getSeriesData0(boolean fill) {
			Map<Date, Map<String, Integer>> dataList = new TreeMap<Date, Map<String, Integer>>();
			if (fill) {
				MutableDateTime dt = new MutableDateTime(minTime);
				while (dt.isBefore(maxTime)) {
					dataList.put(dt.toDate(),
							new LinkedHashMap<String, Integer>());
					dt.add(period.getPeriod());
				}
			}
			for (Map.Entry<String, TrackingSeries> entry : seriesList
					.entrySet()) {
				for (SeriesEntry seriesEntry : entry.getValue().getEntries()) {
					Map<String, Integer> map = dataList.get(seriesEntry
							.getTime());
					if (map != null) {
						map.put(entry.getKey(), seriesEntry.getCount());
					}
				}
			}
			return dataList;
		}

		public Set<String> getSeriesName() {
			return seriesList.keySet();
		}

		public void setMinTime(DateTime minTime) {
			this.minTime = minTime;
		}

		public void setPeriod(TrackingPeriod period) {
			this.period = period;
		}
	}

	private LinkedList<SeriesEntry> entries = new LinkedList<TrackingSeries.SeriesEntry>();

	private String name;

	public TrackingSeries() {
	}

	public TrackingSeries(String name) {
		this.name = name;
	}

	public void addEntry(Date time, Object data) {
		if (entries.isEmpty()) {
			entries.add(new SeriesEntry(time));
		}
		SeriesEntry last = entries.getLast();
		if (last.getTime() == null || time == null) {
			System.out.println("STOP");
		}
		if (last.getTime().getTime() != time.getTime()) {
			// find and add
			ListIterator<SeriesEntry> iterator = entries.listIterator(entries
					.size());
			while (iterator.hasPrevious()) {
				SeriesEntry entry = iterator.previous();
				int compare = entry.time.compareTo(time);
				if (compare == 0) {
					// same time
					last = entry;
					break;
				} else if (compare < 0) {
					// got new time
					last = new SeriesEntry(time);
					// move to next (which is in previous)
					iterator.next();
					iterator.add(last);
					break;
				}
				// got old time
			}
			if (!iterator.hasPrevious()) {
				// it is oldest time
				last = new SeriesEntry(time);
				iterator.add(last);
			}
		}
		last.addData(data);
	}

	public List<SeriesEntry> getEntries() {
		return entries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
