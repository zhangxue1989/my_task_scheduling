package com.zx.listener;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.zx.entity.Person;
import com.zx.qm.manager.CronQuartzManager;

@Repository
public class InitJobBean implements InitializingBean {
	
	@Override
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		
		Class<? extends Job> personJob = (Class<? extends Job>)Class.forName("com.zx.job.PersonJob");
		Class<? extends Job> quartzJob = (Class<? extends Job>)Class.forName("com.zx.job.QuartzJob");
		
		//任务一
		Map<String, Object> params = new HashMap<>();
		Person person = new Person("No001", "张三");
		params.put("person", person);
		
		boolean b1 = CronQuartzManager.addJob("测试1_用户任务", personJob, "0/10 * * * * ?", params);
		System.out.println(b1);
		
		//任务二
		boolean b2 = CronQuartzManager.addJob("测试2_简单任务", quartzJob, "0/10 * * * * ?", null);
		System.out.println(b2);

	}

}
