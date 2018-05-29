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
	
	
	@RequestMapping("/allJob")
	public String allJob(Model model) {
		try {
			List<JobInfo> allJob = CronQuartzManager.getAllJob();
			model.addAttribute("allJob", allJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "/jsp/demo.jsp";
	}
	
}
