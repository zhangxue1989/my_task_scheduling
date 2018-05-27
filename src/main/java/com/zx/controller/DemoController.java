package com.zx.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DemoController {

	@RequestMapping("/get")
	public String get(Model model) {
		
		Person p1 = new Person("1", "张三");
		Person p2 = new Person("2", "李四");
		Person p3 = new Person("3", "王五");

		List<Person> restList = Arrays.asList(p1, p2, p3);
		
		Map<String, Person> restMap = new HashMap<String, Person>();
		restMap.put("1", p1);
		restMap.put("2", p2);
		restMap.put("3", p3);
        
		model.addAttribute("restList", restList);
		model.addAttribute("restMap", restMap);
		
		model.addAttribute("cur_time", new Date());
		
		return "/jsp/demo.jsp";
	}
	
}
