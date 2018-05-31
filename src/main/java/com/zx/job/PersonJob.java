package com.zx.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StopWatch;

import com.zx.entity.Person;

public class PersonJob implements Job {

	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		//Person person = (Person)arg0.getMergedJobDataMap().get("person");
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		for (int j = 0; j < 10000; j++) {
			List<Person> li = new ArrayList<>();
			for (int i = 0; i < 10000; i++) {
				li.add(new Person(String.valueOf(i), String.valueOf(i)));
			}
			Person[] array = li.toArray(new Person[li.size()]);
			li = new ArrayList<>(Arrays.asList(array));
		}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + arg0.getJobDetail().getKey().getName());    
    }  
	
	/*public static void main(String[] args) {
		StopWatch sw = new StopWatch();
		sw.start();
		for (int j = 0; j < 10000; j++) {
			List<Person> li = new ArrayList<>();
			for (int i = 0; i < 10000; i++) {
				li.add(new Person(String.valueOf(i), String.valueOf(i)));
			}
			Person[] array = li.toArray(new Person[li.size()]);
			li = new ArrayList<>(Arrays.asList(array));
		}
		sw.stop();
		System.out.println(sw.prettyPrint());
	}*/
}
