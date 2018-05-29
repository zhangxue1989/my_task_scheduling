package com.zx.qm.manager;


public class AbstractQuartzManager {
	
	private final static String PREFIX_JOB_NAME = "quartz"; 
	
	private final static String SUFFIX_JOB_NAME = "job"; 
	
	private final static String PREFIX_TRIGGER_NAME = "quartz"; 
	
	private final static String SUFFIX_TRIGGER_NAME = "trigger"; 
	
	
	protected static String getJobName(String jobName) {
		return PREFIX_JOB_NAME + ":" + jobName  +":" +SUFFIX_JOB_NAME;
	}

	protected static String getTriggerName(String triggerName) {
		return PREFIX_TRIGGER_NAME + ":" + triggerName  +":" +SUFFIX_TRIGGER_NAME;
	}
	
	
}