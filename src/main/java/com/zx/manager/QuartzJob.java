package com.zx.manager;

import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;

import com.zx.qm.manager.CronQuartzManager;


public class QuartzJob implements Job {

	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
    	//获得参数
    	Set<Entry<String, Object>> entrySet = arg0.getMergedJobDataMap().entrySet();
    	for (Entry<String, Object> entry : entrySet) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");    
    }  
    
    
    public static void main(String[] args) {  
    	Map<String, String> params = new HashMap<>();
    	params.put("k1", "v1");
    	params.put("k2", "v2");
        try {  
            String job_name = "动态任务调度2333";  
            
            System.out.println("【系统启动】开始(每天早上10点开启)...");
            CronQuartzManager.addJob(job_name, QuartzJob.class, "5 0/1 * * * ? ", params);
           
            System.out.println("【全部任务的状态】" + CronQuartzManager.isRun());
            Thread.sleep(5000);
            
            System.out.println("【修改时间】开始(每2秒输出一次)...");
            CronQuartzManager.modifyJobTime(job_name, "0/2 * * * * ?", params);    
            
            Thread.sleep(5000);
            System.out.println("【移除定时】开始...");
            CronQuartzManager.removeJob(job_name);    
            System.out.println("【移除定时】成功");
              
            System.out.println("【再次添加定时任务】开始(每3秒输出一次)...");
            CronQuartzManager.addJob(job_name, QuartzJob.class, "0/3 * * * * ?", params);
            
            Thread.sleep(5000);    
//            System.out.println("【移除定时】开始...");
//            CronQuartzManager.removeJob(job_name);    
//            System.out.println("【移除定时】成功");  
            System.out.println("【全部任务的状态】" + CronQuartzManager.isRun());
            CronQuartzManager.shutdownJobs();
            System.out.println("【全部任务的状态】" + CronQuartzManager.isRun());
            
            CronQuartzManager.startJobs();
            System.out.println("【全部任务的状态】" + CronQuartzManager.isRun());
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  