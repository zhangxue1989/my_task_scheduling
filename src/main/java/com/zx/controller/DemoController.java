package com.zx.controller;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zx.qm.manager.CronQuartzManager;
import com.zx.qm.model.JobInfo;

@Controller
public class DemoController {
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~任务列表页面~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	@RequestMapping("/allJob")
	public String allJob(Model model) {
		try {
			List<JobInfo> allJob = CronQuartzManager.getAllJob();
			model.addAttribute("allJob", allJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		}
		return "/index.jsp";
	}
	
	@RequestMapping("/stopJob")
	public String stopJob(Model model, String jobName) {
		try {
			jobName = new String(jobName.getBytes("ISO-8859-1"), "UTF-8");
			boolean bool = CronQuartzManager.stopJob(jobName);
			model.addAttribute("msg", bool ? "停止成功" : "停止失败");
			
			List<JobInfo> allJob = CronQuartzManager.getAllJob();
			model.addAttribute("allJob", allJob);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		}
		return "/index.jsp";
	}

	@RequestMapping("/startJob")
	public String startJob(Model model, String jobName) {
		try {
			jobName = new String(jobName.getBytes("ISO-8859-1"), "UTF-8");
			boolean bool = CronQuartzManager.startJob(jobName);
			model.addAttribute("msg", bool ? "启动成功" : "启动失败");
			
			List<JobInfo> allJob = CronQuartzManager.getAllJob();
			model.addAttribute("allJob", allJob);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		}
		return "/index.jsp";
	}
	
	@RequestMapping("/removeJob")
	public String removeJob(Model model, String jobName) {
		try {
			jobName = new String(jobName.getBytes("ISO-8859-1"), "UTF-8");
			boolean bool = CronQuartzManager.removeJob(jobName);
			model.addAttribute("msg", bool ? "删除成功" : "删除失败");
			
			List<JobInfo> allJob = CronQuartzManager.getAllJob();
			model.addAttribute("allJob", allJob);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		} 
		return "/index.jsp";
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~修改页面~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	
	@RequestMapping("/getJobInfo")
	public String getJobInfo(Model model, String jobName) {
		try {
			jobName = new String(jobName.getBytes("ISO-8859-1"), "UTF-8");
			JobInfo jobInfo = CronQuartzManager.getJobInfo(jobName);
			model.addAttribute("jobInfo", jobInfo);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		}
		return "/update.jsp";
	}
	
	@RequestMapping("/updateJob")
	public String updateJob(Model model, String jobName, String corn) {
		try {
			jobName = new String(jobName.getBytes("ISO-8859-1"), "UTF-8");
			boolean bool = CronQuartzManager.modifyJobTime(jobName, corn);
			model.addAttribute("msg", bool ? "修改成功" : "修改失败");
			
			JobInfo jobInfo = CronQuartzManager.getJobInfo(jobName);
			model.addAttribute("jobInfo", jobInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		}
		return "/update.jsp";
	}
	
	@RequestMapping("/shutdown")
	public String shutdown(Model model) {
		try {
			boolean bool = CronQuartzManager.stopJobs();
			model.addAttribute("msg", bool ? "全部停止成功" : "全部停止失败");
			
			List<JobInfo> allJob = CronQuartzManager.getAllJob();
			model.addAttribute("allJob", allJob);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "操作异常");
		}
		return "/index.jsp";
	}
	
}
