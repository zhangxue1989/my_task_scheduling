package com.zx.qm.model;

import java.util.Date;
import java.util.Map;

/**
 * @Project: my_task_scheduling
 * @Title: JobInfo
 * @Description: 任务的详情
 * @author: zhangxue
 * @date: 2018年5月29日下午9:38:28
 * @company: yooli
 * @Copyright: Copyright (c) 2015
 * @version v1.0
 */
public class JobInfo {

	private String jobName;

	private String jobGroupName;

	private String triggerName;

	private String triggerGroupName;

	private String corn;

	private String jobClass;

	private String jobClassSimleName;

	private Date nextFireTime;

	private Map<String, Object> params;
	
	private String jobStatus;

	public JobInfo() {	}

	public JobInfo(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String corn,
			String jobClass, String jobClassSimleName, Date nextFireTime, Map<String, Object> params) {
		super();
		this.jobName = jobName;
		this.jobGroupName = jobGroupName;
		this.triggerName = triggerName;
		this.triggerGroupName = triggerGroupName;
		this.corn = corn;
		this.jobClass = jobClass;
		this.jobClassSimleName = jobClassSimleName;
		this.nextFireTime = nextFireTime;
		this.params = params;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getCorn() {
		return corn;
	}

	public void setCorn(String corn) {
		this.corn = corn;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobClassSimleName() {
		return jobClassSimleName;
	}

	public void setJobClassSimleName(String jobClassSimleName) {
		this.jobClassSimleName = jobClassSimleName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Override
	public String toString() {
		return "JobInfo [jobName=" + jobName + ", triggerName=" + triggerName + ", corn=" + corn + ", jobClass="
				+ jobClass + ", jobClassSimleName=" + jobClassSimleName + ", params=" + params + "]";
	}

}
