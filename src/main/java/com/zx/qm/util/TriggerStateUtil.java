package com.zx.qm.util;

import org.quartz.Trigger.TriggerState;

/**
 * @Project: my_task_scheduling
 * @Title: TriggerStateUtil
 * @Description: 任务状态对应关系
 *  BLOCKED 	4 	阻塞 
	COMPLETE 	2 	完成 
	ERROR 		3 	错误 
	NONE 	   -1 	不存在 
	NORMAL 		0 	正常 
	PAUSED 		1	 暂停
 * @author: zhangxue
 * @date: 2018年5月29日下午9:44:09
 * @company: yooli
 * @Copyright: Copyright (c) 2015
 * @version v1.0
 */
public class TriggerStateUtil {
	
	public static String getStatus(TriggerState triggerState){
		switch (triggerState) {
		case BLOCKED:
			return "阻塞";
		case COMPLETE:
			return "完成";
		case ERROR:
			return "错误";
		case NONE:
			return "不存在";
		case NORMAL:
			return "正常";
		case PAUSED:
			return "暂停";
		default:
			return "状态异常";
		}
	}
	
}
