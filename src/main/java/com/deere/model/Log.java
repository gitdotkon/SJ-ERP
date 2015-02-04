package com.deere.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Log extends BaseLog {
	private long id;
	private String logType;
	private String logTitle;
	private String logDetail;
	private Date logTime;

	/*
	 * private String logResult;
	 * private String logResultDetail;
	 * private String logTokenKey;
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogTitle() {
		return logTitle;
	}

	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}
	@Column(length = 3000)
	public String getLogDetail() {
		return logDetail;
	}

	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public Log(String logType, String logTitle, String logDetail) {
		this.logType = logType;
		this.logDetail = logDetail;
		this.logTitle = logTitle;
		this.logTime = new Date();
	}

	public Log() {
	}

}
