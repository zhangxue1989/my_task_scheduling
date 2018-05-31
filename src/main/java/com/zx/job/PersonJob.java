package com.zx.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


//@DisallowConcurrentExecution
public class PersonJob implements Job {

	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		//Person person = (Person)arg0.getMergedJobDataMap().get("person");
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
        System.out.println(
        		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) 
        		+ "\t" + arg0.getJobDetail().getKey().getName()
        		+ "\t" + Thread.currentThread().getName()
        	);    
    }  
}
