package com.zx.qm;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.zx.job.QuartzJob;
import com.zx.qm.manager.CronQuartzManager;
import com.zx.qm.model.JobInfo;

import junit.framework.TestCase;


public class CronQuartzManagerTest extends TestCase {
	/**
	 * 增加多个任务
	 * 目标是:能都正常运行，任务之间互不影响
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:11:44
	 */
	public void testAddJob() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("k1", "v1");
		params.put("k2", "v2");
		boolean b1 = CronQuartzManager.addJob("测试1_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		boolean b2 = CronQuartzManager.addJob("测试1_BBB", QuartzJob.class, "0/2 * * * * ?", null);
		
		Assert.assertEquals(b1, true);
		Assert.assertEquals(b2, true);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改任务的触发时间
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:13:59
	 */
	public void testModifyJobTime() throws Exception {
		boolean b1 = CronQuartzManager.addJob("测试2_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b1, true);
		boolean b2 = CronQuartzManager.modifyJobTime("测试2_AAA", "0/5 * * * * ?");
		Assert.assertEquals(b2, true);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除已有的任务，并查询移除后任务是否还存在
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:15:54
	 */
	public void testRemoveJob() throws Exception {
		boolean b1 = CronQuartzManager.addJob("测试3_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b1, true);
		boolean b2 = CronQuartzManager.removeJob("测试3_AAA");
		Assert.assertEquals(b2, true);
		boolean existsJob = CronQuartzManager.isExistsJob("测试3_AAA");
		Assert.assertEquals(existsJob, false);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加多个任务后，关闭所有的任务
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:17:24
	 */
	public void testShutdownJobs() throws Exception {
		boolean b1 = CronQuartzManager.addJob("测试4_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		boolean b2 = CronQuartzManager.addJob("测试4_BBB", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b1, true);
		Assert.assertEquals(b2, true);
		
		Thread.sleep(4000);
		boolean shutdownJobs = CronQuartzManager.shutdownJobs();
		Assert.assertEquals(shutdownJobs, true);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 任务是否已经开启
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:43:33
	 */
	public void testIsRun() throws Exception {
		boolean b1 = CronQuartzManager.isRun();//没有任务
		Assert.assertEquals(b1, false);
		
		boolean b2 = CronQuartzManager.addJob("测试5_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b2, true);
		Thread.sleep(2000);
		
		boolean b3 = CronQuartzManager.isRun();//启动一个任务
		Assert.assertEquals(b3, true);
		
		boolean b4 = CronQuartzManager.shutdownJobs();//关闭全部任务
		Assert.assertEquals(b4, true);
		
		boolean b5 = CronQuartzManager.isRun();
		Assert.assertEquals(b5, false);
	}
	
	/**
	 * 任务是否是在
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:44:11
	 */
	public void testIsExistsJob() throws Exception {
		boolean b1 = CronQuartzManager.addJob("测试6_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b1, true);
		
		boolean existsJob = CronQuartzManager.isExistsJob("测试6_AAA");
		Assert.assertEquals(existsJob, true);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 任务触发器是否存在
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午4:44:59
	 */
	public void testIsExistsTrigger() throws Exception {
		boolean b1 = CronQuartzManager.addJob("测试7_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b1, true);
		
		boolean existsJob = CronQuartzManager.isExistsTrigger("测试7_AAA");
		Assert.assertEquals(existsJob, true);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试暂停某个任务
	 * @throws Exception
	 * @author: xue.zhang
	 * @date 2018年5月29日下午5:00:36
	 */
	public void testStopJob() throws Exception {
		boolean b1 = CronQuartzManager.addJob("测试8_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b1, true);
		boolean b2 = CronQuartzManager.addJob("测试8_BBB", QuartzJob.class, "0/2 * * * * ?", null);
		Assert.assertEquals(b2, true);
		
		boolean stopJob = CronQuartzManager.stopJob("测试8_AAA");
		Assert.assertEquals(stopJob, true);
		
		Thread.sleep(2000);
		
		stopJob = CronQuartzManager.stopJob("测试8_BBB");
		Assert.assertEquals(stopJob, true);
		
		System.in.read();
	}
	
	/**
	 * @Title: 暂停某个任务后，再启动这个任务
	 * @throws Exception
	 * @date: 2018年5月29日下午9:22:27
	 */
	public void testStartJob() throws Exception {
		CronQuartzManager.addJob("测试9_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		CronQuartzManager.addJob("测试9_BBB", QuartzJob.class, "0/2 * * * * ?", null);
		
		CronQuartzManager.stopJob("测试9_AAA");
		
		Thread.sleep(8000);
		CronQuartzManager.startJob("测试9_AAA");
		
		System.in.read();
	}
	
	public void testGetAllJob() throws Exception{
		CronQuartzManager.addJob("测试10_AAA", QuartzJob.class, "0/2 * * * * ?", null);
		CronQuartzManager.addJob("测试10_BBB", QuartzJob.class, "0/2 * * * * ?", null);
		
		List<JobInfo> allJob = CronQuartzManager.getAllJob();
		
		for (JobInfo jobInfo : allJob) {
			System.out.println(jobInfo);
		}
	}

}
