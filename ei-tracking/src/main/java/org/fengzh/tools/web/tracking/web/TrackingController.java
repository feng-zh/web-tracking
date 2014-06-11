package org.fengzh.tools.web.tracking.web;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fengzh.tools.web.tracking.TrackingEntry;
import org.fengzh.tools.web.tracking.TrackingSeries.TrackingSeriesList;
import org.fengzh.tools.web.tracking.dao.TrackingEntryStore;
import org.fengzh.tools.web.tracking.util.TrackingStatisticUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class TrackingController {

	private static final String CookieUid = "EIUSER_IDENTITY";

	private static final String CookieIgnoreMe = "EIUSER_IGNORE";

	@Autowired(required = true)
	private TrackingEntryStore store;

	private static final String ImagePath = "/spacer.gif";

	public TrackingEntryStore getStore() {
		return store;
	}

	public void setStore(TrackingEntryStore store) {
		this.store = store;
	}

	@RequestMapping("/entry")
	public void entry(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = RequestContextUtils.getWebApplicationContext(
				request).getServletContext();
		String referFrom = request.getHeader("Referer");
		String queryString = request.getQueryString();
		Cookie[] cookies = request.getCookies();
		String webAgent = request.getHeader("User-Agent");
		String remoteAddr = getRemoteAddress(request);
		String target = request.getParameter("target");

		TrackingEntry entry = new TrackingEntry();
		entry.setReferFrom(referFrom);
		entry.setQueryString(queryString != null ? URLDecoder.decode(
				queryString, "UTF-8") : null);
		entry.setRemoteAddr(remoteAddr);
		entry.setTime(new Date());
		entry.setWebAgent(webAgent);

		boolean ignore = getCookie(cookies, CookieIgnoreMe) != null;
		entry.setIgnore(ignore);

		String uid = getUid(request);
		entry.setReturnUser(uid != null);
		if (uid == null) {
			uid = createUid();
			Cookie uidCookie = new Cookie(CookieUid, uid);
			uidCookie.setMaxAge(180 * 24 * 60 * 60); // 180 days
			response.addCookie(uidCookie);
		}
		entry.setUid(uid);

		record(entry);

		response.setHeader("Cache-Control",
				"no-cache, must-revalidate, private, max-age=0, proxy-revalidate");
		response.setDateHeader("Expires", System.currentTimeMillis() - 1000
				* 60 * 60 * 24L);
		response.setHeader("Pragma", "no-cache");
		if (target == null) {
			byte[] image = loadImage(context);
			response.setContentType(context.getMimeType(ImagePath
					.substring(ImagePath.indexOf('.') + 1)));
			response.setContentLength(image.length);
			response.getOutputStream().write(image);
			response.flushBuffer();
		} else {
			response.sendRedirect(target);
		}

	}

	private byte[] loadImage(ServletContext context) throws IOException {
		byte[] image = (byte[]) context.getAttribute(ImagePath);
		if (image == null) {
			InputStream stream = getClass().getResourceAsStream(ImagePath);
			if (stream == null) {
				throw new FileNotFoundException("Not found: " + ImagePath);
			}
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = stream.read(b)) != -1) {
				buffer.write(b, 0, len);
			}
			image = buffer.toByteArray();
			context.setAttribute(ImagePath, image);
		}
		return image;
	}

	private String getRemoteAddress(HttpServletRequest request) {
		String remoteAddr = request.getRemoteHost();
		String proxyForward = request.getHeader("X-Forwarded-For");
		if (proxyForward != null && proxyForward.trim().length() > 0) {
			remoteAddr = remoteAddr + "; " + proxyForward;
		}
		if (isFromProxy(request)) {
			remoteAddr = remoteAddr + " (proxy)";
		}
		return remoteAddr;
	}

	private String getUid(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		return getCookie(cookies, CookieUid);
	}

	private void record(TrackingEntry entry) {
		store.saveEntry(entry);
	}

	private String createUid() {
		return UUID.randomUUID().toString();
	}

	private String getCookie(Cookie[] cookies, String cookieName) {
		if (cookies == null)
			return null;
		for (Cookie c : cookies) {
			if (cookieName.equals(c.getName())) {
				return c.getValue();
			}
		}
		return null;
	}

	@RequestMapping("/list")
	public String list(SearchForm form, Map<String, Object> model) {
		initSearchDate(form);
		List<TrackingEntry> list = store.listEntriesByDateRange(
				form.getStartDate(), form.getEndDate(), form.isIncludeIgnore());
		model.put("list", list);
		model.put("command", form);
		model.put("statistics", TrackingStatisticUtils.createStatistic(list));
		return "list";
	}

	@RequestMapping("/chart")
	public String chart(ChartForm form, Map<String, Object> model) {
		initSearchDate(form);
		List<TrackingEntry> list = store.listEntriesByDateRange(
				form.getStartDate(), form.getEndDate(), form.isIncludeIgnore());
		model.put("list", list);
		TrackingSeriesList seriesList = TrackingStatisticUtils.createSeries(
				list, form.getPeriod());
		model.put("seriesList", seriesList);
		return "chart";
	}

	@RequestMapping("/uid")
	public String listCurrentByUid(SearchForm form, Map<String, Object> model,
			HttpServletRequest request) {
		String uid = getUid(request);
		model.put("uid", uid);
		if (uid != null) {
			initSearchDate(form);
			List<TrackingEntry> list = store.listEntriesByUid(
					form.getStartDate(), form.getEndDate(), uid);
			model.put("list", list);
			model.put("statistics",
					TrackingStatisticUtils.createStatistic(list));
		}
		model.put("command", form);
		model.put("mode", "uid");
		return "listme";
	}

	@RequestMapping("/ip")
	public String listCurrentByIp(SearchForm form, Map<String, Object> model,
			HttpServletRequest request) {
		if (isFromProxy(request)) {
			model.put("error", "You are coming from Proxy, no list for this IP");
			return "listme";
		}
		String remoteAddr = getRemoteAddress(request);
		model.put("remoteAddr", remoteAddr);
		if (remoteAddr != null) {
			initSearchDate(form);
			List<TrackingEntry> list = store.listEntriesByRemoteAddr(
					form.getStartDate(), form.getEndDate(), remoteAddr);
			model.put("list", list);
			model.put("statistics",
					TrackingStatisticUtils.createStatistic(list));
		}
		model.put("command", form);
		model.put("mode", "ip");
		return "listme";
	}

	private boolean isFromProxy(HttpServletRequest request) {
		return request.getHeader("X-BlueCoat-Via") != null;
	}

	@RequestMapping("/ignoreme")
	public String ignoreme(
			@RequestParam(value = "ignoremeSet", required = false) boolean enableIgnoreMe,
			@RequestParam(value = "submit", required = false) String submit,
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		boolean ignoremeSet = getCookie(cookies, CookieIgnoreMe) != null;
		if (submit != null) {
			if (enableIgnoreMe) {
				Cookie cookie = new Cookie(CookieIgnoreMe, "1");
				cookie.setMaxAge(365 * 24 * 60 * 60);// 1 years
				response.addCookie(cookie);
				ignoremeSet = true;
			} else if (ignoremeSet) {
				Cookie cookie = new Cookie(CookieIgnoreMe, "0");
				cookie.setMaxAge(0);// clear
				response.addCookie(cookie);
				ignoremeSet = false;
			}
			model.put("submit", submit);
		}
		model.put("ignoremeSet", ignoremeSet);
		return "ignoreme";
	}

	private void initSearchDate(SearchForm form) {
		DateTime date = new DateTime();
		if (form.getEndDate() == null) {
			form.setEndDate(date.toDate());
		}
		if (form.getStartDate() == null) {
			form.setStartDate(date.minusWeeks(1).withMillisOfDay(0).toDate());
		}
	}

	private void initSearchDate(ChartForm form) {
		DateTime date = new DateTime();
		if (form.getEndDate() == null) {
			form.setEndDate(date.toDate());
		}
		if (form.getStartDate() == null) {
			form.setStartDate(date.minusWeeks(1).withMillisOfDay(0).toDate());
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
	}
}
