package com.zx.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Project: my_task_scheduling
 * @Title: PersonConcurrencyJob
 * @Description: 
 * 	加入，当任务执行时间大于任务时间间隔，会出现上次任务没有执行完毕的时候，下次任务便开始执行。
 * 	注解 @DisallowConcurrentExecution 保证任务类，只能以单线程的方式去执行。
 * @author: zhangxue
 * @date: 2018年5月31日下午9:29:10
 * @company: yooli
 * @Copyright: Copyright (c) 2015
 * @version v1.0
 */
@DisallowConcurrentExecution
public class PersonConcurrencyJob implements Job {

	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		System.out.println(
        		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) 
        		+ "\t" + arg0.getJobDetail().getKey().getName()
        		+ "\t" + Thread.currentThread().getName()
        	);  
		//Person person = (Person)arg0.getMergedJobDataMap().get("person");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*for (int j = 0; j < 10000; j++) {
			List<Person> li = new ArrayList<>();
			for (int i = 0; i < 10000; i++) {
				li.add(new Person(String.valueOf(i), String.valueOf(i)));
			}
			Person[] array = li.toArray(new Person[li.size()]);
			li = new ArrayList<>(Arrays.asList(array));
		}*/
          
    }  
}
