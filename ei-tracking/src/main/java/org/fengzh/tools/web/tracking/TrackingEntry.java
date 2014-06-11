package org.fengzh.tools.web.tracking;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nl.bitwalker.useragentutils.DeviceType;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

@Entity(name = "TrackingEntry")
public class TrackingEntry implements Serializable {

	private static final long serialVersionUID = 1541186816690011512L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "entryId")
	private long id;

	@Column(name = "time")
	private Date time;

	@Column(name = "referFrom")
	private String referFrom;

	@Column(name = "queryString")
	private String queryString;

	@Column(name = "remoteAddr")
	private String remoteAddr;

	@Column(name = "webAgent")
	private String webAgent;

	@Column(name = "ignore")
	private boolean ignore;

	@Column(name = "uid")
	private String uid;

	@Column(name = "returnUser")
	private boolean returnUser;

	private transient UserAgent userAgent;

	public String getBrowserInfo() {
		UserAgent agent = getUserAgent();
		if (agent.getBrowserVersion() == null) {
			System.out.println(webAgent);
		}
		return agent.getBrowser().getName();
		// + (agent.getBrowser().getGroup() == agent.getBrowser() ? " "
		// + agent.getBrowserVersion() : "");
	}

	public UserAgent getUserAgent() {
		if (userAgent == null) {
			userAgent = UserAgent.parseUserAgentString(getWebAgent());
		}
		return userAgent;
	}

	public String getClientAccess() {
		if (getRemoteAddr().indexOf("proxy") != -1) {
			return "via proxy";
		} else {
			return "direct access";
		}
	}

	public String getOsInfo() {
		OperatingSystem os = getUserAgent().getOperatingSystem();
		return os.getName()
				+ (os.getDeviceType() == DeviceType.COMPUTER ? "" : " "
						+ os.getDeviceType().getName());
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getReferFrom() {
		return referFrom;
	}

	public void setReferFrom(String referFrom) {
		this.referFrom = referFrom;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getWebAgent() {
		return webAgent;
	}

	public void setWebAgent(String webAgent) {
		this.webAgent = webAgent;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean isReturnUser() {
		return returnUser;
	}

	public void setReturnUser(boolean returnUser) {
		this.returnUser = returnUser;
	}

	public long getId() {
		return id;
	}

}
