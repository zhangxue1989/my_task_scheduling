package com.zx.manager;

import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

/**
 * @Project: my_task_scheduling
 * @Title: QuartzManager
 * @Description: 定时任务管理类
 * @author: zhangxue
 * @date: 2018年5月27日下午10:21:04
 * @company: yooli
 * @Copyright: Copyright (c) 2015
 * @version v1.0
 */
public class QuartzManager {
	
	/**
	 * 定时任务，时间规则
	 字段   		允许值   				允许的特殊字符
	秒   	 		0-59    			, - * /
	分   			0-59    			, - * /
	小时    		0-23    			, - * /
	日期    		1-31    			, - * ? / L W C
	月份    		1-12 或者 JAN-DEC    , - * /
	星期    		1-7 或者 SUN-SAT    	, - * ? / L C #
	年（可选）    	留空, 1970-2099    	, - * / 
	- 区间  
	* 通配符  
	? 你不想设置那个字段
	
	1.下面只例出几个式子
		CRON表达式    				含义 
		"0 0 12 * * ?"    		每天中午十二点触发 
		"0 15 10 ? * *"    		每天早上10：15触发 
		"0 15 10 * * ?"    		每天早上10：15触发 
		"0 15 10 * * ? *"    	每天早上10：15触发 
		"0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
		"0 * 14 * * ?"    		每天从下午2点开始到2点59分每分钟一次触发 
		"0 0/5 14 * * ?"    	每天从下午2点开始到2：55分结束每5分钟一次触发 
		"0 0/5 14,18 * * ?"   	每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
		"0 0-5 14 * * ?"    	每天14:00至14:05每分钟一次触发 
		"0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
		"0 15 10 ? * MON-FRI"   每个周一、周二、周三、周四、周五的10：15触发 
	 */
	public static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	
	/**
	 * 定时任务的线程组名称
	 */
	private static final String JOB_GROUP_NAME = "dataShareJobGroupName";
	
	/**
	 * 定时任务的触发器的线程组名称
	 */
	private static final String TRIGGER_GROUP_NAME = "dataShareTriggerGroupName";
	
	public static void main(String[] args) throws Exception {
		JobBuilder newJob = JobBuilder.newJob(QuartzJob.class);
		
		newJob.withIdentity("定时任务233", JOB_GROUP_NAME);// 任务名，任务组，任务执行类
		
		//newJob.usingJobData(new JobDataMap(params));//注入参数
		
		JobDetail jobDetail = newJob.build();
		
		CronTriggerImpl trigger = new CronTriggerImpl("定时任务233", TRIGGER_GROUP_NAME);
		
		trigger.setCronExpression("0/2 * * * * ?");
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		
		scheduler.start();
	}
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * @param jobName 任务名
	 * @param cls 任务
	 * @param time 时间设置，参考quartz说明文档
	 * @Title: QuartzManager
	 * @author mzm
	 * @date 2017-7-26 下午03:47:44
	 * @version V2.0
	 */
	@SuppressWarnings("rawtypes")
	public static boolean addJob(String jobName, Class cls, String cron, Map<String, String> params) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			
			JobBuilder newJob = JobBuilder.newJob(cls);
			
			newJob.withIdentity(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类
			
			newJob.usingJobData(new JobDataMap(params));//注入参数
			
			JobDetail jobDetail = newJob.build();
			
			CronTriggerImpl trigger = new CronTriggerImpl(jobName, TRIGGER_GROUP_NAME);
			
			trigger.setCronExpression(cron);
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			//Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).build();
			
//			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, cls);// 任务名，任务组，任务执行类
//			//注入参数
//			jobDetail.getJobDataMap().putAll(params);
//			
//			// 触发器
//			CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
//			trigger.setCronExpression(cron);// 触发器时间设定
//			sched.scheduleJob(jobDetail, trigger);
//			// 启动
//			if (!sched.isShutdown()) {
//				sched.start();
//			}
			return true;
		} catch (Exception e) {
			return false;
		}
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
	 */
	@SuppressWarnings("rawtypes")
	public static boolean addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class jobClass, String cron) {
		try {
//			Scheduler sched = gSchedulerFactory.getScheduler();
//			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
//			// 触发器
//			CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
//			trigger.setCronExpression(cron);// 触发器时间设定
//			sched.scheduleJob(jobDetail, trigger);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName
	 * @param time
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:49:21
	 * @version V2.0
	 */
	@SuppressWarnings("rawtypes")
	public static boolean modifyJobTime(String jobName, String cron, Map<String, String> params) {
		try {
//			Scheduler sched = gSchedulerFactory.getScheduler();
//			CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
//			if (trigger == null) {
//				return false;
//			}
//			String oldTime = trigger.getCronExpression();
//			if (!oldTime.equalsIgnoreCase(cron)) {
//				JobDetail jobDetail = sched.getJobDetail(jobName, JOB_GROUP_NAME);
//				Class objJobClass = jobDetail.getJobClass();
//				removeJob(jobName);
//				addJob(jobName, objJobClass, cron, params);
//			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @Description: 修改一个任务的触发时间
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:49:37
	 * @version V2.0
	 */
	public static boolean modifyJobTime(String triggerName, String triggerGroupName, String cron) {
		try {
//			Scheduler sched = gSchedulerFactory.getScheduler();
//			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
//			if (trigger == null) {
//				return false;
//			}
//			String oldTime = trigger.getCronExpression();
//			if (!oldTime.equalsIgnoreCase(cron)) {
//				CronTrigger ct = (CronTrigger) trigger;
//				// 修改时间
//				ct.setCronExpression(cron);
//				// 重启触发器
//				sched.resumeTrigger(triggerName, triggerGroupName);
//			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:49:51
	 * @version V2.0
	 */
	public static boolean removeJob(String jobName) {
		try {
//			Scheduler sched = gSchedulerFactory.getScheduler();
//			sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
//			sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
//			sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * 
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:50:01
	 * @version V2.0
	 */
	public static boolean removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
//			Scheduler sched = gSchedulerFactory.getScheduler();
//			sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
//			sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
//			sched.deleteJob(jobName, jobGroupName);// 删除任务
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description:启动所有定时任务
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:50:18
	 * @version V2.0
	 */
	public static boolean startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 * @Title: QuartzManager.java
	 * @author mzm
	 * @date 2017-7-26 下午03:50:26
	 * @version V2.0
	 */
	public static boolean shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @Title:定时任务是否在运行 
	 * @return
	 * @author: zhangx
	 * @date: 2017年8月1日 下午7:09:56
	 * @version v1.0
	 */
	public static boolean isRun(){
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			return scheduler.isStarted();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @Title: 根据触发器的触发时间，计算触发器的时间规则
	 * @return
	 * @author: zhangx
	 * @date: 2017年8月2日 上午10:27:15
	 * @version v1.0
	 */
	public static String getCron(int triggerHour){
		if(triggerHour > 0 && triggerHour <= 12) {//每隔几小时触发一次
			return "0 0 0/" + triggerHour + " * * ?";
		} 
		
		if(triggerHour == 24 ) {//每天00点触发
			return "0 0 0 * * ?";
		}
		
		if(triggerHour > 24 && triggerHour < 48) {//每天指定时间触发
			return "0 0 "+(triggerHour - 24)+" * * ?";
		} 
		
		return "";
	}
}