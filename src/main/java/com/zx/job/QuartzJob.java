package com.zx.job;

import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Set;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {

	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
    	//获得参数
    	Set<Entry<String, Object>> entrySet = arg0.getMergedJobDataMap().entrySet();
    	for (Entry<String, Object> entry : entrySet) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "2222222222222222");    
    }  
    
}  