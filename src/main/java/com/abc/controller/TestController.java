package com.abc.controller;

import com.abc.service.ProcessService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("request")
public class TestController {

	@Autowired
	ProcessService processService;

	@RequestMapping(value = "/callProcess", method = RequestMethod.GET)
	public String callTestPage() {
		return "callProcess";
	}

	@RequestMapping(value = "/runProcess", method = RequestMethod.POST)
	@ResponseBody
	public String runProcess(@RequestParam Map<String, String> paramMap) throws Exception{

		processService.runProcess(paramMap);
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("result", "result6");
		String json = new Gson().toJson(returnMap);
		return json;
	}

}
