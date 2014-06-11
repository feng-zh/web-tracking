package org.fengzh.tools.web.tracking;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadablePeriod;

public enum TrackingPeriod {

	ONE_MIN(Period.minutes(1), PeriodType.minutes()), FIVE_MIN(Period
			.minutes(5), PeriodType.minutes()), HALF_HOUR(Period.minutes(30),
			PeriodType.minutes()), ONE_HOUR(Period.hours(1), PeriodType.hours()), HALF_DAY(
			Period.hours(12), PeriodType.hours()), ONE_DAY(Period.days(1),
			PeriodType.days()), ONE_WEEK(Period.weeks(1), PeriodType.weeks()), ONE_MONTH(
			Period.months(1), PeriodType.months()), THREE_MONTH(Period
			.months(3), PeriodType.months()), ONE_YEAR(Period.years(1),
			PeriodType.years());

	private Period period;
	private PeriodType periodType;

	private TrackingPeriod(Period period, PeriodType periodType) {
		this.period = period;
		this.periodType = periodType;
	}

	public String toString() {
		return period.toString();
	}

	public Period getPeriod() {
		return period;
	}

	public void next(MutableDateTime dateTime) {
		dateTime.add(period);
	}

	public MutableDateTime roundFloor(DateTime dateTime) {
		MutableDateTime newTime = new MutableDateTime(dateTime);
		if (periodType == PeriodType.years()) {
			// year start align
			newTime.year().roundFloor();
		} else if (periodType == PeriodType.months()) {
			// year start align
			newTime.year().roundFloor();
		} else if (periodType == PeriodType.weeks()) {
			// week start align
			newTime.weekOfWeekyear().roundFloor();
		} else if (periodType == PeriodType.days()) {
			// day start align
			newTime.dayOfMonth().roundFloor();
		} else if (periodType == PeriodType.hours()) {
			// day start align
			newTime.dayOfMonth().roundFloor();
		} else if (periodType == PeriodType.minutes()) {
			// hour start align
			newTime.hourOfDay().roundFloor();
		}
		return findNear(dateTime, newTime);
	}

	private MutableDateTime findNear(DateTime dateTime, MutableDateTime newTime) {
		ReadablePeriod p = period;
		if (newTime.isBefore(dateTime.minus(period))) {
			MutablePeriod newPeriod = new MutablePeriod(newTime, dateTime);
			for (int i = 0, n = period.size(); i < n; i++) {
				int pv = period.getValue(i);
				if (pv != 0) {
					pv = newPeriod.getValue(i) / pv * pv;
				}
				newPeriod.setValue(i, pv);
			}
			p = newPeriod;
		}
		newTime.add(p);
		return newTime;
	}
}
