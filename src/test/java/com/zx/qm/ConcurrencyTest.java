package com.zx.qm;

import java.io.IOException;

import com.zx.job.PersonConcurrencyJob;
import com.zx.job.PersonJob;
import com.zx.job.QuartzJob;
import com.zx.qm.manager.CronQuartzManager;

import junit.framework.TestCase;


public class ConcurrencyTest extends TestCase {
	/**
	 * 测试当任务执行时间大于任务时间间隔
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:11:44
	 */
	public void test1() throws Exception {
		CronQuartzManager.addJob("测试1_AAA", PersonConcurrencyJob.class, "0/2 * * * * ?", null);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:11:44
	 */
	public void test2() throws Exception {
		CronQuartzManager.addJob("AAA", PersonConcurrencyJob.class, "0/1 * * * * ?", null);
		CronQuartzManager.addJob("BBB", QuartzJob.class, "0/1 * * * * ?", null);
		CronQuartzManager.addJob("CCC", QuartzJob.class, "0/1 * * * * ?", null);
		CronQuartzManager.addJob("DDD", QuartzJob.class, "0/1 * * * * ?", null);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
