package com.abc.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uengine.kernel.ProcessInstance;
import org.uengine.processmanager.ProcessManagerRemote;

/**
 * Created by Administrator on 2016-09-29.
 */
@Service("processService")
public class ProcessService {

    @Autowired
    @Qualifier("processManagerBeanForQueue")
    ProcessManagerRemote pm;

    @Transactional
    public String runProcess(String defId, String userId){

        //ProcessExecutionThread processExecutionThread = MetaworksRemoteService.getComponent(ProcessExecutionThread.class);
        String initInstId = "";
        ProcessInstance instance = null;
        try {
            initInstId = pm.initializeProcess(defId);
            instance = pm.getProcessInstance(initInstId);

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

        return initInstId;
    }

}
