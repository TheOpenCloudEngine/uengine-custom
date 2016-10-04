package com.abc.controller;

import com.abc.service.ProcessService;
import com.google.gson.Gson;
import org.oce.garuda.multitenancy.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uengine.kernel.ProcessInstance;
import org.uengine.processmanager.ProcessManagerRemote;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

	@Autowired
	@Qualifier("processManagerBeanForQueue")
	ProcessManagerRemote pm;

	@RequestMapping(value = "test.do", method = RequestMethod.GET)
	public String callJsp() {
		return "test";
	}

	@RequestMapping(value = "call.do", method = RequestMethod.GET)
	public String callTestPage() {
		return "callProcess";
	}

	@RequestMapping(value = "initProcess.do", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String initProcess(@RequestParam Map<String, String> paramMap) throws Exception{

		new TenantContext("1");

		String defId = paramMap.get("defId");
		String userId = paramMap.get("userId");

		//ProcessExecutionThread processExecutionThread = MetaworksRemoteService.getComponent(ProcessExecutionThread.class);
		String initInstanceId = "";
		ProcessInstance instance = null;
		try {
			initInstanceId = pm.initializeProcess(defId);
			instance = pm.getProcessInstance(initInstanceId);

		}catch (Exception e){
			e.printStackTrace();
		}

		if(instance == null){
			try {
				throw new Exception("Porcess is not initialized.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("instanceId", initInstanceId);

		String json = new Gson().toJson(returnMap);
		return json;
	}

}
