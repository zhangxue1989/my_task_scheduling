package com.zx.qm.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.zx.qm.model.CornJobInfo;
import com.zx.qm.util.Assert;
import com.zx.qm.util.TriggerStateUtil;

/**
 * @Project: my_task_scheduling
 * @Title: CronQuartzManager
 * @Description: 时间表达式，任务管理类。
 * 	jobGroup 和 triggerGroup 默认不用写，统一管理。
 *  jobName 和 triggerName 默认输入值是一样的，输出值不同。
 * @author: zhangxue
 * @date: 2018年5月29日下午9:52:31
 * @company: alibaba
 * @Copyright: Copyright (c) 2015
 * @version v1.0
 */
public  class CronQuartzManager extends AbstractQuartzManager {

	public static final SchedulerFactory STD_SCHEDULER_FACTORY;
	
	/**
	 * 定时任务的线程组名称
	 */
	private static final String DEFAULT_CRON_JOB_GROUP_NAME = "defaultCronJobGroupName";
	
	/**
	 * 定时任务的触发器的线程组名称
	 */
	private static final String DEFAULT_CRON_TRIGGER_GROUP_NAME = "defaultCronTriggerGroupName";
	
	static {
		STD_SCHEDULER_FACTORY= new StdSchedulerFactory();
	}
	
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * @param jobName 任务名	triggerName
	 * @param cls 任务
	 * @param time 时间设置，参考quartz说明文档
	 * @Title: QuartzManager
	 * @author mzm
	 * @date 2017-7-26 下午03:47:44
	 * @version V2.0
	 */
	public static boolean addJob(String jobName, Class<? extends Job> jobClass, String cron, Map<String, Object> params) throws Exception {
		return addJob(jobName, DEFAULT_CRON_JOB_GROUP_NAME, jobName, DEFAULT_CRON_TRIGGER_GROUP_NAME, jobClass, cron, params);
	}

