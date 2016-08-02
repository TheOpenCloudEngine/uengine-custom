package com.abc.monitor;

import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.kernel.ProcessInstance;
import org.uengine.processmanager.ProcessManagerBean;

import java.rmi.RemoteException;

/**
 * Created by uEngineYBS on 2016-07-27.
 */
public class ShellDetailView {

    public ShellDetailView() {
    }

    public ShellDetailView(Console console, String instanceId, String tracingTag) {
        this.console = console;
        this.instanceId = instanceId;
        this.tracingTag = tracingTag;
    }

    Console console;
    public Console getConsole() { return console; }
    public void setConsole(Console console) { this.console = console; }

    String instanceId;
    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }

    String tracingTag;
    public String getTracingTag() { return tracingTag; }
    public void setTracingTag(String tracingTag) { this.tracingTag = tracingTag; }

    @ServiceMethod(payload = {"instanceId", "tracingTag"})
    public void stop(){

        ProcessManagerBean pm = MetaworksRemoteService.getComponent(ProcessManagerBean.class);
        ProcessInstance instance = null;
        try {

            // stopSignaledContext = "stopSignaled_" + getInstanceId() + "_" + getTracingTag();
            instance = pm.getProcessInstance(getInstanceId());
            instance.setProperty(getTracingTag(), "stopSignaled", new Boolean(true));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //public static String stopSignaledContext;
}
