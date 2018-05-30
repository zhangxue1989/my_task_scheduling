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
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "1111111111111111");    
    }  
}