	/**
	 * @Description: 添加一个定时任务
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param triggerName 触发器名
	 * @param triggerGroupName 触发器组名
	 * @param jobClass 任务
	 * @param time 时间设置，参考quartz说明文档
	 * @author mzm
	 * @date 2017-7-26 下午03:48:15
	 * @version V2.0
	 * @throws Exception 
	 */
	private static boolean addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class<? extends Job> jobClass, String cron, Map<String, Object> params) throws Exception {
		if(Assert.isEmpty(jobName, jobGroupName, triggerName, triggerGroupName, cron) || Assert.isEmpty(jobClass)) {
			return false;
		}
		if(Assert.isEmpty(params)) {
			params = new HashMap<>();
		}
		try {
			JobDetail job = JobBuilder
					.newJob(jobClass)//任务类
					.withIdentity(getJobName(jobName), jobGroupName)// 任务名，任务组，任务执行类
					.usingJobData(new JobDataMap(params))//注入参数
					.build();
			
			// 触发器
			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setName(getTriggerName(triggerName));
			trigger.setGroup(triggerGroupName);
			trigger.setCronExpression(cron);
			
			Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName	默认jobName与triggerName相同
	 * @param time
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:49:21
	 * @version V2.0
	 * @throws Exception 
	 */
	public static boolean modifyJobTime(String jobName, String cron) throws Exception {
		return modifyJobTime(jobName, DEFAULT_CRON_TRIGGER_GROUP_NAME, cron);
	}
	
	/**
	 * @Description: 修改一个任务的触发时间
	 * @param triggerName	默认jobName与triggerName相同
	 * @param triggerGroupName
	 * @param time
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:49:37
	 * @version V2.0
	 * @throws Exception 
	 */
	private static boolean modifyJobTime(String triggerName, String triggerGroupName, String cron) throws Exception {
		if(Assert.isEmpty(triggerName, triggerGroupName, cron)) {
			return false;
		}
		
		try {
			Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
			TriggerKey triggerKey = new TriggerKey(getTriggerName(triggerName), triggerGroupName);
			Trigger trigger = scheduler.getTrigger(triggerKey);
			if(trigger == null) {
				return false;
			}
			/* quertz-1.6.3 版本支持的修改触发时间的方法。在2+以上的版本不兼容了。 
			CronTriggerImpl cronTrigger = (CronTriggerImpl)trigger;
			String oldCronExpression = cronTrigger.getCronExpression();
			if (!oldCronExpression.equalsIgnoreCase(cron)) {
				// 修改时间
				cronTrigger.setCronExpression(cron);
				// 重启触发器
				scheduler.resumeTrigger(triggerKey);
			}
			*/
			// 新的触发器
			CronTriggerImpl newTrigger = new CronTriggerImpl();
			newTrigger.setName(getTriggerName(triggerName));
			newTrigger.setGroup(triggerGroupName);
			newTrigger.setCronExpression(cron);
			
			scheduler.rescheduleJob(triggerKey, newTrigger);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName	默认jobName与triggerName相同
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:49:51
	 * @version V2.0
	 * @throws Exception 
	 */
	public static boolean removeJob(String jobName) throws Exception {
		return removeJob(jobName, DEFAULT_CRON_JOB_GROUP_NAME, jobName, DEFAULT_CRON_TRIGGER_GROUP_NAME);
	}

	/**
	 * @Description: 移除一个任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * 
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:50:01
	 * @version V2.0
	 * @throws Exception 
	 */
	private static boolean removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws Exception {
		if(Assert.isEmpty(jobName, jobGroupName, triggerName, triggerGroupName)) {
			return false;
		}
		
		try {
			Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
			
			TriggerKey triggerKey = new TriggerKey(getTriggerName(triggerName), triggerGroupName);
			JobKey jobKey = new JobKey(getJobName(jobName), jobGroupName);
			
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(jobKey);// 删除任务
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Description:启动所有定时任务
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:50:18
	 * @version V2.0
	 * @throws Exception 
	 */
	@Deprecated//无法使用，原理理解错误
	public static boolean startJobs() throws Exception {
		try {
			Scheduler sched = STD_SCHEDULER_FACTORY.getScheduler();
			sched.start();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Description:关闭所有定时任务,执行后全部任务被删除
	 * @Title: QuartzManager
	 * @author mzm
	 * @date 2017-7-26 下午03:50:26
	 * @version V2.0
	 * @throws Exception 
	 */
	public static boolean shutdownJobs() throws Exception {
		try {
			Scheduler sched = STD_SCHEDULER_FACTORY.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Description:停止所有的任务,执行可再次被启动
	 * @Title: QuartzManager
	 * @author mzm
	 * @date 2017-7-26 下午03:50:26
	 * @version V2.0
	 * @throws Exception 
	 */
	public static boolean stopJobs() throws Exception {
		try {
			Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
			for (String groupName : scheduler.getJobGroupNames()) {
	            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
	            	scheduler.pauseJob(jobKey);//暂停任务
	            }
	        }
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * @Title:定时任务是否在运行 
	 * @return	当没有运行的项目或所有的任务都停止的时候，返回false。
	 * @author: zhangx
	 * @date: 2017年8月1日 下午7:09:56
	 * @version v1.0
	 * @throws SchedulerException 
	 */
	public static boolean isRun() throws SchedulerException{
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		return scheduler.isStarted();
	}
	
	/**
	 * 任务是否已经存在
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 * @author: xue.zhang
	 * @date 2018年5月28日下午5:52:23
	 */
	public static boolean isExistsJob(String jobName) throws SchedulerException {
		if(Assert.isEmpty(jobName)) {
			return false;
		}
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		JobKey jobKey = new JobKey(getJobName(jobName), DEFAULT_CRON_JOB_GROUP_NAME);
		return scheduler.checkExists(jobKey);
	}

	/**
	 * 任务的触发器是否存在
	 * @param triggerName 默认与jobName相同
	 * @return
	 * @throws SchedulerException
	 * @author: xue.zhang
	 * @date 2018年5月28日下午5:52:39
	 */
	public static boolean isExistsTrigger(String triggerName) throws SchedulerException {
		if(Assert.isEmpty(triggerName)) {
			return false;
		}
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		TriggerKey triggerKey = new TriggerKey(getTriggerName(triggerName), DEFAULT_CRON_TRIGGER_GROUP_NAME);
		return scheduler.checkExists(triggerKey);
	}
	
	/**
	 * 暂停某个任务
	 * @param jobName
	 * @return
	 * @author: xue.zhang
	 * @throws SchedulerException 
	 * @date 2018年5月28日下午5:58:35
	 */
	public static boolean stopJob(String jobName) throws SchedulerException {
		if(Assert.isEmpty(jobName)) {
			return false;
		}
		
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		JobKey jobKey = new JobKey(getJobName(jobName), DEFAULT_CRON_JOB_GROUP_NAME);
		if(scheduler.isStarted()) {//任务管理器是开启的
			scheduler.pauseJob(jobKey);//暂停任务
		}
		return true;
	}

	/**
	 * 暂停某个任务
	 * @param jobName
	 * @return
	 * @author: xue.zhang
	 * @throws SchedulerException 
	 * @date 2018年5月28日下午5:58:35
	 */
	public static boolean startJob(String jobName) throws SchedulerException {
		if(Assert.isEmpty(jobName)) {
			return false;
		}
		
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		JobKey jobKey = new JobKey(getJobName(jobName), DEFAULT_CRON_JOB_GROUP_NAME);
		scheduler.resumeJob(jobKey);//重启任务
		return true;
	}
	
	/**
	 * @Title: 获得任务信息
	 * @return
	 * @throws SchedulerException
	 * @date: 2018年5月29日下午9:50:59
	 */
	@SuppressWarnings("unchecked")
	public static CornJobInfo getJobInfo(String jobName) throws SchedulerException{
		if(Assert.isEmpty(jobName)) {
			return new CornJobInfo();
		}
		
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		jobName = getJobName(jobName);
		String jobGroup = DEFAULT_CRON_JOB_GROUP_NAME;
		JobKey jobKey = new JobKey(jobName, jobGroup);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if(jobDetail == null)
			return new CornJobInfo();
		
		Map<String, Object> params = jobDetail.getJobDataMap().getWrappedMap();
		Class<? extends Job> jobClass = jobDetail.getJobClass();
		String jobClassName = jobClass.getName();
		String jobClassSimleName = jobClass.getSimpleName();
		
		List<CronTriggerImpl> triggers = (List<CronTriggerImpl>) scheduler.getTriggersOfJob(jobKey);
		
		CornJobInfo jobInfo = new CornJobInfo();
		
		if(Assert.isEmpty(triggers)){//该任务没有时间触发器
			jobInfo = new CornJobInfo(
					jobName.split(":")[1], jobGroup, 
					"", "", 
					"", 
					jobClassName, jobClassSimleName, 
					null, params);
			jobInfo.setJobStatus("不存在");
		} else {
			Date nextFireTime = triggers.get(0).getNextFireTime();//获得下一次任务触发时间
			CronTriggerImpl trigger = triggers.get(0);
			String triggerName = trigger.getKey().getName();
			String triggerGroupName = trigger.getKey().getGroup();
			String corn = trigger.getCronExpression();
			
			TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			String status = TriggerStateUtil.getStatus(triggerState);
			
			jobInfo = new CornJobInfo(
					jobName.split(":")[1], jobGroup, 
					triggerName.split(":")[1], triggerGroupName, 
					corn, 
					jobClassName, jobClassSimleName, 
					nextFireTime, params);
			
			jobInfo.setJobStatus(status);
		}
		
		return jobInfo;
	}
	/**
	 * @Title: 获得所有任务的信息
	 * @return
	 * @throws SchedulerException
	 * @date: 2018年5月29日下午9:50:59
	 */
	@SuppressWarnings("unchecked")
	public static List<CornJobInfo> getAllJob() throws SchedulerException{
		Scheduler scheduler = STD_SCHEDULER_FACTORY.getScheduler();
		List<CornJobInfo> resultList = new ArrayList<>(); 
		
		for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
            	JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            	
            	if(jobDetail == null)//任务不存在
        			continue;
            	
            	String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();
                Map<String, Object> params = jobDetail.getJobDataMap().getWrappedMap();
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                String jobClassName = jobClass.getName();
                String jobClassSimleName = jobClass.getSimpleName();
                
                CornJobInfo jobInfo = new CornJobInfo();
                List<CronTriggerImpl> triggers = (List<CronTriggerImpl>) scheduler.getTriggersOfJob(jobKey);
        		if(Assert.isEmpty(triggers)){//该任务没有时间触发器
        			jobInfo = new CornJobInfo(
        					jobName.split(":")[1], jobGroup, 
        					"", "", 
        					"", 
        					jobClassName, jobClassSimleName, 
        					null, params);
        			jobInfo.setJobStatus("不存在");
        		} else {
        			Date nextFireTime = triggers.get(0).getNextFireTime();//获得下一次任务触发时间
        			CronTriggerImpl trigger = triggers.get(0);
        			String triggerName = trigger.getKey().getName();
        			String triggerGroupName = trigger.getKey().getGroup();
        			String corn = trigger.getCronExpression();
        			
        			TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        			String status = TriggerStateUtil.getStatus(triggerState);
        			
        			jobInfo = new CornJobInfo(
        					jobName.split(":")[1], jobGroup, 
        					triggerName.split(":")[1], triggerGroupName, 
        					corn, 
        					jobClassName, jobClassSimleName, 
        					nextFireTime, params);
        			
        			jobInfo.setJobStatus(status);
        		}
                resultList.add(jobInfo);
            }
        }
		return resultList;
	}
	
	
}