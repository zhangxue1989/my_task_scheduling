package com.zx.qm;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.zx.manager.QuartzJob;
import com.zx.qm.manager.CronQuartzManager;

import junit.framework.TestCase;


public class CronQuartzManagerTest extends TestCase {

	public void testAddJob() {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("k1", "v1");
			params.put("k2", "v2");
			boolean b1 = CronQuartzManager.addJob("2333", QuartzJob.class, "0/2 * * * * ?", null);
			boolean b2 = CronQuartzManager.addJob("6666", QuartzJob.class, "0/2 * * * * ?", null);
			
			Assert.assertEquals(b1, true);
			Assert.assertEquals(b2, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}


	public void testModifyJobTime() throws Exception {
		boolean b1 = CronQuartzManager.addJob("2333", QuartzJob.class, "0/2 * * * * ?", null);
		boolean b = CronQuartzManager.modifyJobTime("2333", "0/3 * * * * ?", null);
		Assert.assertEquals(b, true);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void testRemoveJob() throws Exception {
		//boolean b1 = CronQuartzManager.addJob("2333", QuartzJob.class, "0/2 * * * * ?", null);
		boolean b = CronQuartzManager.removeJob("2333");
		Assert.assertEquals(b, true);
		boolean existsJob = CronQuartzManager.isExistsJob("2333");
		System.out.println(existsJob);
		/*try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}


	public void testStartJobs() {
	}


	public void testShutdownJobs() {
	}


	public void testIsRun() {
	}


	public void testIsExistsJob() {
	}


	public void testIsExistsTrigger() {
	}


	public void testStopJob() {
	}


	public void testStartJob() {
	}


	public void testGetJobName() {
	}


	public void testGetTriggerName() {
	}

}
