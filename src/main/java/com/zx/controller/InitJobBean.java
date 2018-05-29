package com.zx.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.zx.entity.Person;
import com.zx.job.PersonJob;
import com.zx.job.QuartzJob;
import com.zx.qm.manager.CronQuartzManager;

@Repository
public class InitJobBean implements InitializingBean {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		//任务一
		Map<String, Object> params = new HashMap<>();
		Person person = new Person("No001", "张三");
		params.put("person", person);
		
		boolean b1 = CronQuartzManager.addJob("测试1_用户任务", PersonJob.class, "0/5 * * * * ?", params);
		System.out.println(b1);
		
		//任务二
		boolean b2 = CronQuartzManager.addJob("测试2_简单任务", QuartzJob.class, "0/2 * * * * ?", null);
		System.out.println(b2);

	}

}
