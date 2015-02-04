package com.deere.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: LogInformation
 * @Description: Use this model to save system exception and error
 * @author Gavin Li ligavin@johndeere.com
 * @date Sep 26, 2014 6:31:11 PM
 * 
 */
@Entity
@Table(name = "log_information")
public class SystemLog extends BaseLog {

	private long id;
	private String type;
	private String className;
	private String classMethod;
	private String methodParameter;
	private String rootCause;
	private String information;
	private String detail;
	private Date occurTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "class_name")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "class_method")
	public String getClassMethod() {
		return classMethod;
	}

	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}

	@Column(name = "root_cause", length = 3000)
	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	@Column(name = "information", length = 3000)
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Column(name = "occur_time")
	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	@Column(name = "detail", length = 3000)
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name = "method_parameter", length = 3000)
	public String getMethodParameter() {
		return methodParameter;
	}

	public void setMethodParameter(String methodParameter) {
		this.methodParameter = methodParameter;
	}
}
