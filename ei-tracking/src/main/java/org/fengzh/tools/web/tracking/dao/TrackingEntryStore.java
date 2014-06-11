package org.fengzh.tools.web.tracking.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fengzh.tools.web.tracking.TopicStatistic;
import org.fengzh.tools.web.tracking.TrackingEntry;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TrackingEntryStore {
	private ExecutorService backend = Executors.newSingleThreadExecutor();

	@Autowired(required = true)
	private HibernateTemplate template;

	public HibernateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@SuppressWarnings("unchecked")
	public List<TrackingEntry> listEntriesByDateRange(Date fromDate,
			Date toDate, boolean includeIgnore) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(TrackingEntry.class);
		criteria.add(Restrictions.between("time", fromDate, toDate));
		if (!includeIgnore) {
			criteria.add(Restrictions.eq("ignore", false));
		}
		criteria.addOrder(Order.asc("time"));
		return template.findByCriteria(criteria);
	}

	public void saveEntry(final TrackingEntry entry) {
		backend.execute(new Runnable() {
			public void run() {
				saveEntry0(entry);
			}

		});
	}

	private void saveEntry0(TrackingEntry entry) {
		template.save(entry);
	}

	@SuppressWarnings("unchecked")
	public List<TrackingEntry> listEntriesByUid(Date fromDate, Date toDate,
			String uid) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(TrackingEntry.class);
		criteria.add(Restrictions.between("time", fromDate, toDate));
		criteria.add(Restrictions.eq("uid", uid));
		criteria.addOrder(Order.asc("time"));
		return template.findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<TrackingEntry> listEntriesByRemoteAddr(Date fromDate,
			Date toDate, String remoteAddr) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(TrackingEntry.class);
		criteria.add(Restrictions.between("time", fromDate, toDate));
		criteria.add(Restrictions.eq("remoteAddr", remoteAddr));
		criteria.addOrder(Order.asc("time"));
		return template.findByCriteria(criteria);
	}

	public List<TopicStatistic> listTipicStatisticsByDateRange(Date fromDate,
			Date toDate, boolean includeIgnore) {
		return Collections.emptyList();
	}

}
