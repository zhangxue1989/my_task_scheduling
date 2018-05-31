package com.zx.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zx.entity.Person;

public class PersonJob implements Job {

	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		//Person person = (Person)arg0.getMergedJobDataMap().get("person");
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + arg0.getJobDetail().getKey().getName());    
    }  
}
