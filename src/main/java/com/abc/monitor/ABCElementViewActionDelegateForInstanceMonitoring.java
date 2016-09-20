package com.abc.monitor;

import com.abc.activitytype.CustomSQLActivity;
import com.abc.activitytype.HiveActivity;
import com.abc.activitytype.ShellActivity;
import com.abc.activitytype.SyncActivity;
import com.abc.activitytype.interceptor.ScriptBaseAbstractTask;
import com.abc.activitytype.interceptor.TaskAttributes;
import com.abc.activitytype.interceptor.TaskHistory;
import com.abc.activitytype.view.*;
import com.abc.portal.ABCInstanceView;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.IFrame;
import org.metaworks.widget.Label;
import org.metaworks.widget.ModalWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.modeling.ElementView;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.social.ElementViewActionDelegateForInstanceMonitoring;
import org.uengine.web.util.ApplicationContextRegistry;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by jjy on 2016. 7. 18..
 */

@Component
public class ABCElementViewActionDelegateForInstanceMonitoring extends ElementViewActionDelegateForInstanceMonitoring {


    @Override
    public void onDoubleClick(ElementView elementView) {

        ApplicationContext context = ApplicationContextRegistry.getApplicationContext();
        TaskAttributes taskAttributes = context.getBean(TaskAttributes.class);

        if (elementView instanceof AnalysisActivityView) {

            IFrame iFrame = new IFrame();
            iFrame.setSrc("/test/test.do");
            iFrame.setHeight("600px");
            iFrame.setWidth("500px");

            ModalWindow modal = new ModalWindow(iFrame, elementView.getElement().getDescription());
            modal.setHeight(600);
            modal.setWidth(500);
            MetaworksRemoteService.wrapReturn(modal);
            //MetaworksRemoteService.wrapReturn(new ModalWindow(new IFrame("/data/ssh/" + getInstanceId()), elementView.getElement().getDescription()));

        } else if (elementView instanceof HiveActivityView
                || elementView instanceof SparkActivityView
                || elementView instanceof MapreduceActivityView) {

            try {
                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);
                ScriptBaseAbstractTask abstractTask = (ScriptBaseAbstractTask) elementView.getElement();

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                //TaskHistory taskHistory = taskAttributes.getTaskHistory(instance, abstractTask.getTracingTag());
                String stdout = "";
                Serializable serializable = instance.getProperty(abstractTask.getTracingTag(), "stdout");
                if(serializable != null){
                    stdout = (String)serializable;
                }
                MetaworksRemoteService.wrapReturn(new ModalWindow(new ShellDetailView(new Console(stdout), getInstanceId(), abstractTask.getTracingTag())));
//                if(taskHistory != null){
//                    MetaworksRemoteService.wrapReturn(new ModalWindow(new ShellDetailView(new Console(stdout), getInstanceId(), abstractTask.getTracingTag())));
//                }

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (elementView instanceof SyncActivityView) {

            try {

                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                MetaworksRemoteService.wrapReturn(new ModalWindow(new Console()));

                SyncActivity syncActivity = (SyncActivity) elementView.getElement();

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        } else if (elementView instanceof ShellActivityView) {

            try {

                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);
                ShellActivity shellActivity = (ShellActivity) elementView.getElement();

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                String log = (String) instance.getProperty(shellActivity.getTracingTag(), "log"); //for current log
                MetaworksRemoteService.wrapReturn(new ModalWindow(new ShellDetailView(new Console(log), getInstanceId(), shellActivity.getTracingTag())));


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //super.onDoubleClick(elementView);
        } else if (elementView instanceof CustomSQLActivityView) {

            try {

                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);
                CustomSQLActivity customSQLActivity = (CustomSQLActivity) elementView.getElement();

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                String log = (String) instance.getProperty(customSQLActivity.getTracingTag(), "resultSql"); //for current log
                MetaworksRemoteService.wrapReturn(new ModalWindow(new CustomSQLDetailView(new Console(log), getInstanceId(), customSQLActivity.getTracingTag())));


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //super.onDoubleClick(elementView);
        }

    }

    @Autowired
    public ProcessManagerRemote processManagerRemote;

}
