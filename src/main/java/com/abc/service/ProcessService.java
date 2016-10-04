package com.abc.service;


import org.metaworks.dwr.MetaworksRemoteService;
import org.oce.garuda.multitenancy.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uengine.codi.mw3.model.IInstance;
import org.uengine.codi.mw3.model.Instance;
import org.uengine.codi.mw3.model.WorkItemHandler;
import org.uengine.kernel.EJBProcessInstance;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.RoleMapping;
import org.uengine.processmanager.ProcessManagerRemote;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by Administrator on 2016-09-29.
 */
@Component("processService")
@Scope("request")
public class ProcessService {

    @Autowired
    @Qualifier("processManagerBeanForQueue")
    ProcessManagerRemote pm;  //@Service 로 ProcessService를 선언할 시에는 이 것이 재사용되어 문제가 발생한다.

    @Transactional
    public void runProcess(Map<String, String> paramMap) throws Exception{
//        this.run(paramMap);
//    }
//
//    public void run(Map<String, String> paramMap) throws Exception{
        //ProcessManagerRemote pm = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);
        //tenant setting
        String defId = paramMap.get("defId");
        String userId = paramMap.get("userId");
        String userName = paramMap.get("userName");
        new TenantContext(paramMap.get("tenantId"));

        String initInstId = "";
        ProcessInstance instance = null;
        try {
            initInstId = pm.initializeProcess(defId);
            instance = pm.getProcessInstance(initInstId);
            if("1".equals(paramMap.get("isSim"))) {
                ((EJBProcessInstance)instance).getProcessInstanceDAO().set("isSim", Integer.valueOf(1));
                instance.setName("[Test] "+instance.getName());
            }

            RoleMapping roleMapping = RoleMapping.create();
            roleMapping.setName("Initiator");
            roleMapping.setEndpoint(userId);
            roleMapping.setResourceName(userName);
            roleMapping.fill(instance);
            instance.putRoleMapping(roleMapping);

            EJBProcessInstance ejbProcessInstance = (EJBProcessInstance)instance;
            ejbProcessInstance.getProcessInstanceDAO().set("initEP", "1");

            pm.putRoleMapping(initInstId, roleMapping);
            pm.setLoggedRoleMapping(roleMapping);
            pm.executeProcess(initInstId);
            pm.applyChanges();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
